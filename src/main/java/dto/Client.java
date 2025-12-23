package dto;

public final class Client {
    private final String name;
    private final String phoneNumber;
    private final int idNumber;

    public Client(ClientBuilder clientBuilder) {
        this.name = clientBuilder.name;
        this.phoneNumber = clientBuilder.phoneNumber;
        this.idNumber = clientBuilder.idNumber;
    }

    public static class ClientBuilder{
        private String name;
        private String phoneNumber;
        private int idNumber;

        public ClientBuilder name(String name){
            this.name = name;
            return this;
        }

        public ClientBuilder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getIdNumber() {
        return idNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", idNumber=" + idNumber +
                '}';
    }
}
