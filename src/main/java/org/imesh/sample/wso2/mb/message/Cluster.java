package org.imesh.sample.wso2.mb.message;

import java.io.Serializable;
import java.util.*;

public class Cluster implements Serializable {
    private String hostName;
    private int tenantStart;
    private int tenantEnd;
    private boolean starTenant;
    private String cartridgeType;
    private Map<String, String> autoScalingParams;
    private List<Member> members;

    public Cluster() {
        this.autoScalingParams = new HashMap<String, String>();
        this.members = new ArrayList<Member>();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getTenantStart() {
        return tenantStart;
    }

    public void setTenantStart(int tenantStart) {
        this.tenantStart = tenantStart;
    }

    public int getTenantEnd() {
        return tenantEnd;
    }

    public void setTenantEnd(int tenantEnd) {
        this.tenantEnd = tenantEnd;
    }

    public boolean isStarTenant() {
        return starTenant;
    }

    public void setStarTenant(boolean starTenant) {
        this.starTenant = starTenant;
    }

    public String getCartridgeType() {
        return cartridgeType;
    }

    public void setCartridgeType(String cartridgeType) {
        this.cartridgeType = cartridgeType;
    }

    public Map<String, String> getAutoScalingParams() {
        return autoScalingParams;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(Member member) {
        members.remove(member);
    }
}
