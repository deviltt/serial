package com.tt.activeMQ;

import com.tt.entity.DestinationData;
import com.tt.singleton.SessionFactory;

import javax.jms.*;
import java.util.Arrays;

/**
 * @author tt
 * 作用：从ActiveMQ的队列中获取数据
 */
public class Receiver {
    private static SessionFactory sessionFactory = SessionFactory.getInstance();
    private static Session session = sessionFactory.getSession();
    private static Connection connection = sessionFactory.getConnection();
    private static int sum;
    private static DestinationData destinationData = new DestinationData();
    public static void main(String[] args) {
        try {
            //接收消息前启动消息
            connection.start();
            //创建一个目的地
            Destination destination = session.createQueue("Queue");
            //创建一个消费者
            MessageConsumer consumer = session.createConsumer(destination);

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    BytesMessage bytesMessage = (BytesMessage) message;
                    //开线程处理
                    SubReceiver subReceiver = new SubReceiver(bytesMessage);
                    subReceiver.start();
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void bytesToHexString(byte[] bytes, DestinationData destinationData) {
        for (int i = 0; i < bytes.length; i++) {
            switch (i) {
                case 14:
                    destinationData.setHour(bytes[i]);
                    break;
                case 15:
                    destinationData.setMinute(bytes[i]);
                    break;
                case 16:
                    destinationData.setSecond(bytes[i]);
                    break;
                case 17:
                    destinationData.setMicrosecond(bytes[i]);
                    break;
                case 18:
                    destinationData.setLongitudeDegree(bytes[i]);
                    break;
                case 19:
                    destinationData.setLongitudeMinute(bytes[i]);
                    break;
                case 20:
                    destinationData.setLongitudeSecond(bytes[i]);
                    break;
                case 21:
                    destinationData.setLongitudeMicrosecond(bytes[i]);
                    break;
                case 22:
                    destinationData.setLatitudeDegree(bytes[i]);
                    break;
                case 23:
                    destinationData.setLatitudeMinute(bytes[i]);
                    break;
                case 24:
                    destinationData.setLatitudeSecond(bytes[i]);
                    break;
                case 25:
                    destinationData.setLatitudeMicrosecond(bytes[i]);
                    break;
                default:
                    break;
            }
        }
    }

    private static class SubReceiver extends Thread {
        private BytesMessage bytesMessage;
        byte[] bytes = new byte[300];
        SubReceiver(BytesMessage bytesMessage) {
            this.bytesMessage = bytesMessage;
        }

        @Override
        public void run() {
            super.run();
            try {
                bytesMessage.readBytes(bytes);
            } catch (JMSException e) {
                e.printStackTrace();
            }
//            bytesToHexString(bytes, destinationData);
//            System.out.println("sum: " + (++sum) + " " + "接收到的消息是：" + destinationData);
            System.out.println("sum: " + (++sum) + " " + "接收到的消息是：" + Arrays.toString(bytes));
        }
    }
}
