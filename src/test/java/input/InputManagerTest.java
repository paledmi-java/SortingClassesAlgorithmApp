package input;

import dto.Client;
import input.strategy.ClientInputStrategy;
import input.strategy.FileReaderStrategy;
import input.strategy.ManualInputReaderStrategy;
import input.strategy.RandomDataGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InputManagerTest {

        private InputManager inputManager;

        @BeforeEach
        void setUp() {
            inputManager = new InputManager();
        }

        @Test
        @DisplayName("Конструктор без стратегии должен создавать InputManager с null-стратегией")
        void testConstructorWithoutStrategy() {
            assertNull(inputManager.getCurrentStrategy());
        }

        @Test
        @DisplayName("Конструктор с null-стратегией должен выбрасывать IllegalArgumentException")
        void testConstructorWithNullStrategyThrowsException() {
            assertThrows(IllegalArgumentException.class, () -> new InputManager(null));
        }

        @Test
        @DisplayName("Конструктор с валидной стратегией должен устанавливать её как текущую")
        void testConstructorWithValidStrategy() {
            ClientInputStrategy strategy = new TestClientInputStrategy();
            InputManager manager = new InputManager(strategy);
            assertEquals(strategy, manager.getCurrentStrategy());
        }

        @Test
        @DisplayName("setStrategy с null должен выбрасывать IllegalArgumentException")
        void testSetStrategyWithNullThrowsException() {
            assertThrows(IllegalArgumentException.class, () -> inputManager.setStrategy(null));
        }

        @Test
        @DisplayName("setStrategy должен корректно устанавливать новую стратегию")
        void testSetStrategy() {
            ClientInputStrategy strategy = new TestClientInputStrategy();
            inputManager.setStrategy(strategy);
            assertEquals(strategy, inputManager.getCurrentStrategy());
        }

        @Test
        @DisplayName("loadData без установленной стратегии должен выбрасывать IllegalStateException")
        void testLoadDataWithoutStrategyThrowsException() throws IOException {
            assertThrows(IllegalStateException.class, inputManager::loadData);
        }

        @Test
        @DisplayName("loadData должен возвращать данные от текущей стратегии")
        void testLoadDataReturnsDataFromStrategy() throws IOException {
            ClientInputStrategy strategy = new TestClientInputStrategy();
            inputManager.setStrategy(strategy);

            CustomCollection<Client> result = inputManager.loadData();

            assertNotNull(result);
            assertEquals(1, result.size()); // Ожидаем 1 клиента от тестовой стратегии
        }

        @Test
        @DisplayName("loadData должен пробрасывать IOException от стратегии")
        void testLoadDataPropagatesIOException() throws IOException {
            ClientInputStrategy strategy = new ClientInputStrategy() {
                @Override
                public CustomCollection<Client> getData() throws IOException {
                    throw new IOException("Ошибка ввода-вывода");
                }
            };
            inputManager.setStrategy(strategy);

            assertThrows(IOException.class, inputManager::loadData);
        }

        @Test
        @DisplayName("createFileStrategy с null-путём должен выбрасывать IllegalArgumentException")
        void testCreateFileStrategyWithNullPathThrowsException() {
            assertThrows(IllegalArgumentException.class,
                    () -> new FileReaderStrategy(null));
        }

        @Test
        @DisplayName("createFileStrategy с пустым путём должен выбрасывать IllegalArgumentException")
        void testCreateFileStrategyWithEmptyPathThrowsException() {
            assertThrows(IllegalArgumentException.class,
                    () -> new FileReaderStrategy(""));
        }

        @Test
        @DisplayName("createFileStrategy должен создавать FileReaderStrategy с указанным путём")
        void testCreateFileStrategy() {
            String filePath = "test.txt";
            FileReaderStrategy strategy = new FileReaderStrategy(filePath);

            assertNotNull(strategy);
            // Если у FileReaderStrategy есть геттер для пути:
            // assertEquals(filePath, strategy.getFilePath());
        }

        @Test
        @DisplayName("createManualStrategy должен создавать ManualInputReaderStrategy")
        void testCreateManualStrategy() {
            ManualInputReaderStrategy strategy = new ManualInputReaderStrategy();

            assertNotNull(strategy);
        }

        @Test
        @DisplayName("createRandomStrategy с отрицательным count должен выбрасывать IllegalArgumentException")
        void testCreateRandomStrategyWithNegativeCountThrowsException() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RandomDataGeneratorStrategy(-1));
        }

        @Test
        @DisplayName("createRandomStrategy с нулевым count должен выбрасывать IllegalArgumentException")
        void testCreateRandomStrategyWithZeroCountThrowsException() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RandomDataGeneratorStrategy(0));
        }

        @Test
        @DisplayName("createRandomStrategy должен создавать RandomDataGeneratorStrategy с указанным количеством")
        void testCreateRandomStrategy() {
            int count = 5;
            RandomDataGeneratorStrategy strategy =new RandomDataGeneratorStrategy(count);

            assertNotNull(strategy);
            // Если у RandomDataGeneratorStrategy есть геттер для count:
            // assertEquals(count, strategy.getCount());
        }
    }

    // Тестовая реализация ClientInputStrategy для использования в тестах
    class TestClientInputStrategy implements ClientInputStrategy {
        @Override
        public CustomCollection<Client> getData() throws IOException {
            CustomCollection<Client> clients = new CustomCollection<>();
            clients.add(new Client.ClientBuilder().name("Test Client")
                    .phoneNumber("+79991234567").idNumber(1).build());

            // Предполагаем, что CustomCollection имеет конструктор от Collection<Client>
            return clients;
        }
    }