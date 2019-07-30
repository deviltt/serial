package com.tt.activeMQ;

import com.tt.singleton.SessionFactory;

import javax.jms.*;

/**
 * @author tt
 */
public class Sender {
    //消息服务器的连接地址
    private static final String BROKER_URL = "tcp://localhost:61616";
    //单例工厂
    private static SessionFactory sessionFactory=SessionFactory.getInstance();
    //计数发了多少条数据
    private static int num=0;
    public static synchronized void sendMessage(byte[] msg) {
        MessageProducer messageProducer = null;
        try {
            //创建一个消息
            BytesMessage bytesMessage = sessionFactory.getSession().createBytesMessage();
            bytesMessage.writeBytes(msg);
            //创建一个目的地
            Destination destination = sessionFactory.getSession().createQueue("Queue4");
            //创建一个消息的生产者
            messageProducer = sessionFactory.getSession().createProducer(destination);  //生产者将send的消息发送到destination
            //发送消息
            messageProducer.send(bytesMessage);

            System.out.println(++num);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
//        } finally {
//            try {
//                if (messageProducer != null) {
//                    messageProducer.close();
//                }
//                if (session != null) {
//                    session.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
