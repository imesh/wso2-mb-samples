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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class TopicPublisher {
    String topicName = "SampleTopic";

    private Properties getProperties() throws IOException {
        Properties prop = new Properties();
        prop.load(TopicPublisherMain.class.getClassLoader().getResourceAsStream("jndi.properties"));
        return prop;
    }

    public void publishMessage(Object message) throws NamingException, JMSException, IOException {
        // Prepare JNDI properties
        Properties properties = getProperties();
        InitialContext ctx = new InitialContext(properties);

        // Lookup connection factory
        String connectionFactoryName = properties.get("connectionfactoryName").toString();
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory) ctx.lookup(connectionFactoryName);
        TopicConnection topicConnection = connectionFactory.createTopicConnection();
        topicConnection.start();
        TopicSession topicSession = topicConnection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        // Create topic
        Topic topic = topicSession.createTopic(topicName);

        // Send message
        if(message instanceof String) {
            TextMessage textMessage = topicSession.createTextMessage((String)message);
            javax.jms.TopicPublisher topicPublisher = topicSession.createPublisher(topic);
            topicPublisher.publish(textMessage);

            System.out.println("Text message sent: " + (String)message);
        }
        else if(message instanceof Serializable) {
            ObjectMessage objectMessage = topicSession.createObjectMessage((Serializable) message);
            javax.jms.TopicPublisher topicPublisher = topicSession.createPublisher(topic);
            topicPublisher.publish(objectMessage);

            System.out.println("Object message sent: " + ((Serializable)message).toString());
        }
        else {
            throw new RuntimeException("Unknown message type");
        }

        // Clean up
        topicSession.close();
        topicConnection.close();
    }
}
