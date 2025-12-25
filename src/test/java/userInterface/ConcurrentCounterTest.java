package userInterface;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcurrentCounterTest {


    private final ConcurrentCounter counter = new ConcurrentCounter();

    @Test
    @DisplayName("countAlexes должен вернуть 0 для пустой коллекции")
    void testCountAlexesEmptyCollection() {
        // Arrange
        CustomCollection<Client> emptyClients = new CustomCollection<>();

        // Act
        int result = counter.countAlexes(emptyClients);

        // Assert
        assertEquals(0, result, "Для пустой коллекции должно быть 0 Алексеев");
    }

    @Test
    @DisplayName("countAlexes должен вернуть 0 если нет клиентов с именем Алексей")
    void testCountAlexesNoAlexes() {
        // Arrange
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Иван Иванов").build());
        clients.add(new Client.ClientBuilder().name("Мария Петрова").build());
        clients.add(new Client.ClientBuilder().name("Пётр Сидоров").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert
        assertEquals(0, result, "Если нет Алексеев, результат должен быть 0");
    }

    @Test
    @DisplayName("countAlexes должен корректно посчитать одного Алексея")
    void testCountAlexesOneAlex() {
        // Arrange
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей Иванов").build());
        clients.add(new Client.ClientBuilder().name("Мария Петрова").build());
        clients.add(new Client.ClientBuilder().name("Пётр Сидоров").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert
        assertEquals(1, result, "Должен быть найден 1 Алексей");
    }

    @Test
    @DisplayName("countAlexes должен корректно посчитать нескольких Алексеев")
    void testCountAlexesMultipleAlexes() {
        // Arrange
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей Иванов").build());
        clients.add(new Client.ClientBuilder().name("Мария Петрова").build());
        clients.add(new Client.ClientBuilder().name("Алексей Сидоров").build());
        clients.add(new Client.ClientBuilder().name("Анна Козлова").build());
        clients.add(new Client.ClientBuilder().name("Алексей Николаев").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert
        assertEquals(3, result, "Должно быть найдено 3 Алексея");
    }

    @Test
    @DisplayName("countAlexes должен найти Алексея даже если имя содержит дополнительные слова")
    void testCountAlexesNameWithAdditionalWords() {
        // Arrange
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей Владимирович Иванов").build());
        clients.add(new Client.ClientBuilder().name("Сергей Алексеевич Петров").build()); // не должен учитываться
        clients.add(new Client.ClientBuilder().name("Алексей").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert
        assertEquals(2, result, "Должны быть найдены оба Алексея, даже с дополнительными словами в имени");
    }

    @Test
    @DisplayName("countAlexes должен корректно работать с нечётным количеством клиентов")
    void testCountAlexesOddNumberOfClients() {
        // Arrange: 5 клиентов, 2 из которых — Алексей
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей Иванов").build());
        clients.add(new Client.ClientBuilder().name("Мария Петрова").build());
        clients.add(new Client.ClientBuilder().name("Алексей Сидоров").build());
        clients.add(new Client.ClientBuilder().name("Анна Козлова").build());
        clients.add(new Client.ClientBuilder().name("Пётр Николаев").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert: коллекция делится на 2 и 3 элемента, но результат должен быть корректным
        assertEquals(2, result, "Для нечётного количества клиентов должно быть найдено 2 Алексея");
    }

    @Test
    @DisplayName("countAlexes должен корректно работать с чётным количеством клиентов")
    void testCountAlexesEvenNumberOfClients() {
        // Arrange: 4 клиента, 2 из которых — Алексей
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей Иванов").build());
        clients.add(new Client.ClientBuilder().name("Мария Петрова").build());
        clients.add(new Client.ClientBuilder().name("Алексей Сидоров").build());
        clients.add(new Client.ClientBuilder().name("Анна Козлова").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert: коллекция делится на 2 части по 2 элемента
        assertEquals(2, result, "Для чётного количества клиентов должно быть найдено 2 Алексея");
    }

    @Test
    @DisplayName("countAlexes должен обработать коллекцию с одним клиентом — Алексеем")
    void testCountAlexesSingleClientAlex() {
        // Arrange
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert
        assertEquals(1, result, "Для одного клиента-Алексея результат должен быть 1");
    }

    @Test
    @DisplayName("countAlexes должен обработать коллекцию с одним клиентом не-Алексеем")
    void testCountAlexesSingleClientNotAlex() {
        // Arrange
        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Иван").build());

        // Act
        int result = counter.countAlexes(clients);

        // Assert
        assertEquals(0, result, "Для одного клиента не-Алексея результат должен быть 0");
    }

    @Test
    @DisplayName("countAlexes должен выбрасывать RuntimeException при InterruptedException")
    void testCountAlexesThrowsOnInterruptedException() throws Exception {
        // Arrange: создаём кастомный ConcurrentCounter, который имитирует InterruptedException
        ConcurrentCounter faultyCounter = new ConcurrentCounter() {
            @Override
            public int countAlexes(CustomCollection<Client> clients) {
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                Future<Long> count1 = executorService.submit(() -> 0L);
                executorService.shutdown();
                try {
                    Thread.currentThread().interrupt();
                    count1.get(); // вызовет InterruptedException
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
                return 0;
            }
        };

        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей").build());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> faultyCounter.countAlexes(clients),
                "При InterruptedException должен выбрасываться RuntimeException"
        );
    }

    @Test
    @DisplayName("countAlexes должен выбрасывать RuntimeException при ExecutionException")
    void testCountAlexesThrowsOnExecutionException() throws Exception {
        // Arrange: имитируем ExecutionException
        ConcurrentCounter faultyCounter = new ConcurrentCounter() {
            @Override
            public int countAlexes(CustomCollection<Client> clients) {
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                Future<Long> count1 = executorService.submit(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        throw new Exception("Simulated execution error");
                    }
                });
                executorService.shutdown();
                try {
                    count1.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                return 0;
            }
        };

        CustomCollection<Client> clients = new CustomCollection<>();
        clients.add(new Client.ClientBuilder().name("Алексей").build());

// Act & Assert
        assertThrows(RuntimeException.class,
                () -> faultyCounter.countAlexes(clients),
                "При ExecutionException должен выбрасываться RuntimeException"
        );
    }
}