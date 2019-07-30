package com.tt.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author tt
 */
public class Sender {
    //消息服务器的连接地址
    private static final String BROKER_URL = "tcp://localhost:61616";

    public static void sendMessage(byte[] msg) {
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;
        try {
            //创建一个连接工厂
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            //创建一个连接
            connection = connectionFactory.createConnection();
            //创建一个session
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);    //param1:是否启动事务 param2:自动确认
            //创建一个消息
            BytesMessage bytesMessage = session.createBytesMessage();
            bytesMessage.writeBytes(msg);
            //创建一个目的地
            Destination destination = session.createQueue("Queue2");
            //创建一个消息的生产者
            messageProducer = session.createProducer(destination);  //生产者将send的消息发送到destination
            //发送消息
            messageProducer.send(bytesMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (messageProducer != null) {
                    messageProducer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        Sender sender=new Sender();
//        sender.sendMessage("你好");
//        System.out.println("发送成功");
//    }
}
