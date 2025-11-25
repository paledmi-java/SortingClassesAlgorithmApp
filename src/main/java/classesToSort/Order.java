package classesToSort;

import java.time.LocalDate;

// СДЕЛАТЬ BUILDER
public final class Order {
    private final Client client;
    private final LocalDate rentDate;
    private final int price;

    public Order(OrderBuilder orderBuilder) {
        this.client = orderBuilder.client;
        this.rentDate = orderBuilder.rentDate;
        this.price = orderBuilder.price;
    }

    public static class OrderBuilder{
        private Client client;
        private LocalDate rentDate;
        private int price;

        public OrderBuilder client(Client client){ // Тут устанавливается Класс
            this.client = client;
            return this;
        }

        public OrderBuilder rentDate(LocalDate rentDate){
            this.rentDate = rentDate;
            return this;
        }

        public OrderBuilder price(int price){
            this.price = price;
            return this;
        }
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public int getPrice() {
        return price;
    }
}
