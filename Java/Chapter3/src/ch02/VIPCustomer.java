package ch02;

public class VIPCustomer extends Customer {

    private String agentID;
    double saleRatio;

    public VIPCustomer() {

        bonusRatio = 0.05;
        saleRatio = 0.1;
        customerGrade = "VIP";
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
    
}
