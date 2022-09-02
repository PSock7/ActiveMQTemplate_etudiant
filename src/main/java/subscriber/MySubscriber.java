package subscriber;

import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySubscriber {

	public static void main(String[] args) {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			TopicConnectionFactory factory = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");

			Topic topic = (Topic) applicationContext.getBean("topic");

			// Create a connection. See
			// https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
			TopicConnection topicConn = factory.createTopicConnection();
			// Open a session
			TopicSession topicSession = topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			// start the connection
			topicConn.start();
			// Create a subscriber
			TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
			// Receive the message
			TextMessage message = (TextMessage) topicSubscriber.receive();

			// print the message
			System.out.println("received: " + message.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
