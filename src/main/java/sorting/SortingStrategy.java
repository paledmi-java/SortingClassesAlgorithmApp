package sorting;

import dto.Client;
import java.util.Comparator;
import java.util.List;

public interface SortingStrategy {


    void sort(List<Client> clients);
    void sortWithComparator(List<Client> clients, Comparator<Client> comparator);
    String getStrategyName();
}