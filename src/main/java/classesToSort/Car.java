package classesToSort;

public final class Car {
    private final String model;
    private final int productionYear;
    private final int horsePower;

    public Car(CarBuilder carBuilder) {
        this.model = carBuilder.model;
        this.productionYear = carBuilder.productionYear;
        this.horsePower = carBuilder.horsePower;
    }

    public static class CarBuilder{ // МОЖНО ДЕЛАТЬ ЕГО PUBLIC?
        private String model;
        private int productionYear;
        private int horsePower;

        public CarBuilder model(String model){
            this.model = model;
            return this;
        }

        public CarBuilder productionYear(int productionYear){
            this.productionYear = productionYear;
            return this;
        }

        public CarBuilder horsePower(int horsePower){
            this.horsePower = horsePower;
            return this;
        }

        public Car build(){
            return new Car(this);
        }


    }

    public String getModel() {
        return model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public int getHorsePower() {
        return horsePower;
    }
}
