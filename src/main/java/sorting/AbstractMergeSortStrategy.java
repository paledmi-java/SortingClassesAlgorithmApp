package sorting;

import dto.Client;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractMergeSortStrategy implements SortingStrategy {

    public void sortWithComparator(List<Client> clients, Comparator<Client> comparator) {
        if (clients == null || clients.size() <= 1) {
            return;
        }

        mergeSort(clients, 0, clients.size() - 1, comparator);
    }

    @Override
    public String getStrategyName() {
        return "Abstract Merge Sort (сортировка слиянием)";
    }

    private void mergeSort(List<Client> clients, int left, int right,
                           Comparator<Client> comparator) {

        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(clients, left, mid, comparator);

            mergeSort(clients, mid + 1, right, comparator);

            merge(clients, left, mid, right, comparator);
        }
    }


    private void merge(List<Client> clients, int left, int mid, int right,
                       Comparator<Client> comparator) {

        List<Client> temp = new ArrayList<>();

        int i = left;
        int j = mid + 1;


        while (i <= mid && j <= right) {
            Client leftClient = clients.get(i);
            Client rightClient = clients.get(j);


            int comparison = comparator.compare(leftClient, rightClient);


            if (comparison <= 0) {
                temp.add(leftClient);
                i++;
            } else {
                temp.add(rightClient);
                j++;
            }
        }

        while (i <= mid) {
            temp.add(clients.get(i));
            i++;
        }

        while (j <= right) {
            temp.add(clients.get(j));
            j++;
        }

        for (int k = 0; k < temp.size(); k++) {
            clients.set(left + k, temp.get(k));
        }
    }

    public void sortEvenValuesOnly(List<Client> clients, Comparator<Client> comparator) {
        if (clients == null || clients.isEmpty()) {
            return;
        }

        // Создаем список для хранения элементов с четными idNumber и их индексов
        List<Client> evenClients = new ArrayList<>();
        List<Integer> evenIndices = new ArrayList<>();

        // Собираем все элементы с четными idNumber и запоминаем их индексы
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if (client.getIdNumber() % 2 == 0) {
                evenClients.add(client);
                evenIndices.add(i);
            }
        }

        // Если нет элементов с четными значениями, ничего не делаем
        if (evenClients.isEmpty()) {
            return;
        }

        // Сортируем только элементы с четными значениями
        sortWithComparator(evenClients, comparator);

        // Возвращаем отсортированные элементы на исходные позиции
        for (int i = 0; i < evenClients.size(); i++) {
            int originalIndex = evenIndices.get(i);
            clients.set(originalIndex, evenClients.get(i));
        }
    }
}