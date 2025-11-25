import classesToSort.Car;

public class Main {
    public static void main(String[] args) {
        Car car = new Car.CarBuilder().model("Toyota")
                .productionYear(1999).horsePower(300).build();
    }
}
