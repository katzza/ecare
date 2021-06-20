package com.ecare.services;

import com.ecare.dao.OptionDAO;
import com.ecare.domain.OptionEntity;
import com.ecare.dto.OptionDto;
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
public class OptionService {
    @Autowired
    OptionDAO optionDAO;
    @Autowired
    private ModelMapper modelMapper;

    public List<OptionDto> getAllOptions() {
        List<OptionEntity> options = optionDAO.findNonBaseOptions();

        return options.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<String> saveOption(OptionDto optionDto) {  //todo: save - return object or Id, error - exception
        if (optionDAO.findByName(optionDto.getOptionName()).size() > 0) {
            log.info("Option {} already exist", optionDto.getOptionName());
            return Optional.of(String.format("Option %s already exist", optionDto.getOptionName()));
        }
        OptionEntity option = convertToEntity(optionDto);
        optionDAO.save(option);
        return Optional.empty();
    }

    public Optional<String> updateOption(OptionDto optionDto) { //todo: update - return object or Id, error - exception
        OptionEntity option = (OptionEntity) optionDAO.findById(optionDto.getOptionId());
        option.setOptionName(optionDto.getOptionName());
        option.setDescription(optionDto.getDescription());
        option.setBaseOptionId(optionDto.getBaseOptionId());
        option.setConnectionPrice(optionDto.getConnectionPrice());
        option.setMonthPrice(optionDto.getMonthPrice());
        option.setMulti(optionDto.isMulti());
        optionDAO.update(option);
        return Optional.empty();
    }

    public void showBaseOptions(OptionDto optionDto) {
        Map<String, Integer> mapOptions = optionDto.getBaseOptions();
        optionDAO.getBaseOptions().forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
    }

    public OptionDto findById(int optionId) {
        try {
            OptionEntity optionEntity = (OptionEntity) optionDAO.findById(optionId);
            return convertToDto(optionEntity);
        } catch (Exception ex) {
            OptionEntity optionEntity = new OptionEntity();
            return convertToDto(optionEntity);
        }

    }

    public OptionDto getMainOptionByBaseAndTariffId(int baseOptionId, int tariffId) {
        log.info("getUniqueOptionByBaseAndTariffId(baseOptionId, tariffId) {}, {} ", +baseOptionId, tariffId);
        List<OptionEntity> option = optionDAO.getByBaseIsMultuAndTariffId(baseOptionId, false, tariffId);
        if (option.size() > 0) {
            return convertToDto(option.get(0));
        } else
            log.info("return null");
        return null;
    }

    public List<OptionDto> getMultiOptionsByBaseAndTariffId(int baseOptionId, int tariffId) {
        log.info("getMultiOptionsByBaseAndTariffId (baseOptionId, tariffId) {}, {}", baseOptionId, tariffId);
        List<OptionEntity> options = optionDAO.getByBaseIsMultuAndTariffId(baseOptionId, true, tariffId);
        return getOptionDtos(options);
    }

    public List<OptionDto> showTariffAddedMultiOptions(int tariffId) {
        List<OptionEntity> options = optionDAO.getTariffAddedMultiAndFreeoptions(tariffId);
        return getOptionDtos(options);
    }

    private List<OptionDto> getOptionDtos(List<OptionEntity> options) {
        return options.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    private OptionDto convertToDto(OptionEntity optionEntity) {
        OptionDto optionDto = modelMapper.map(optionEntity, OptionDto.class);
        if (optionEntity.getBaseOptionId() != 0) {
            optionDto.setBaseOptionName(((OptionEntity) optionDAO.findById(optionEntity.getBaseOptionId())).getOptionName());
        }
        return optionDto;
    }

    private OptionEntity convertToEntity(OptionDto optionDto) {

        return modelMapper.map(optionDto, OptionEntity.class);
    }


    public OptionDto getOptionDtoShowBaseOptions() {
        OptionDto optionDto = new OptionDto();
        showBaseOptions(optionDto);
        return optionDto;
    }

    public OptionDto getOptionByIdShowBaseOptions(int optionId) {
        OptionDto optionDto = findById(optionId);
        showBaseOptions(optionDto);
        return optionDto;
    }
}
