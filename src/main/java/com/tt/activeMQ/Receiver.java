package com.tt.activeMQ;

import com.tt.entity.DestinationData;
import com.tt.serial.Serial;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author tt
 */
public class Receiver extends Thread{
    private static final String BROKER_URL = "tcp://localhost:61616";

    public static void receiveMessage() {
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        DestinationData destinationData = new DestinationData();

        try {
            //创建一个工厂
            ConnectionFactory factory=new ActiveMQConnectionFactory(BROKER_URL);
            //创建一个连接
            connection=factory.createConnection();
            //创建session
            session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个目的地
            Destination destination=session.createQueue("Queue2");
            //创建一个消费者
            consumer=session.createConsumer(destination);
            //接收消息前启动消息
            connection.start();
            //接收消息
            Message message=consumer.receive();
            if (message instanceof BytesMessage){
                byte[] bytes=new byte[Serial.getBuffer().length];
                BytesMessage bytesMessage= (BytesMessage) message;
                bytesMessage.readBytes(bytes);
                bytesToHexString(bytes, destinationData);
                System.out.println("接收到的消息是："+destinationData);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try {
                if (consumer != null) {
                    consumer.close();
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

    private static void bytesToHexString(byte[] bytes, DestinationData destinationData) {
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

//    public static void main(String[] args) {
//        Receiver receiver=new Receiver();
//        receiveMessage();
//    }
}
