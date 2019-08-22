package com.tt.activeMQ;

import com.tt.singleton.SessionFactory;

import javax.jms.*;

/**
 * @author tt
 */
public class Sender {
    //单例工厂
    private static SessionFactory sessionFactory = SessionFactory.getInstance();
    //计数发了多少条数据
    private static int num = 0;

    //使用单例线程不需要同步方法
    public static synchronized void sendMessage(byte[] msg) {
        //开启一个consumer线程来消费
//        Receiver receiver = new Receiver();
//        receiver.start();

        MessageProducer messageProducer = null;
        try {
            //创建一个消息
            BytesMessage bytesMessage = sessionFactory.getSession().createBytesMessage();
            bytesMessage.writeBytes(msg);
            //创建一个目的地
            Destination destination = sessionFactory.getSession().createQueue("Queue");
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
