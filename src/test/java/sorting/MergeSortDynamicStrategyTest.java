package sorting;

import dto.Client;
import enums.Field;
import input.CustomCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MergeSortDynamicStrategyTest {

    @Test
    @DisplayName("Конструктор с валидными параметрами должен создавать стратегию")
    void testConstructorWithValidParameters() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.NAME, true);
        assertNotNull(strategy);
    }

    @Test
    @DisplayName("sort с null-коллекцией не должен вызывать исключений")
    void testSortWithNullCollection() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.NAME, true);
        strategy.sort(null);
        // Тест проходит, если не выброшено исключение
    }

    @Test
    @DisplayName("sort с пустой коллекцией не должен изменять коллекцию")
    void testSortWithEmptyCollection() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.ID_NUMBER, false);
        CustomCollection<Client> clients = new CustomCollection<>();
        strategy.sort(clients);
        assertEquals(0, clients.size());
    }

    @Test
    @DisplayName("sort с коллекцией из одного элемента не должен изменять коллекцию")
    void testSortWithSingleElement() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.PHONE_NUMBER, true);
        CustomCollection<Client> clients = new CustomCollection<>();
        Client client = new Client.ClientBuilder()
                .name("Иван")
                .phoneNumber("+79991234567")
                .idNumber(1)
                .build();
        clients.add(client);

        strategy.sort(clients);

        assertEquals(1, clients.size());
        assertEquals("Иван", clients.get(0).getName());
        assertEquals("+79991234567", clients.get(0).getPhoneNumber());
        assertEquals(1, clients.get(0).getIdNumber());
    }

    @Test
    @DisplayName("sort по имени в порядке возрастания должен корректно сортировать")
    void testSortByNameAscending() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.NAME, true);
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Сергей").phoneNumber("+7111").idNumber(3).build());
        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7222").idNumber(2).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7333").idNumber(1).build());

        strategy.sort(clients);

        // Ожидаемый порядок: Анна, Борис, Сергей
        assertEquals("Анна", clients.get(0).getName());
        assertEquals("Борис", clients.get(1).getName());
        assertEquals("Сергей", clients.get(2).getName());
    }

    @Test
    @DisplayName("sort по имени в порядке убывания должен корректно сортировать")
    void testSortByNameDescending() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.NAME, false);
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(2).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7333").idNumber(3).build());

        strategy.sort(clients);

        // Ожидаемый порядок: Виктор, Борис, Анна
        assertEquals("Виктор", clients.get(0).getName());
        assertEquals("Борис", clients.get(1).getName());
        assertEquals("Анна", clients.get(2).getName());
    }

    @Test
    @DisplayName("sort по ID в порядке возрастания должен корректно сортировать")
    void testSortByIdAscending() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.ID_NUMBER, true);
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(3).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7333").idNumber(2).build());

        strategy.sort(clients);

        // Ожидаемый порядок: 1, 2, 3
        assertEquals(1, clients.get(0).getIdNumber());
        assertEquals(2, clients.get(1).getIdNumber());
        assertEquals(3, clients.get(2).getIdNumber());
    }

    @Test
    @DisplayName("sort по ID в порядке убывания должен корректно сортировать")
    void testSortByIdDescending() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.ID_NUMBER, false);
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(3).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7333").idNumber(2).build());

        strategy.sort(clients);

        // Ожидаемый порядок: 3, 2, 1
        assertEquals(3, clients.get(0).getIdNumber());
        assertEquals(2, clients.get(1).getIdNumber());
        assertEquals(1, clients.get(2).getIdNumber());
    }

    @Test
    @DisplayName("sort по номеру телефона в порядке возрастания должен корректно сортировать")
    void testSortByPhoneAscending() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.PHONE_NUMBER, true);
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7333").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7111").idNumber(2).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7222").idNumber(3).build());

        strategy.sort(clients);

        // Ожидаемый порядок: +7111, +7222, +7333
        assertEquals("+7111", clients.get(0).getPhoneNumber());
        assertEquals("+7222", clients.get(1).getPhoneNumber());
        assertEquals("+7333", clients.get(2).getPhoneNumber());
    }

    @Test
    @DisplayName("sort по номеру телефона в порядке убывания должен корректно сортировать")
    void testSortByPhoneDescending() {
        MergeSortDynamicStrategy strategy = new MergeSortDynamicStrategy(Field.PHONE_NUMBER, false);
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7333").idNumber(2).build());
        clients.add(new Client.ClientBuilder().name("Виктор").phoneNumber("+7222").idNumber(3).build());

        strategy.sort(clients);

        // Ожидаемый порядок: +7333, +7222, +7111
        assertEquals("+7333", clients.get(0).getPhoneNumber());
        assertEquals("+7222", clients.get(1).getPhoneNumber());
        assertEquals("+7111", clients.get(2).getPhoneNumber());
    }


    @Test
    @DisplayName("getStrategyName должен возвращать ожидаемую строку с указанием поля сортировки")
    void testGetStrategyName() {
        MergeSortDynamicStrategy ascendingStrategy = new MergeSortDynamicStrategy(Field.NAME, true);
        MergeSortDynamicStrategy descendingStrategy = new MergeSortDynamicStrategy(Field.ID_NUMBER, false);

        // Act & Assert
        String expectedAscending = "Dynamic Merge Sort (Динамическая сортировка по: NAME)";
        String expectedDescending = "Dynamic Merge Sort (Динамическая сортировка по: ID_NUMBER)";

        assertEquals(expectedAscending, ascendingStrategy.getStrategyName(),
                "Для стратегии с сортировкой по NAME (возрастание) должна возвращаться корректная строка");
        assertEquals(expectedDescending, descendingStrategy.getStrategyName(),
                "Для стратегии с сортировкой по ID_NUMBER (убывание) должна возвращаться корректная строка");
    }

}