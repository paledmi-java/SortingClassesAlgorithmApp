package input.strategy;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomDataGeneratorStrategyTest {

    @Test
    @DisplayName("Конструктор с положительным количеством записей должен создавать стратегию")
    void testConstructorWithPositiveCount() {
        ClientInputStrategy strategy = new RandomDataGeneratorStrategy(5);
        assertNotNull(strategy);
        assertEquals(5, ((RandomDataGeneratorStrategy) strategy).getCount());
    }

    @Test
    @DisplayName("Конструктор с нулевым количеством записей должен выбрасывать IllegalArgumentException")
    void testConstructorWithZeroCountThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomDataGeneratorStrategy(0),
                "Конструктор должен выбрасывать исключение при нулевом количестве записей"
        );
    }

    @Test
    @DisplayName("Конструктор с отрицательным количеством записей должен выбрасывать IllegalArgumentException")
    void testConstructorWithNegativeCountThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomDataGeneratorStrategy(-5),
                "Конструктор должен выбрасывать исключение при отрицательном количестве записей"
        );
    }

    @Test
    @DisplayName("getData должен генерировать указанное количество клиентов")
    void testGetDataGeneratesCorrectNumberOfClients() throws IOException {
        // Arrange
        int expectedCount = 3;
        ClientInputStrategy strategy = new RandomDataGeneratorStrategy(expectedCount);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert
        assertEquals(expectedCount, clients.size());
    }

    @Test
    @DisplayName("Все сгенерированные клиенты должны иметь уникальные ID")
    void testGeneratedClientsHaveUniqueIds() throws IOException {
        // Arrange
        int count = 10;
        ClientInputStrategy strategy = new RandomDataGeneratorStrategy(count);

        // Act
        CustomCollection<Client> clients = strategy.getData();
        Set<Integer> ids = new HashSet<>();

        // Assert: проверяем уникальность ID
        for (Client client : clients) {
            assertTrue(ids.add(client.getIdNumber()),
                    "ID клиента не уникален: " + client.getIdNumber());
        }
        assertEquals(count, ids.size());
    }

    @Test
    @DisplayName("ID клиентов должны быть в диапазоне 0–1000")
    void testIdsAreInRange() throws IOException {
        // Arrange
        int count = 50;
        ClientInputStrategy strategy = new RandomDataGeneratorStrategy(count);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: каждый ID должен быть в диапазоне [0, 1000]
        for (Client client : clients) {
            int id = client.getIdNumber();
            assertTrue(id >= 0 && id <= 1000,
                    "ID " + id + " выходит за пределы диапазона 0–1000");
        }
    }

    @Test
    @DisplayName("Номера телефонов должны соответствовать формату +79XXXXXXXXX")
    void testPhoneNumbersMatchFormat() throws IOException {
        // Arrange
        int count = 20;
        ClientInputStrategy strategy = new RandomDataGeneratorStrategy(count);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: проверяем формат номера телефона
        for (Client client : clients) {
            String phone = client.getPhoneNumber();
            assertTrue(phone.matches("^\\+79\\d{9}$"),
                    "Номер телефона '" + phone + "' не соответствует формату +79XXXXXXXXX");
        }
    }

    @Test
    @DisplayName("Имена клиентов не должны быть пустыми")
    void testNamesAreNotEmpty() throws IOException {
        // Arrange
        int count = 15;
        ClientInputStrategy strategy = new RandomDataGeneratorStrategy(count);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: имя не должно быть пустым или null
        for (Client client : clients) {
            assertNotNull(client.getName(), "Имя клиента не должно быть null");
            assertFalse(client.getName().trim().isEmpty(),
                    "Имя клиента не должно быть пустым");
        }
    }

    @Test
    @DisplayName("getData должен выбрасывать IllegalStateException при попытке сгенерировать >1000 уникальных ID")
    void testGetDataThrowsIllegalStateExceptionWhenExceedingUniqueIdLimit() throws IOException {
        // Arrange: пытаемся сгенерировать больше уникальных ID, чем доступно в диапазоне 0–1000
        ClientInputStrategy strategy = new RandomDataGeneratorStrategy(1001);

        // Act & Assert
        assertThrows(IllegalStateException.class,
                strategy::getData,
                "getData должен выбрасывать IllegalStateException, когда все ID в диапазоне 0–1000 использованы"
        );
    }

    @Test
    @DisplayName("Повторный вызов getData должен генерировать новые случайные данные")
    void testRepeatedGetDataCallsGenerateDifferentData() throws IOException {
        // Arrange
        int count = 2;
        RandomDataGeneratorStrategy strategy = new RandomDataGeneratorStrategy(count);

        // Act: первый вызов
        CustomCollection<Client> firstResult = strategy.getData();
        // Второй вызов
        CustomCollection<Client> secondResult = strategy.getData();

        // Assert: сравниваем ID клиентов из двух вызовов
        Set<Integer> firstIds = new HashSet<>();
        for (Client client : firstResult) {
            firstIds.add(client.getIdNumber());
        }

        Set<Integer> secondIds = new HashSet<>();
        for (Client client : secondResult) {
            secondIds.add(client.getIdNumber());
        }

        // Ожидаем, что хотя бы один ID отличается (высокая вероятность при случайной генерации)
        boolean hasDifferentIds = !firstIds.equals(secondIds);
        assertTrue(hasDifferentIds,
                "Повторные вызовы getData должны генерировать разные наборы данных");
    }
}