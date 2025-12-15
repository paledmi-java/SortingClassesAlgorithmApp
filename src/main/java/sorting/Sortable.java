package sorting;

import dto.Client;

import java.util.Comparator;
import java.util.List;

public interface Sortable {

    void sort(List<Client> clients, Comparator<Client> comparator);

}
