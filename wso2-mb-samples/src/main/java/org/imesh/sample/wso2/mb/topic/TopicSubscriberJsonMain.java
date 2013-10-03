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

package org.imesh.sample.wso2.mb.topic;

import com.google.gson.Gson;
import org.imesh.sample.wso2.mb.message.Topology;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class TopicSubscriberJsonMain {
    public static void main(String[] args) {

        TopicSubscriber subscriber = null;
        try {
            subscriber = new TopicSubscriber("SampleTopic");
            subscriber.subscribe();

            while (true) {
                Message message = subscriber.receive();
                if (message != null) {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String jsonMessage = textMessage.getText();
                        System.out.println("Message received: " + jsonMessage);

                        try {
                            Gson gson = new Gson();
                            Topology result = gson.fromJson(jsonMessage, Topology.class);
                            System.out.println("Object created from json: " + result.toString());
                        }
                        catch (Exception e) {
                            // Could not parse received message to json
                            e.printStackTrace();
                        }
                    }
                    else {
                        throw new RuntimeException("Unknown message type");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(subscriber != null) {
                try {
                    subscriber.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
