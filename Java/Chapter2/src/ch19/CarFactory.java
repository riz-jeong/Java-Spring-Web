package ch19;

public class CarFactory {

    private static CarFactory instance = new CarFactory();

    private CarFactory() {
    }
    
    public Car createCar() {
        return new Car();
    }

    public static CarFactory getInstance() {
        if (instance == null) {
            instance = new CarFactory();
        }
        return instance;
    }

}
