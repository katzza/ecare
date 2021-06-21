package com.example;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
/*@Singleton*/
@ApplicationScoped
@Named
public class WebSocket {
    private static final String URI = "http://localhost:8080/hotTariffs";
    @Inject
    @Push(channel = "websocket")
    private PushContext pushContext;
    private List<Tariff> tariffs = new CopyOnWriteArrayList<>();

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs.clear();
        this.tariffs = tariffs;
    }

    public void sendMessage(String message) {
        log.info("send tariff data");
        pushContext.send(message);
    }

    @PostConstruct
    public void onStartUp() {
        try {
            URL url = new URL(URI);
            HttpURLConnection connection = (HttpURLConnection) (url.openConnection());
            int status = connection.getResponseCode();
            log.info("getting data on startup, response status: " + status);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
