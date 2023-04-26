package ch04;

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
    }

    @Override
    public int calcPrice(int price) {
        bonusPoint += price * bonusRatio;
        price -= (int)(price * saleRatio);
        return price;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
    
}
