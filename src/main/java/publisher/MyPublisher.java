package publisher;

import javax.jms.DeliveryMode;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyPublisher {

	public static void main(String[] args) {

		try {

			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			TopicConnectionFactory factory = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");

			Topic topic = (Topic) applicationContext.getBean("topic");

			// Create a connection. See
			// https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
			TopicConnection topicConn = factory.createTopicConnection();
			// Open a session without transaction and acknowledge automatic
			TopicSession topicSession = topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			// Start the connection
			topicConn.start();
			// Create a publisher
			TopicPublisher topicPublisher = topicSession.createPublisher(topic);
			topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// Create a message
			TextMessage message = topicSession.createTextMessage();
			message.setText("Hello World");
			// publish the messages
			topicPublisher.publish(message);
			System.out.println("published: " + message.getText());

			// Close the session
			topicSession.close();
			// Close the connection
			topicConn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}