package com.ecare.services;

import com.ecare.dao.ContractDAO;
import com.ecare.dao.OptionDAO;
import com.ecare.dao.TariffDAO;
import com.ecare.domain.*;
import com.ecare.dto.ClientDto;
import com.ecare.dto.ContractDto;
import com.ecare.dto.TariffDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class ContractFacade {

    @Autowired
    ContractDAO contractDAO;
    @Autowired
    ClientService clientService;
    @Autowired
    OptionService optionService;
    @Autowired
    NumberService numberService;
    @Autowired
    TariffDAO tariffDAO;
    @Autowired
    OptionDAO optionDAO;
    @Autowired
    TariffService tariffService;
    @Autowired
    ModelMapper modelMapper;


    @Transactional
    public List<ContractEntity> getContractsByTariffId(int id) {
        return contractDAO.getContractsByTariffId(id);
    }

    @Transactional
    public ContractDto findById(int id) {
        return convertToDto((ContractEntity) contractDAO.findById(id));
    }

    @Transactional
    public void blockContractByClient(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByUser(true);
        contractDAO.update(contract);
    }

    @Transactional
    public void unblockContractByClient(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByUser(false);
        contractDAO.update(contract);
    }

    @Transactional
    public int blockContractByCompany(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByCompany(true);
        contractDAO.update(contract);
        return contract.getClientByClientId().getClientId();
    }

    @Transactional
    public int unblockContractByCompany(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByCompany(false);
        contractDAO.update(contract);
        return contract.getClientByClientId().getClientId();
    }

    @Transactional
    public void updateContract(ContractDto contractDto) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(contractDto.getContractId());
        contract.setTariffByTariffId((TariffEntity) tariffDAO.findById(contractDto.getTariffId().getTariffId()));
        contractDAO.update(contract);
    }

    @Transactional
    public boolean isBlocked(ContractDto contractDto) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(contractDto.getContractId());
        return contract.isBlockedByCompany() || contract.isBlockedByUser();
    }


    @Transactional
    public void showAllTariffs(ContractDto contractDto) {
        Map<String, Integer> mapTariffs = contractDto.getTariffs();
        tariffDAO.getTariffNamesAndIds().forEach(array -> mapTariffs.put((String) array[1], (Integer) array[0]));
    }

    @Transactional
    public int save(ContractDto contractDto) {
        ContractEntity contract = new ContractEntity();
        ClientEntity client = clientService.findEntityById(contractDto.getClientId());
        contract.setClientByClientId(client);
        TariffEntity tariff = (TariffEntity) tariffDAO.findById(contractDto.getTariffId().getTariffId());
        contract.setTariffByTariffId(tariff);
        NumberEntity number = numberService.bookNumber(contractDto.getPhoneNumber().getId());
        contract.setPhoneNumber(number.getPhoneNumber());
        contractDAO.save(contract);
        return contract.getContractId();
    }

    private ContractDto convertToDto(ContractEntity contractEntity) {
      /*  ContractDto contract = modelMapper.map(contractEntity, ContractDto.class);
        contract.setTariff(contract.getTariffId().getTariffId());
        return contract;*/
        return modelMapper.map(contractEntity, ContractDto.class);
    }

    private ContractEntity convertToEntity(ContractDto contractDto) {
      /*  ContractEntity contractEntity = modelMapper.map(contractDto, ContractEntity.class);
        contractEntity.setTariffByTariffId((TariffEntity) tariffDAO.findById(contractDto.getTariff()));*/
        return modelMapper.map(contractDto, ContractEntity.class);
    }


    public ContractDto prepareNewContract(int clientId) {
        ContractDto contractDto = new ContractDto();
        numberService.generateFreeNumbers();
        ClientDto client = clientService.findById(clientId);
        contractDto.setClientId(client.getClientId());
        contractDto.setClientEmail(client.getUser().getEmail());
        numberService.putFreeNumbersToContractDto(contractDto);
        showAllTariffs(contractDto);
        return contractDto;
    }

/*    @Transactional
    public ContractDto prepareNewContractToSetOptions(int contractId) {
        ContractDto contract = findById(contractId);
        TariffDto tariff = tariffService.findByIdWithAddedOptions(contract.getTariffId().getTariffId());
        contract.setTariffId(tariff);
      //  showUnselectedMultiFreeOptions(contract, tariff);
        return contract;
    }*/

  /*  @Transactional
    public void showUnselectedMultiFreeOptions(ContractDto contractDto, TariffDto tariffDto) {
        Map<String, Integer> mapOptions = contractDto.getContractOptions();
        int tariffId = tariffDto.getTariffId();
        if (tariffDto.getCallsOption() != null) {
            optionDAO.getNotTariffAddedMultioptions(OptionType.CALLS.getValueNumber(), tariffId).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        }
        if (tariffDto.getInternetOption() != null) {
            optionDAO.getNotTariffAddedMultioptions(OptionType.INTERNET.getValueNumber(), tariffId).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        }
        if (tariffDto.getTravelOption() != null) {
            optionDAO.getNotTariffAddedMultioptions(OptionType.TRAVEL.getValueNumber(), tariffId).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        }
        optionDAO.getUnselectedFreeOptions(tariffId).forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
    }

    @Transactional
    public void saveOptionsToContract(ContractDto contractDto) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(contractDto.getContractId());
        int contractOptionsNumber = contractDto.getSelectedContractOptionIds().size();
        if (contractOptionsNumber>0){
            for (int i = 0; i < contractOptionsNumber; i++){
                OptionEntity option = (OptionEntity) optionDAO.findById(contractDto.getSelectedContractOptionIds().get(i));
                contractOptionService.saveContractOptionInDataBase(contract, option, this);
            }
        }
*//*        contractDAO.update(contract);
        return Optional.empty();*//*
    }*/

}
