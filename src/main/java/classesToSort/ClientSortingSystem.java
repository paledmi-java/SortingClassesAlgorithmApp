package classesToSort;

import java.util.ArrayList;
import java.util.List;

public class ClientSortingSystem {
    private final List<Client> clientList = new ArrayList<>();

    public List<Client> getClientList() {
        return List.copyOf(clientList);
    }
}
