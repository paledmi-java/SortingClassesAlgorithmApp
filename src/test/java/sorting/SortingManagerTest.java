package sorting;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingManagerTest {


    @Test
    @DisplayName("Конструктор без параметров должен создавать SortingManager с null-стратегией")
    void testConstructorWithoutParameters() {
        SortingManager manager = new SortingManager();
        assertNull(manager.getCurrentStrategy());
    }

    @Test
    @DisplayName("Конструктор с nullстратегией должен выбрасывать IllegalArgumentException")
    void testConstructorWithNullStrategyThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new SortingManager(null),
                "Конструктор должен выбрасывать исключение при nullстратегии"
        );
    }

    @Test
    @DisplayName("Конструктор с валидной стратегией должен устанавливать её как текущую")
    void testConstructorWithValidStrategy() {
        // Создаём тестовую реализацию SortingStrategy
        SortingStrategy testStrategy = new SortingStrategy() {
            @Override
            public void sort(CustomCollection<Client> clients) {
                // Пустая реализация для теста
            }

            @Override
            public String getStrategyName() {
                return "Test Strategy";
            }

            @Override
            public void sortEvenValuesOnly(CustomCollection<Client> clients) {
            }
        };

        SortingManager manager = new SortingManager(testStrategy);
        assertEquals(testStrategy, manager.getCurrentStrategy(),
                "Текущая стратегия должна совпадать с переданной в конструкторе"
        );
    }

    @Test
    @DisplayName("setStrategy с null должен выбрасывать IllegalArgumentException")
    void testSetStrategyWithNullThrowsException() {
        SortingManager manager = new SortingManager();

        assertThrows(IllegalArgumentException.class,
                () -> manager.setStrategy(null),
                "setStrategy должен выбрасывать исключение при nullстратегии"
        );
    }

    @Test
    @DisplayName("setStrategy должен корректно устанавливать новую стратегию")
    void testSetStrategy() {
        SortingManager manager = new SortingManager();

        // Первая стратегия
        SortingStrategy strategy1 = new SortingStrategy() {
            @Override
            public void sort(CustomCollection<Client> clients) {}

            @Override
            public String getStrategyName() { return "Strategy 1"; }

            @Override
            public void sortEvenValuesOnly(CustomCollection<Client> clients) {
            }
        };

        // Вторая стратегия
        SortingStrategy strategy2 = new SortingStrategy() {
            @Override
            public void sort(CustomCollection<Client> clients) {}

            @Override
            public String getStrategyName() { return "Strategy 2"; }

            @Override
            public void sortEvenValuesOnly(CustomCollection<Client> clients) {

            }
        };

        // Устанавливаем первую стратегию
        manager.setStrategy(strategy1);
        assertEquals(strategy1, manager.getCurrentStrategy(),
                "После setStrategy текущая стратегия должна быть strategy1"
        );

        // Заменяем на вторую стратегию
        manager.setStrategy(strategy2);
        assertEquals(strategy2, manager.getCurrentStrategy(),
                "После повторного setStrategy текущая стратегия должна быть strategy2"
        );
    }

    @Test
    @DisplayName("getStrategy после конструктора без параметров должен возвращать null")
    void testGetStrategyAfterDefaultConstructor() {
        SortingManager manager = new SortingManager();
        assertNull(manager.getCurrentStrategy(),
                "getStrategy должен возвращать null после конструктора без параметров"
        );
    }

    @Test
    @DisplayName("Последовательность операций: конструктор без параметров → setStrategy → getStrategy")
    void testSequenceOfOperations() {
        SortingManager manager = new SortingManager();

        // Изначально стратегия null
        assertNull(manager.getCurrentStrategy());

        // Устанавливаем стратегию
        SortingStrategy testStrategy = new SortingStrategy() {
            @Override
            public void sort(CustomCollection<Client> clients) {}

            @Override
            public String getStrategyName() { return "Test Strategy"; }

            @Override
            public void sortEvenValuesOnly(CustomCollection<Client> clients) {

            }
        };
        manager.setStrategy(testStrategy);

        // Проверяем, что стратегия установилась
        assertEquals(testStrategy, manager.getCurrentStrategy(),
                "После setStrategy getStrategy должен возвращать установленную стратегию"
        );
    }
}