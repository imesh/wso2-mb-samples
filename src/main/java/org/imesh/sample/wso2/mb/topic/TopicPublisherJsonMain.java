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

import org.imesh.sample.wso2.mb.message.Cluster;
import org.imesh.sample.wso2.mb.message.Member;
import org.imesh.sample.wso2.mb.message.Topology;
import com.google.gson.Gson;

import javax.jms.JMSException;

public class TopicPublisherJsonMain {
    public static void main(String[] args) {
        TopicPublisher publisher = null;
        try {
            publisher = new TopicPublisher("SampleTopic");
            publisher.connect();
            publisher.sendMessage(generateJson());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (publisher != null)
                try {
                    publisher.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
        }
    }

    private static String generateJson() {
        Topology topology = new Topology();
        Cluster cluster1 = new Cluster();
        cluster1.setHostName("tomcat.demo.org");
        cluster1.setTenantStart(1);
        cluster1.setTenantEnd(100);
        cluster1.setCartridgeType("Openstack");
        topology.addCluster(cluster1);

        Member m1 = new Member();
        m1.setIpAddress("10.0.0.1");
        m1.setHttpPort(80);
        m1.setHttpProxyPort(8280);
        m1.setHttpsPort(90);
        m1.setHttpsProxyPort(8290);
        cluster1.addMember(m1);

        Member m2 = new Member();
        m2.setIpAddress("10.0.0.2");
        m2.setHttpPort(80);
        m2.setHttpProxyPort(8280);
        m2.setHttpsPort(90);
        m2.setHttpsProxyPort(8290);
        cluster1.addMember(m2);

        Gson gson = new Gson();
        return gson.toJson(topology);
    }
}
