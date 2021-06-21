package com.ecare.services;

import com.ecare.dao.TariffDAO;
import com.ecare.domain.TariffEntity;
import com.ecare.dto.HotTariffDto;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotTariffService {
    @Autowired
    MessageSender messageSender;
    @Autowired
    TariffDAO tariffDAO;
    @Autowired
    private ModelMapper modelMapper;


    public void sendMessage() {
     //   messageSender.sendMessage("[{\"name\":\"HotBlue\",\"price\":1000},{\"name\":\"HotBlack\",\"price\":500},{\"name\":\"HotRed\",\"price\":600}]");
        messageSender.sendMessage(build());
    }

    private String build() {
        List<TariffEntity> tariffs = tariffDAO.getChampionTariffs();
        List<HotTariffDto> hotTariffs = tariffs.stream().map(this::convertToHotTariffDto)
                .collect(Collectors.toList());
        return new Gson().toJson(hotTariffs);
    }

    private HotTariffDto convertToHotTariffDto(TariffEntity tariffEntity) {
    /*    HotTariffDto hotTariff = new HotTariffDto(); //or map?!!
        hotTariff.setTariffName(tariffEntity.getTariffName());
        hotTariff.setTariffDescription(tariffEntity.getTariffDescription());
        hotTariff.setPrice(tariffEntity.getPrice());
        return hotTariff;*/
        return modelMapper.map(tariffEntity, HotTariffDto.class);
    }

    @PostConstruct
    public void postConstruct() {
        sendMessage();
    }
}
