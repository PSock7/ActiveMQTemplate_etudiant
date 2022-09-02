package sender;

import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySender {

	public static void main(String[] args) {

		try {

			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");

			Queue queue = (Queue) applicationContext.getBean("queue");

			// Create a connection. See
			// https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
			QueueConnection queueConn = factory.createQueueConnection();

			// Open a session without transaction and acknowledge automatic
			QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
			// Start the connection
			queueConn.start();
			// Create a sender
			QueueSender queueSender = queueSession.createSender(queue);
			// Persistent mode
			// queueSender.setDeliveryMode(DeliveryMode.PERSISTENT);
			//queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// Persistent mode
			 queueSender.setDeliveryMode(DeliveryMode.PERSISTENT);
			// Time to live
			 // queueSender.setTimeToLive(2000);
			// Priority
			// queueSender.setPriority(7);

			// Create a message
			TextMessage message = queueSession.createTextMessage("Hello ! love Efrei ?");
			// Send the message
			queueSender.send(message);
			System.out.println("sent: " + message.getText());
			// Close the session
			queueSession.close();
			// Close the connection
			queueConn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
