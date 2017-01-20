package org.imesh.sample.wso2.mb.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topology implements Serializable {
    private List<Cluster> clusters;

    public Topology() {
        this.clusters = new ArrayList<Cluster>();
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public void addCluster(Cluster cluster) {
        this.clusters.add(cluster);
    }

    public void removeCluster(Cluster cluster) {
        this.clusters.remove(cluster);
    }
}
