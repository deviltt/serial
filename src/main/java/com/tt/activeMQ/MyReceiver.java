package com.tt.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MyReceiver {
    private static final String url = "tcp://127.0.0.1:61616";
    private static final String queueName = "Queue4";

    public static void main(String[] args) throws JMSException {
        //创建connectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
//                byte[] bytes=new byte[31];
                System.out.println(message);
            }
        });
    }
}
