package com.ecare.services;

import com.ecare.dto.HotTariffDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HotTariffService {
    @Autowired
    MessageSender messageSender;
   /* @Autowired
    TariffService tariffService;*/


    public void sendMessage() {
            messageSender.sendMessage("[{\"name\":\"HotBlue\",\"price\":1000},{\"name\":\"HotBlack\",\"price\":500},{\"name\":\"HotRed\",\"price\":600}]");
      //  messageSender.sendMessage(build());
    }

    private String build() {
    /*    List<HotTariffDto> hotTariffs = tariffService.getChampionTariffs();
        return new Gson().toJson(hotTariffs);*/
        return "";
    }

    @PostConstruct
    public void postConstruct() {
        sendMessage();
    }
}
