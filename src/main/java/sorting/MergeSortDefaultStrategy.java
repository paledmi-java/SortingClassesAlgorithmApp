package sorting;

import dto.Client;
import input.CustomCollection;

import java.util.Comparator;

public class MergeSortDefaultStrategy extends AbstractMergeSortStrategy {

    @Override
    public void sort(CustomCollection<Client> clients) {
        sortWithComparator(clients, getComparator());
    }

    protected Comparator<Client> getComparator() {
        return (c1, c2) -> {
            int nameComparison = c1.getName().compareTo(c2.getName());
            if (nameComparison != 0) return nameComparison;

            int idComparison = Integer.compare(c1.getIdNumber(), c2.getIdNumber());
            if (idComparison != 0) return idComparison;

            return c1.getPhoneNumber().compareTo(c2.getPhoneNumber());
        };

    }

    @Override
    public String getStrategyName() {
        return "Merge Sort Default Strategy (сортировка по имени -> ID -> телефону)";
    }
}
