/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.imesh.sample.mb.queue;

import org.imesh.sample.mb.topic.TopicPublisherMain;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class QueueReceiver {
    String queueName = "SampleQueue";

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(TopicPublisherMain.class.getClassLoader().getResourceAsStream("jndi.properties"));
        properties.put("queue." + queueName, queueName);
        return properties;
    }

    public void receiveMessages() throws NamingException, JMSException, IOException {
        Properties properties = getProperties();
        InitialContext ctx = new InitialContext(properties);

        // Lookup connection factory
        String connectionFactoryName = properties.getProperty("connectionfactoryName");
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup(connectionFactoryName);
        QueueConnection connection = connectionFactory.createQueueConnection();
        connection.start();
        QueueSession queueSession = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        // Receive message
        Queue queue =  (Queue) ctx.lookup(queueName);
        MessageConsumer queueReceiver = queueSession.createConsumer(queue);
        Message message = queueReceiver.receive();

        // Print message
        if(message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Text message received: " + textMessage.getText());
        }
        else if(message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            System.out.println("Object message received: " + objectMessage.toString());
        }
        else {
            throw new RuntimeException("Unknown message type");
        }

        // Clean up resources
        queueReceiver.close();
        queueSession.close();
        connection.stop();
        connection.close();
    }
}
