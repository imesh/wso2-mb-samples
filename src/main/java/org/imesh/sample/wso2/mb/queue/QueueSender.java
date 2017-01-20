
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

package org.imesh.sample.wso2.mb.queue;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * Implements functionality for sending messages to a given queue.
 */
public class QueueSender {
    private String queueName;
    private QueueConnection connection;
    private QueueSession queueSession;
    private javax.jms.QueueSender queueSender;
    private Queue queue;

    public QueueSender(String queueName) {
        this.queueName = queueName;
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(QueueSender.class.getClassLoader().getResourceAsStream("jndi.properties"));
        properties.put("queue." + queueName, queueName);
        return properties;
    }

    public void connect() throws IOException, NamingException, JMSException {
        Properties properties = getProperties();
        InitialContext ctx = new InitialContext(properties);

        // Lookup connection factory
        String connectionFactoryName = properties.getProperty("connectionfactoryName");
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup(connectionFactoryName);
        connection = connectionFactory.createQueueConnection();
        connection.start();
        queueSession = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        // Create queue
        queueSession.createQueue(queueName);

        // Lookup queue
        queue = (Queue) ctx.lookup(queueName);
    }

    public void sendMessage(Object message) throws JMSException {
        // Send message
        if (message instanceof String) {
            TextMessage textMessage = queueSession.createTextMessage((String) message);
            queueSender = queueSession.createSender(queue);
            queueSender.send(textMessage);

            System.out.println("Text message sent: " + message);
        } else if (message instanceof Serializable) {
            ObjectMessage objectMessage = queueSession.createObjectMessage((Serializable) message);
            queueSender = queueSession.createSender(queue);
            queueSender.send(objectMessage);

            System.out.println("Object message sent: " + message.toString());
        } else {
            throw new RuntimeException("Unknown message type");
        }
    }

    public void close() throws JMSException {
        // Clean up resources
        if (queueSender != null)
            queueSender.close();
        if (queueSession != null)
            queueSession.close();
        if (connection != null)
            connection.close();
    }
}