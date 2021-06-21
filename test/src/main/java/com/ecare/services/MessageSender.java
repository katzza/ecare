package com.ecare.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Slf4j
@Service
public class MessageSender {

    private static final long TIME_TO_LIVE = 30_000; //messages

    @Resource(lookup = "java:/test/MyConnectionFactory")//inject with JNDI, resource
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/test/MyQueue") //Queue
    private Destination destination;

    /**
     * Send message to JMS queue
     *
     * @param json message content in json formatted String
     */

    public void sendMessage(String json) {
        try (
                QueueConnection connection = (QueueConnection) connectionFactory.createConnection();
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
               /* QueueConnection connection = connectionFactory.createQ*/ ///стр 8 JMS-pdf
                MessageProducer producer = session.createProducer(destination)//connect to Queue's name
        ) {
            TextMessage message = session.createTextMessage(json);
            producer.setTimeToLive(TIME_TO_LIVE);
            producer.send(message);
            log.info("Send jms:" + json);

        } catch (JMSException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
