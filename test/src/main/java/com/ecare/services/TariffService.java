package com.ecare.services;

import com.ecare.dao.ContractDAO;
import com.ecare.dao.OptionDAO;
import com.ecare.dao.TariffDAO;
import com.ecare.dao.TariffOptionsDAO;
import com.ecare.domain.*;
import com.ecare.dto.OptionDto;
import com.ecare.dto.TariffDto;
import com.ecare.error.BaseObjectDeletionException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TariffService {
    @Autowired
    TariffDAO tariffDAO;
    @Autowired
    ContractDAO contractDAO;
    @Autowired
    ContractFacade contractFacade;
    @Autowired
    OptionService optionService;
    @Autowired
    OptionDAO optionDAO;
    @Autowired
    TariffOptionsDAO tariffOptionsDAO;
    @Autowired
    HotTariffService hotTariffService;

    @Autowired
    private ModelMapper modelMapper;

    public List<TariffDto> getAllTariffs() {
        List<TariffEntity> tariffEntitys = tariffDAO.findAll();
        List<TariffDto> tariffs = tariffEntitys.stream().map(this::convertToDto)
                .collect(Collectors.toList());
        for (TariffDto tariff : tariffs) {
            showTariffAddedMultiFreeOptions(tariff);
        }
        return tariffs;
    }

    public TariffDto findById(int tariffId) {
        try {
            TariffEntity tariffEntity = (TariffEntity) tariffDAO.findById(tariffId);
            tariffEntity.setTariffOptionEntities(null);
            TariffDto tariff = convertToDto(tariffEntity);
            showTariffAddedUniqueOptions(tariff);
            showTariffAddedMultiFreeOptions(tariff);
            return tariff;
        } catch (Exception ex) {
            TariffEntity tariffEntity = new TariffEntity();
            return convertToDto(tariffEntity);
        }
    }

    public Optional<String> saveTariff(TariffDto tariffDto) {
        if (!tariffDAO.findByName(tariffDto.getTariffName()).isEmpty()) {
            log.info(String.format("Tariff %s already exist", tariffDto.getTariffName()));
            return Optional.of(String.format("Tariff %s already exist", tariffDto.getTariffName()));
        }
        int internetOptionId = tariffDto.getInternetOptionId();
        int travelOptionId = tariffDto.getTravelOptionId();
        int callsOptionId = tariffDto.getCallsOptionId();
        if (callsOptionId == 0 && internetOptionId == 0 && travelOptionId == 0) {
            return Optional.of("The tariff does not contain any of the basic options: CALLS/INTERNET/TRAVEL - the tariff cannot be recorded");
        }
        TariffEntity tariff = convertToEntity(tariffDto);
        tariffDAO.save(tariff);
        tariffDto.setTariffId(tariff.getTariffId());

        if (setMainTariffOption(callsOptionId, tariff))
            return Optional.of(String.format("Calls option with Id %d not found", callsOptionId));
        if (setMainTariffOption(internetOptionId, tariff))
            return Optional.of(String.format("Internet option with Id %d not found", internetOptionId));
        if (setMainTariffOption(travelOptionId, tariff)) {
            return Optional.of(String.format("Travel option with Id %d not found", travelOptionId));
        }

        int freeOptionsNumber = tariffDto.getFreeOptionIds().size();
        if (freeOptionsNumber > 0) {
            for (int i = 0; i < freeOptionsNumber; i++) {
                OptionEntity option = (OptionEntity) optionDAO.findById(tariffDto.getFreeOptionIds().get(i));
                try {
                    saveTariffOptionInDataBase(tariff, option);
                } catch (Exception ex) {
                    return Optional.of(option.getOptionName() + " -this option is also added to the tariff " + tariff.getTariffName());
                }
            }
        }
        tariffDAO.update(tariff);
        hotTariffService.sendMessage();
        return Optional.empty();
    }

    private boolean setMainTariffOption(int optionId, TariffEntity tariff) {
        if (optionId != 0) {
            try {
                OptionEntity option = (OptionEntity) optionDAO.findById(optionId);
                saveTariffOptionInDataBase(tariff, option);
            } catch (Exception ex) {
                return true;
            }
        }
        return false;
    }

    private Optional<String> saveTariffOptionInDataBase(TariffEntity tariff, OptionEntity option) {
        if (tariffsOptionCombinationAbsent(tariff, option)) {
            TariffOptionsEntity tariffOptionsEntity = new TariffOptionsEntity();
            tariffOptionsEntity.setTariffByTariffId(tariff);
            tariffOptionsEntity.setOptionByOptionId(option);
            tariffOptionsDAO.save(tariffOptionsEntity);
            return Optional.empty();
        } else
            return Optional.of(String.format("Combination of tariff %sand option %s is already appear in the database", tariff.getTariffName(), option.getOptionName()));
    }

    private boolean tariffsOptionCombinationAbsent(TariffEntity tariff, OptionEntity option) {
        return tariffOptionsDAO.findByTariffIdOptionId(tariff.getTariffId(), option.getOptionId()).isEmpty();
    }


    public Optional<String> updateTariff(TariffDto tariffDto) {
        int tariffId = tariffDto.getTariffId();
        TariffEntity tariff = (TariffEntity) tariffDAO.findById(tariffId);
        tariff.setPrice(tariffDto.getPrice());
        tariff.setTariffName(tariffDto.getTariffName());
        tariff.setTariffDescription(tariffDto.getTariffDescription());
        int newCallsOptionId = tariffDto.getCallsOptionId();
        int newInternetOptionId = tariffDto.getInternetOptionId();
        int newTravelOptionId = tariffDto.getTravelOptionId();
        if (newCallsOptionId == 0 && newInternetOptionId == 0 && newTravelOptionId == 0) {
            return Optional.of("The tariff does not contain any of the basic options: CALLS/INTERNET/TRAVEL - the tariff cannot be recorded");
        }
        OptionDto oldCallsOption = optionService.getMainOptionByBaseAndTariffId(1, tariffId);
        if (oldCallsOption != null) {
            Optional<String> callsOptionError = updateMainOptionInTariff(tariffId, tariff, newCallsOptionId, oldCallsOption.getOptionId(), 1);
            if (callsOptionError.isPresent()) return callsOptionError;
        } else if (newCallsOptionId != 0) {
            setMainTariffOption(newCallsOptionId, tariff);
        }

        OptionDto oldInternetOption = optionService.getMainOptionByBaseAndTariffId(2, tariffId);
        if (oldInternetOption != null) {
            Optional<String> internetOptionError = updateMainOptionInTariff(tariffId, tariff, newInternetOptionId, oldInternetOption.getOptionId(), 2);
            if (internetOptionError.isPresent()) return internetOptionError;
        } else if (newInternetOptionId != 0) {
            setMainTariffOption(newInternetOptionId, tariff);
        }

        OptionDto oldTravelOption = optionService.getMainOptionByBaseAndTariffId(3, tariffId);
        if (oldTravelOption != null) {
            Optional<String> travelOptionError = updateMainOptionInTariff(tariffId, tariff, newTravelOptionId, oldTravelOption.getOptionId(), 3);
            if (travelOptionError.isPresent()) return travelOptionError;
        } else if (newTravelOptionId != 0) {
            setMainTariffOption(newTravelOptionId, tariff);
        }
        tariffDAO.update(tariff);
        hotTariffService.sendMessage();
        return Optional.empty();
    }

    private Optional<String> updateMainOptionInTariff(int tariffId, TariffEntity tariff, int newOptionId, int oldOptionId, int baseOptionId) {
        if (newOptionId != oldOptionId) {
            deleteOptionFromTariff(tariffId, oldOptionId);
            if (newOptionId == 0) {
                deleteTariffsOptionsByMainOptionDeletion(tariffId, baseOptionId);
            } else
                try {
                    OptionEntity option = (OptionEntity) optionDAO.findById(newOptionId);
                    saveTariffOptionInDataBase(tariff, option);
                } catch (Exception ex) {
                    return Optional.of(String.format("Option with Id %d not found", newOptionId));
                }
        }
        return Optional.empty();
    }

    private void deleteTariffsOptionsByMainOptionDeletion(int tariffId, int baseOptionId) {
        List<OptionDto> optionsToDeleteFromTariff = optionService.getMultiOptionsByBaseAndTariffId(baseOptionId, tariffId);
        if (!optionsToDeleteFromTariff.isEmpty())
            for (OptionDto option : optionsToDeleteFromTariff) {
                tariffOptionsDAO.delete(tariffId, option.getOptionId());
            }
    }


    /**
     * Base tariff is needed to replace the tariff to be removed
     */

    public void deleteTariff(int tariffId) {
        TariffEntity tarifftoDelete = (TariffEntity) tariffDAO.findById(tariffId);
        List<TariffEntity> baseTariff = tariffDAO.getBaseTariff();
        if (tarifftoDelete.isBaseTariff() || baseTariff.isEmpty()) {
            throw new BaseObjectDeletionException
                    (String.format("The object you are trying to delete: %s is the base object, or the base object has not been assigned yet. " +
                            "Please assign a new base object first!", tarifftoDelete.getTariffName()));
        } else {
            List<ContractEntity> contracts = contractFacade.getContractsByTariffId(tariffId);
            if (!contracts.isEmpty()) {
                for (ContractEntity contract : contracts) {
                    contract.setTariffByTariffId(baseTariff.get(0));
                    contractDAO.update(contract);
                }
            }
            tarifftoDelete.setContractEntities(null);
            tariffDAO.delete(tarifftoDelete);
        }
    }

    public void makeBaseTariff(int tariffId) {
        List<TariffEntity> oldBaseTariff = tariffDAO.getBaseTariff();
        if (!oldBaseTariff.isEmpty()) {
            oldBaseTariff.get(0).setBaseTariff(false);
            tariffDAO.update(oldBaseTariff.get(0));
        }
        TariffEntity newBaseTariff = (TariffEntity) tariffDAO.findById(tariffId);
        newBaseTariff.setBaseTariff(true);
        tariffDAO.update(newBaseTariff);
    }

    public void showUniqueCallsOptions(TariffDto tariffDto) {
        Map<String, Integer> mapOptions = tariffDto.getCallsOptions();
        optionDAO.getOptionsByParameters(OptionType.CALLS.getValueNumber(), false).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        mapOptions.put(" ", 0); //for empty
    }

    public void showUniqueInternetOptions(TariffDto tariffDto) {
        Map<String, Integer> mapOptions = tariffDto.getInternetOptions();
        optionDAO.getOptionsByParameters(OptionType.INTERNET.getValueNumber(), false).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        mapOptions.put(" ", 0); //for empty
    }

    public void showUniqueTravelOptions(TariffDto tariffDto) {
        Map<String, Integer> mapOptions = tariffDto.getTravelOptions();
        mapOptions.put(" ", 0); //for empty
        optionDAO.getOptionsByParameters(OptionType.TRAVEL.getValueNumber(), false).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
    }

    public void showFreeOptions(TariffDto tariffDto) {
        Map<String, Integer> mapOptions = tariffDto.getFreeOptions();
        optionDAO.getOptionsByParameters(0, true).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        mapOptions.put(" ", 0); //for empty
    }

    public void showUnselectedFreeOptions(TariffDto tariffDto) {
        Map<String, Integer> mapOptions = tariffDto.getFreeOptions();
        optionDAO.getUnselectedFreeOptions(tariffDto.getTariffId()).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        mapOptions.put(" ", 0); //for empty
    }


    public void showUnselectedMultiOptions(TariffDto tariffDto) {
        int tariffId = tariffDto.getTariffId();
        Map<String, Integer> mapOptions = tariffDto.getMultipleOptions();
        if (tariffDto.getCallsOption() != null) {
            optionDAO.getNotTariffAddedMultioptions(OptionType.CALLS.getValueNumber(), tariffId).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        }
        if (tariffDto.getInternetOption() != null) {
            optionDAO.getNotTariffAddedMultioptions(OptionType.INTERNET.getValueNumber(), tariffId).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        }
        if (tariffDto.getTravelOption() != null) {
            optionDAO.getNotTariffAddedMultioptions(OptionType.TRAVEL.getValueNumber(), tariffId).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        }
    }


    public Optional<String> setMultioptionsToTariff(TariffDto tariffDto) {
        //  TariffEntity tariff = (TariffEntity) tariffDAO.findByName(tariffDto.getTariffName());
        TariffEntity tariff = (TariffEntity) tariffDAO.findById(tariffDto.getTariffId());
        int multiOptionsNumber = tariffDto.getMultipleOptionIds().size();
        if (multiOptionsNumber > 0) {
            for (int i = 0; i < multiOptionsNumber; i++) {
                OptionEntity option = (OptionEntity) optionDAO.findById(tariffDto.getMultipleOptionIds().get(i));
                saveTariffOptionInDataBase(tariff, option);
            }
        }
        tariffDAO.update(tariff);
        return Optional.empty();
    }

    public void showTariffAddedUniqueOptions(TariffDto tariff) {
        OptionDto callsOption = optionService.getMainOptionByBaseAndTariffId(OptionType.CALLS.getValueNumber(), tariff.getTariffId());
        if (callsOption != null) {
            tariff.setCallsOption(callsOption);
            log.info(tariff.getCallsOption().getOptionName());
        }
        OptionDto internetOption = optionService.getMainOptionByBaseAndTariffId(OptionType.INTERNET.getValueNumber(), tariff.getTariffId());
        if (internetOption != null) {
            tariff.setInternetOption(internetOption);
            log.info(tariff.getInternetOption().getOptionName());
        }
        OptionDto travelOption = optionService.getMainOptionByBaseAndTariffId(OptionType.TRAVEL.getValueNumber(), tariff.getTariffId());
        if (travelOption != null) {
            tariff.setTravelOption(travelOption);
            log.info(tariff.getTravelOption().getOptionName());
        }
    }

    public void showTariffAddedMultiFreeOptions(TariffDto tariff) {
        List<OptionDto> tariffAddedMultiAndFreeoptions = optionService.showTariffAddedMultiOptions(tariff.getTariffId());
        if (!tariffAddedMultiAndFreeoptions.isEmpty()) {
            tariff.setMultipleOptionDtos(tariffAddedMultiAndFreeoptions);
        }
    }


    public List<TariffDto> getTariffsShowMultioptions() {
        List<TariffDto> listTariffs = getAllTariffs();
        for (TariffDto tariff : listTariffs) {
            showTariffAddedMultiFreeOptions(tariff);
        }
        return listTariffs;
    }

    public void deleteOptionFromTariff(int tariffId, int optionId) {
        tariffOptionsDAO.delete(tariffId, optionId);
    }

    private TariffDto convertToDto(TariffEntity tariffEntity) {
        return modelMapper.map(tariffEntity, TariffDto.class);
    }

    private TariffEntity convertToEntity(TariffDto tariffDto) {
        return modelMapper.map(tariffDto, TariffEntity.class);
    }

}
