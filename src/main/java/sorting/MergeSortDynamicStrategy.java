package sorting;

import dto.Client;
import enums.Field;
import input.CustomCollection;

import java.util.Comparator;

public class MergeSortDynamicStrategy extends AbstractMergeSortStrategy{
    private final Field field;
    private final boolean ascending;

    public MergeSortDynamicStrategy(Field field, boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }

    @Override
    public void sort(CustomCollection<Client> clients) {
        sortWithComparator(clients, getComparator());
    }

    public Comparator<Client> getComparator(){
        Comparator<Client> comparator = switch (field){
            case NAME -> Comparator.comparing(Client :: getName);
            case ID_NUMBER -> Comparator.comparing(Client :: getIdNumber);
            case PHONE_NUMBER -> Comparator.comparing(Client :: getPhoneNumber);
            default -> throw new IllegalArgumentException("Неизвестное поле: " + field);
        };

        if (ascending) {
            return comparator;
        } else {
            return comparator.reversed();
        }
    }

    @Override
    public String getStrategyName() {
        return "Dynamic Merge Sort (Динамическая сортировка по: " + field + ")";
    }
}
