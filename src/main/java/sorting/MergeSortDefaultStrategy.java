package sorting;

import dto.Client;

import java.util.Comparator;
import java.util.List;

public class MergeSortDefaultStrategy extends AbstractMergeSortStrategy{

    @Override
    public void sort(List<Client> clients) {
        Comparator<Client> comparator = createDefaultComparator();
        sortWithComparator(clients, comparator);
    }

    private Comparator<Client> createDefaultComparator() {
        return new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                int nameComparison = client1.getName().compareTo(client2.getName());
                if (nameComparison != 0) {
                    return nameComparison;
                }

                int idComparison = Integer.compare(client1.getIdNumber(), client2.getIdNumber());
                if (idComparison != 0) {
                    return idComparison;
                }

                return client1.getPhoneNumber().compareTo(client2.getPhoneNumber());
            }
        };
    }

    @Override
    public String getStrategyName() {
        return "Merge Sort Default Strategy (сортировка по имени -> ID -> телефону)";
    }
}
