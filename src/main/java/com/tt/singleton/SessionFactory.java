package com.tt.singleton;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * 单例模式，避免重复的创建、关闭，影响系统开销
 */
public final class SessionFactory {
    //消息服务器的连接地址
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static ActiveMQConnectionFactory connectionFactory;
    private static Connection connection;
    private Session session;

    public Session getSession() {
        return session;
    }

    public Connection getConnection() {
        return connection;
    }

    private SessionFactory() {
        //创建一个连接工厂
        connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        try {
            //创建一个连接
            connection = connectionFactory.createConnection();
            //创建一个session
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);    //param1:是否启动事务 param2:自动确认
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static SessionFactory sessionFactory = new SessionFactory();
    }

    public static SessionFactory getInstance() {
        return Holder.sessionFactory;
    }
}
