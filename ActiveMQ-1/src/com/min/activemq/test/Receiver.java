package com.min.activemq.test;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
* 消息接收者，内部使用线程创建Connection
* @author min.zhao
* @E-mail:zhuifeng1017@gmail.com
* @version 创建时间：2014-3-6 下午1:37:37
 */
public class Receiver extends Thread{
	 // ConnectionFactory ：连接工厂，JMS 用它创建连接
	ActiveMQConnectionFactory connectionFactory;
    // Connection ：JMS 客户端到JMS Provider 的连接
    private Connection connection = null;
    // Session： 一个发送或接收消息的线程
    private Session session;
    MessageConsumer consumer;
    private boolean running = false;
    private  MessageListener messageListener;
    
    public Receiver(){
    	messageListener  = new MessageListener() {
    		@Override
    		public void onMessage(Message message) {
    			if (null != message) {
    				System.out.println(Thread.currentThread().getName() + "--收到消息" + message);
    			}
    		}
    	};
    }
    
    /**
     * @param messageListener
     */
    public void setMessageListener(MessageListener messageListener){
    	this.messageListener = messageListener;
    }
    
    /**
     * 启动接收消息
     */
    public void startRecv(){
    	if (!this.running){
    		//this.setDaemon(true);
    		super.start();
    		this.running = true;
    	}
    }
    
    /**
     * 停止接收消息
     */
    public void stopRecv(){
    	try {
    		if (null !=consumer){
    			consumer.setMessageListener(null);
    			consumer.close();
    			consumer = null;
    		}
			if (null != session) {
				session.close();
				session = null;
			}
			if (null != connection) {
				connection.close();
				connection = null;
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	this.running = false;
    }
    
	public void run() {
		try {
			 connectionFactory = new ActiveMQConnectionFactory(
			// ActiveMQConnection.DEFAULT_USER,
			// ActiveMQConnection.DEFAULT_PASSWORD,
					"system", "manager",
					// "tcp://localhost:61616"
					"tcp://192.168.108.13:61616");
			
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// Destination ：消息的目的地;消息发送给谁.
			Destination destination = session.createQueue("FirstQueue");
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(this.messageListener);
		} catch (Exception e) {
			e.printStackTrace();
			this.stopRecv();// 出异常就停止接收消息并作清理
		}
	}
}



