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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class QueueReceiverMain {
    public static void main(String[] args) {
        QueueReceiver queueReceiver = null;
        try {
            queueReceiver = new QueueReceiver("SampleQueue");
            queueReceiver.connect();

            int count = 0;
            while (true) {
                if(count == 10)
                    break; // Read 10 messages and exit

                Message message = queueReceiver.receiveMessage();
                count++;

                // Print message
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Text message received: " + textMessage.getText());
                } else if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    System.out.println("Object message received: " + objectMessage.toString());
                } else {
                    throw new RuntimeException("Unknown message type");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(queueReceiver != null)
                    queueReceiver.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
