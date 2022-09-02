package receiver;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyReceiver {

	public static void main(String[] args) {
		try {

			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");

			Queue queue = (Queue) applicationContext.getBean("queue");

			// Create a connection. See
			// https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
			QueueConnection queueConn = factory.createQueueConnection();
			// Open a session
			QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			// start the connection
			queueConn.start();
			// Create a receive
			QueueReceiver queueReceiver = queueSession.createReceiver(queue);
			// Receive the message
			TextMessage message = (TextMessage) queueReceiver.receive();

			// print the message
			System.out.println("received: " + message.getText());

			// close the queue connection
			// queueConn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
