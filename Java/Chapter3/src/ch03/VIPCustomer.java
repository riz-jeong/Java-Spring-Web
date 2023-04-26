package ch03;

public class VIPCustomer extends Customer {

    private String agentID;
    double saleRatio;

    // public VIPCustomer() {

    //     bonusRatio = 0.05;
    //     saleRatio = 0.1;
    //     customerGrade = "VIP";

    //     System.out.println("VIPCustomer() call");
    // }

    public VIPCustomer(int customerID, String customerName) {
        super(customerID, customerName);

        customerGrade = "VIP";
        bonusRatio = 0.01;
        saleRatio = 0.1;

        System.out.println("VIPCustomer(int, String) call");
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
    
}
