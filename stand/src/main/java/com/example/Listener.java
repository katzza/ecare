package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.lang.reflect.Type;
import java.util.ArrayList;

@MessageDriven(activationConfig = {@ActivationConfigProperty(
        propertyName = "destination",   //standalone file
        propertyValue = "java:/test/MyQueue"
)})

@Slf4j
public class Listener implements MessageListener {
    @Inject
    WebSocket webSocket;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
                try {
                String messageText = textMessage.getText();
                log.info(messageText);
                Type listType = new TypeToken<ArrayList<Tariff>>() {
                }.getType();
                webSocket.setTariffs(new Gson().fromJson(messageText, listType));
                log.info("MESSAGE: " + messageText);
                webSocket.sendMessage("update");
            } catch (JMSException e) {
                System.out.println(
                        "Error: " + e.getMessage());
            }
        }
    }

}
