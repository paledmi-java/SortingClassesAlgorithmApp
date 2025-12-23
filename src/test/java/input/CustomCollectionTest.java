//package input;
//
//import dto.Client;
//import input.Strategy.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CustomCollectionTest {
//
//        private InputManager inputManager;
//        @Mock
//        private ClientInputStrategy mockStrategy;
//
//        @BeforeEach
//        void setUp() {
//            MockitoAnnotations.openMocks(this);
//        }
//
//        // Тесты конструкторов
//        @Test
//        void constructorWithoutStrategy_shouldInitializeWithNullStrategy() {
//            inputManager = new InputManager();
//            assertNull(inputManager.getCurrentStrategy());
//        }
//
//        @Test
//        void constructorWithStrategy_shouldSetStrategy() {
//            inputManager = new InputManager(mockStrategy);
//            assertEquals(mockStrategy, inputManager.getCurrentStrategy());
//        }
//
//        @Test
//        void constructorWithNullStrategy_shouldThrowIllegalArgumentException() {
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class,
//                    () -> new InputManager(null)
//            );
//            assertEquals("Стратегия не может быть null", exception.getMessage());
//        }
//
//        // Тесты setStrategy
//        @Test
//        void setStrategy_shouldUpdateCurrentStrategy() {
//            inputManager = new InputManager();
//            inputManager.setStrategy(mockStrategy);
//            assertEquals(mockStrategy, inputManager.getCurrentStrategy());
//        }
//
//        @Test
//        void setStrategyWithNull_shouldThrowIllegalArgumentException() {
//            inputManager = new InputManager();
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class,
//                    () -> inputManager.setStrategy(null)
//            );
//            assertEquals("Стратегия не может быть null", exception.getMessage());
//        }
//
//        // Тест getCurrentStrategy
//        @Test
//        void getCurrentStrategy_shouldReturnCurrentStrategy() {
//            inputManager = new InputManager(mockStrategy);
//            ClientInputStrategy strategy = inputManager.getCurrentStrategy();
//            assertEquals(mockStrategy, strategy);
//        }
//
//        // Тесты loadData
//        @Test
//        void loadData_withStrategySet_shouldCallGetDataAndReturnResult() throws IOException {
//            CustomCollection<Client> expectedData = new CustomCollection<>();
//            expectedData.addAll(Collections.emptyList());
//
//            when(mockStrategy.getData()).thenReturn(expectedData);
//
//            inputManager = new InputManager(mockStrategy);
//            CustomCollection<Client> result = inputManager.loadData();
//
//            assertEquals(expectedData, result);
//            verify(mockStrategy).getData();
//        }
//
//        @Test
//        void loadData_withoutStrategy_shouldThrowIllegalStateException() {
//            inputManager = new InputManager();
//
//            IllegalStateException exception = assertThrows(
//                    IllegalStateException.class,
//                    () -> inputManager.loadData()
//            );
//
//            assertEquals(
//                    "Стратегия ввода не установлена. Используйте setStrategy() перед вызовом loadData()",
//                    exception.getMessage()
//            );
//        }
//
//        @Test
//        void loadData_whenGetDataThrowsIOException_shouldPropagateException() throws Exception {
//            when(mockStrategy.getData()).thenThrow(IOException.class);
//
//            inputManager = new InputManager(mockStrategy);
//
//            assertThrows(IOException.class, () -> inputManager.loadData());
//        }
//
//        // Тесты createFileStrategy
//        @Test
//        void createFileStrategy_withValidPath_shouldReturnFileReaderStrategy() {
//            inputManager = new InputManager();
//            FileReaderStrategy strategy = inputManager.createFileStrategy("data.txt");
//
//            assertNotNull(strategy);
//            assertEquals("data.txt", strategy.getFilePath());
//        }
//
//        @Test
//        void createFileStrategy_withNullPath_shouldThrowIllegalArgumentException() {
//            inputManager = new InputManager();
//
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class,
//                    () -> inputManager.createFileStrategy(null)
//            );
//
//            assertEquals("Путь к файлу не может быть null или пустым", exception.getMessage());
//        }
//
//        @Test
//        void createFileStrategy_withEmptyPath_shouldThrowIllegalArgumentException() {
//            inputManager = new InputManager();
//
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class,
//                    () -> inputManager.createFileStrategy("")
//            );
//
//            assertEquals("Путь к файлу не может быть null или пустым", exception.getMessage());
//        }
//
//        @Test
//        void createFileStrategy_withWhitespacePath_shouldThrowIllegalArgumentException() {
//            inputManager = new InputManager();
//
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class,
//                    () -> inputManager.createFileStrategy("   ")
//            );
//
//            assertEquals("Путь к файлу не может быть null или пустым", exception.getMessage());
//        }
//
//        // Тест createManualStrategy
//        @Test
//        void createManualStrategy_shouldReturnManualInputReaderStrategy() {
//            inputManager = new InputManager();
//            ManualInputReaderStrategy strategy = inputManager.createManualStrategy();
//
//            assertNotNull(strategy);
//        }
//
//        // Тесты createRandomStrategy
//        @Test
//        void createRandomStrategy_withPositiveCount_shouldReturnRandomDataGeneratorStrategy() {
//            inputManager = new InputManager();
//            RandomDataGeneratorStrategy strategy = inputManager.createRandomStrategy(5);
//
//            assertNotNull(strategy);
//            assertEquals(5, strategy.getCount());
//        }
//
//        @Test
//        void createRandomStrategy_withZeroCount_shouldThrowIllegalArgumentException() {
//            inputManager = new InputManager();
//
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class,
//                    () -> inputManager.createRandomStrategy(0)
//            );
//
//            assertTrue(exception.getMessage().contains("count"));
//        }
//
//        @Test
//        void createRandomStrategy_withNegativeCount_shouldThrowIllegalArgumentException() {
//            inputManager = new InputManager();
//
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class,
//                    () -> inputManager.createRandomStrategy(-1)
//            );
//
//            assertTrue(exception.getMessage().contains("count"));
//        }
//    }
