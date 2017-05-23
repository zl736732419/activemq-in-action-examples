package org.apache.activemq.book.ch2.portfolio;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {

    private static String brokerURL = "tcp://192.168.1.200:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    private static final String[] destinations = new String[] {"CSCO", "ORCL"};
    
    
    public Consumer() throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
    public static void main(String[] args) throws JMSException {
    	Consumer consumer = new Consumer();
    	for (String stock : destinations) {
    		Destination destination = consumer.getSession().createTopic("STOCKS." + stock);
    		MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
    		messageConsumer.setMessageListener(new Listener());
    	}
    	System.out.println("消息接受者准备好了!!!");
    }
	
	public Session getSession() {
		return session;
	}

}
