package com.ecare.services;

import com.ecare.dao.TariffDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ecare.domain.TariffEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HotTariffService {
    @Autowired
    MessageSender messageSender;
    @Autowired
    TariffDAO tariffDAO;

    public void sendMessage() {
        messageSender.sendMessage("{Tariff [id=" + 1 + ", name=" + "name" + ", price=" + "50" +"]}"
        // messageSender.sendMessage(build(tariff));
        );
    }

    private String build() {
        List<TariffEntity> hotTariffs = tariffDAO.getTariffByTariffId(1);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(hotTariffs);
    }

    @PostConstruct
    public void postConstruct() {
        sendMessage();
    }
}
