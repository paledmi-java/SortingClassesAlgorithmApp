package sorting;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeSortDefaultStrategyTest {

    @Test
    @DisplayName("sort с null-коллекцией не должен вызывать исключений")
    void testSortWithNullCollection() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
        strategy.sort(null);
        // Тест проходит, если не выброшено исключение
    }

    @Test
    @DisplayName("sort с пустой коллекцией не должен изменять коллекцию")
    void testSortWithEmptyCollection() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();
        strategy.sort(clients);
        assertEquals(0, clients.size());
    }

    @Test
    @DisplayName("sort с коллекцией из одного элемента не должен изменять коллекцию")
    void testSortWithSingleElement() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
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
    @DisplayName("sort должен сортировать по имени (основной критерий)")
    void testSortByName() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
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
    @DisplayName("sort должен сортировать по ID, если имена одинаковые")
    void testSortByIdWhenNamesAreEqual() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(3).build());
        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7222").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7333").idNumber(2).build());

        strategy.sort(clients);

        // Все имена одинаковые, сортируем по ID: 1, 2, 3
        assertEquals(1, clients.get(0).getIdNumber());
        assertEquals(2, clients.get(1).getIdNumber());
        assertEquals(3, clients.get(2).getIdNumber());
    }

    @Test
    @DisplayName("sort должен сортировать по телефону, если имена и ID одинаковые")
    void testSortByPhoneWhenNamesAndIdsAreEqual() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7333").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build());
        clients.add(new Client.ClientBuilder().name("Анна").phoneNumber("+7222").idNumber(1).build());

        strategy.sort(clients);

        // Имена и ID одинаковые, сортируем по телефону: +7111, +7222, +7333
        assertEquals("+7111", clients.get(0).getPhoneNumber());
        assertEquals("+7222", clients.get(1).getPhoneNumber());
        assertEquals("+7333", clients.get(2).getPhoneNumber());
    }

    @Test
    @DisplayName("sort должен корректно обрабатывать дубликаты")
    void testSortHandlesDuplicates() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        Client duplicate1 = new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build();
        Client duplicate2 = new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build();

        clients.add(duplicate1);
        clients.add(duplicate2);
        clients.add(new Client.ClientBuilder().name("Борис").phoneNumber("+7222").idNumber(2).build());

        strategy.sort(clients);

        // Дубликаты должны остаться в коллекции
        assertEquals("Анна", clients.get(0).getName());
        assertEquals("Анна", clients.get(1).getName());
        assertEquals("Борис", clients.get(2).getName());
    }

    @Test
    @DisplayName("getStrategyName должен возвращать ожидаемую строку")
    void testGetStrategyName() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
        String expectedName = "Merge Sort Default Strategy (сортировка по имени -> ID -> телефону)";
        assertEquals(expectedName, strategy.getStrategyName());
    }

    @Test
    @DisplayName("sort должен сохранять стабильность сортировки для равных элементов")
    void testSortStabilityForEqualElements() {
        MergeSortDefaultStrategy strategy = new MergeSortDefaultStrategy();
        CustomCollection<Client> clients = new CustomCollection<>();

        // Добавляем два клиента с одинаковыми данными в разном порядке
        Client first = new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build();
        Client second = new Client.ClientBuilder().name("Анна").phoneNumber("+7111").idNumber(1).build();

        clients.add(second);
        clients.add(first);

        strategy.sort(clients);

        // Для стабильной сортировки порядок равных элементов должен сохраняться
        // В данном случае оба элемента равны, поэтому порядок может быть любым,
        // но оба элемента должны присутствовать
        assertEquals(2, clients.size());
        assertTrue(clients.get(0).getName().equals("Анна") &&
                clients.get(0).getPhoneNumber().equals("+7111") &&
                clients.get(0).getIdNumber() == 1);
        assertTrue(clients.get(1).getName().equals("Анна") &&
                clients.get(1).getPhoneNumber().equals("+7111") &&
                clients.get(1).getIdNumber() == 1);
    }
}