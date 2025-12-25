package sorting;

import dto.Client;
import input.CustomCollection;

public interface SortingStrategy {

    void sort(CustomCollection<Client> clients);
    String getStrategyName();
    void sortEvenValuesOnly(CustomCollection<Client> clients);
}