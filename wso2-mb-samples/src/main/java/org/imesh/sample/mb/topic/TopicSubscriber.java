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

package org.imesh.sample.mb.topic;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class TopicSubscriber {
    private String topicName = "SampleTopic";

    private Properties getProperties() throws IOException {
        Properties prop = new Properties();
        prop.load(TopicPublisherMain.class.getClassLoader().getResourceAsStream("jndi.properties"));
        return prop;
    }

    public void subscribe() throws NamingException, JMSException, IOException {
        // Prepare JNDI properties
        Properties properties = getProperties();
        InitialContext ctx = new InitialContext(properties);

        // Lookup connection factory
        String connectionFactoryName = properties.get("connectionfactoryName").toString();
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory) ctx.lookup(connectionFactoryName);
        TopicConnection topicConnection = connectionFactory.createTopicConnection();
        topicConnection.start();
        TopicSession topicSession = topicConnection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        // Receive message
        Topic topic = topicSession.createTopic(topicName);
        javax.jms.TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
        Message message = topicSubscriber.receive();

        // Print message
        if (message instanceof TextMessage) {
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
        topicSubscriber.close();
        topicSession.close();
        topicConnection.stop();
        topicConnection.close();
    }
}
