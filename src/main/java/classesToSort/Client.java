package classesToSort;

import java.util.ArrayList;
import java.util.List;

public final class Client {
    private final String name;
    private final Credentials credentials;
    private final int idNumber;
//    private final List<Order> orderHistory = new ArrayList<>();

    public Client(ClientBuilder clientBuilder) {
        this.name = clientBuilder.name;
        this.credentials = clientBuilder.credentials;
        this.idNumber = clientBuilder.idNumber;
    }

    public static class ClientBuilder{
        private String name;
        private Credentials credentials;
        private int idNumber;

        public ClientBuilder name(String name){
            this.name = name;
            return this;
        }

        private ClientBuilder credentials(Credentials credentials){
            this.credentials = credentials;
            return this;
        }

        public ClientBuilder idNumber(int idNumber){
            this.idNumber = idNumber;
            return this;
        }

        public Client build(){
            return new Client(this);
        }

    }

    public String getName() {
        return name;
    }

    private Credentials getCredentials() { // public or private???
        return credentials;
    }

    public int getIdNumber() {
        return idNumber;
    }

//    public List<Order> getOrderHistory() {
//        return List.copyOf(orderHistory);
//    }

    private static class Credentials{ // ДЕЛАТЬ ВЛОЖЕННЫМ ИЛИ НЕТ
        private final String phoneNumber;
        private final String address;
        private final String email;

        public Credentials(CredBuilder credBuilder) {
            this.phoneNumber = credBuilder.phoneNumber;
            this.address = credBuilder.address;
            this.email = credBuilder.email;
        }

        public static class CredBuilder{
            private String phoneNumber;
            private String address;
            private String email;

            public CredBuilder phoneNumber(String phoneNumber){
                this.phoneNumber = phoneNumber;
                return this;
            }

            public CredBuilder address(String address){
                this.address = address;
                return this;
            }

            public CredBuilder email(String email){
                this.email = email;
                return this;
            }

            public Credentials build(){
                return new Credentials(this);
            }
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public String getEmail() {
            return email;
        }
    }
}
