package sorting;

import dto.Client;
import enums.Field;

import java.util.Comparator;
import java.util.List;

public class MergeSortDynamicStrategy extends AbstractMergeSortStrategy{
    private final Field field;
    private final boolean ascending;

    public MergeSortDynamicStrategy(Field field, boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }

    @Override
    public void sort(List<Client> clients) {
        Comparator<Client> comparator = createComparatorForField();
        if (!ascending) {
            comparator = comparator.reversed();
        }
        sortWithComparator(clients, comparator);
    }

    public Comparator<Client> createComparatorForField(){
        return switch (field){
            case NAME -> Comparator.comparing(Client :: getName);
            case ID_NUMBER -> Comparator.comparing(Client :: getIdNumber);
            case PHONE_NUMBER -> Comparator.comparing(Client :: getPhoneNumber);
            default -> throw new IllegalArgumentException("Неизвестное поле: " + field);
        };
    }

    @Override
    public String getStrategyName() {
        return "Dynamic Merge Sort (Динамическая сортировка по: " + field + ")";
    }
}
