package ch14;

public class Subway {
    
    int lineNumber;
    int passangerCount;
    int money;

    public Subway(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void take(int money) {
        this.money += money;
        passangerCount++;
    }

    public void showInfo() {
        System.out.println(lineNumber + "호선의 승객 수는 " + passangerCount + "명 이고, 수입은 " + money + "원 입니다.");
    }
}
