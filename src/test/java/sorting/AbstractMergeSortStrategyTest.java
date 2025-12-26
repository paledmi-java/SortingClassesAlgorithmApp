package sorting;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractMergeSortStrategyTest {

    // Конкретная реализация абстрактного класса для тестирования
    static class TestMergeSortStrategy extends AbstractMergeSortStrategy {
        @Override
        protected Comparator<Client> getComparator() {
            return Comparator.comparing(Client::getName);
        }

        @Override
        public void sort(CustomCollection<Client> clients) {
        }
    }

    @Test
    @DisplayName("sortWithComparator с null-коллекцией не должен вызывать исключений")
    void testSortWithComparatorWithNullCollection() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        strategy.sortWithComparator(null, Comparator.comparing(Client::getIdNumber));
        // Тест проходит, если не выброшено исключение
    }

    @Test
    @DisplayName("sortWithComparator с пустой коллекцией не должен изменять коллекцию")
    void testSortWithComparatorWithEmptyCollection() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();
        strategy.sortWithComparator(clients, Comparator.comparing(Client::getIdNumber));
        assertEquals(0, clients.size());
    }

    @Test
    @DisplayName("sortWithComparator с коллекцией из одного элемента не должен изменять коллекцию")
    void testSortWithComparatorWithSingleElement() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();
        Client client = new Client.ClientBuilder().name("Иван").phoneNumber("+79991234567").idNumber(1).build();
        clients.add(client);

        strategy.sortWithComparator(clients, Comparator.comparing(Client::getIdNumber));

        assertEquals(1, clients.size());
        assertEquals("Иван", clients.get(0).getName());
    }

    @Test
    @DisplayName("sortWithComparator должен корректно сортировать коллекцию по idNumber")
    void testSortWithComparatorSortsByIdNumber() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        // Добавляем клиентов в неупорядоченном порядке
        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(3).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7333").idNumber(2).build());

        Comparator<Client> idComparator = Comparator.comparing(Client::getIdNumber);
        strategy.sortWithComparator(clients, idComparator);

        // Проверяем, что клиенты отсортированы по idNumber: 1, 2, 3
        assertEquals(1, clients.get(0).getIdNumber());
        assertEquals(2, clients.get(1).getIdNumber());
        assertEquals(3, clients.get(2).getIdNumber());
    }

    @Test
    @DisplayName("getStrategyName должен возвращать ожидаемую строку")
    void testGetStrategyName() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        String expectedName = "Abstract Merge Sort (сортировка слиянием)";
        assertEquals(expectedName, strategy.getStrategyName());
    }

    @Test
    @DisplayName("sortEvenValuesOnly с null-коллекцией не должен вызывать исключений")
    void testSortEvenValuesOnlyWithNullCollection() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        strategy.sortEvenValuesOnly(null);
        // Тест проходит, если не выброшено исключение
    }

    @Test
    @DisplayName("sortEvenValuesOnly с пустой коллекцией не должен изменять коллекцию")
    void testSortEvenValuesOnlyWithEmptyCollection() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();
        strategy.sortEvenValuesOnly(clients);
        assertEquals(0, clients.size());
    }

    @Test
    @DisplayName("sortEvenValuesOnly должен сортировать только элементы с чётными idNumber")
    void testSortEvenValuesOnlySortsEvenIdNumbers() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        // Порядок: нечётный, чётный, нечётный, чётный, чётный
        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build()); // нечётный
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(4).build()); // чётный
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7333").idNumber(3).build()); // нечётный
        clients.add(new Client.ClientBuilder().name("Галина").phoneNumber("+7444").idNumber(8).build()); // чётный
        clients.add(new Client.ClientBuilder().name("Дмитрий").phoneNumber("+7555").idNumber(6).build()); // чётный

        strategy.sortEvenValuesOnly(clients);

        // Нечётные элементы остаются на своих местах
        assertEquals(1, clients.get(0).getIdNumber()); // Анна на позиции 0
        assertEquals(3, clients.get(2).getIdNumber()); // Виктор на позиции 2

        // Чётные элементы отсортированы по возрастанию: 4, 6, 8
        assertEquals(4, clients.get(1).getIdNumber()); // Борис на позиции 1
        assertEquals(6, clients.get(3).getIdNumber()); // Дмитрий на позиции 3
        assertEquals(8, clients.get(4).getIdNumber()); // Галина на позиции 4
    }

    @Test
    @DisplayName("sortEvenValuesOnly не должен ничего делать, если нет чётных idNumber")
    void testSortEvenValuesOnlyDoesNothingWhenNoEvenIdNumbers() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(3).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7333").idNumber(5).build());

        strategy.sortEvenValuesOnly(clients);

        // Все элементы остались на своих местах в исходном порядке
        assertEquals(1, clients.get(0).getIdNumber());
        assertEquals(3, clients.get(1).getIdNumber());
        assertEquals(5, clients.get(2).getIdNumber());
    }

    @Test
    @DisplayName("sortEvenValuesOnly с пользовательским компаратором должен сортировать по заданному критерию")
    void testSortEvenValuesOnlyWithCustomComparator() {
        AbstractMergeSortStrategy strategy = new TestMergeSortStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(4).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(8).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7333").idNumber(6).build());

        // Компаратор по убыванию idNumber
        Comparator<Client> descendingIdComparator = Comparator.comparing(Client::getIdNumber).reversed();
        strategy.sortEvenValuesOnly(clients, descendingIdComparator);

        // Чётные idNumber отсортированы по убыванию: 8, 6, 4
        assertEquals(8, clients.get(0).getIdNumber());
        assertEquals(6, clients.get(1).getIdNumber());
        assertEquals(4, clients.get(2).getIdNumber());
    }
}