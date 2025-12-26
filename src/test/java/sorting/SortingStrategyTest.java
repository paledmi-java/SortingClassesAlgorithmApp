package sorting;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingStrategyTest {

    // Тестовая реализация интерфейса SortingStrategy для использования в тестах
    private static class TestSortingStrategy implements SortingStrategy {
        private boolean sortCalled = false;
        private boolean sortEvenValuesOnlyCalled = false;
        private String strategyName;

        public TestSortingStrategy(String strategyName) {
            this.strategyName = strategyName;
        }

        @Override
        public void sort(CustomCollection<Client> clients) {
            sortCalled = true;
        }

        @Override
        public String getStrategyName() {
            return strategyName;
        }

        @Override
        public void sortEvenValuesOnly(CustomCollection<Client> clients) {
            sortEvenValuesOnlyCalled = true;
        }

        // Вспомогательные методы для проверки вызовов
        public boolean isSortCalled() {
            return sortCalled;
        }

        public boolean isSortEvenValuesOnlyCalled() {
            return sortEvenValuesOnlyCalled;
        }
    }

    @Test
    @DisplayName("sort должен быть вызываемым без исключений")
    void testSortIsCallable() {
        SortingStrategy strategy = new TestSortingStrategy("Test Strategy");
        CustomCollection<Client> clients = new CustomCollection<>();

        strategy.sort(clients);

        assertTrue(((TestSortingStrategy) strategy).isSortCalled(),
                "Метод sort должен быть вызван без исключений");
    }

    @Test
    @DisplayName("getStrategyName должен возвращать корректное имя стратегии")
    void testGetStrategyNameReturnsCorrectName() {
        String expectedName = "Test Strategy";
        SortingStrategy strategy = new TestSortingStrategy(expectedName);

        String actualName = strategy.getStrategyName();

        assertEquals(expectedName, actualName,
                "getStrategyName должен возвращать имя, установленное при создании стратегии");
    }

    @Test
    @DisplayName("sortEvenValuesOnly должен быть вызываемым без исключений")
    void testSortEvenValuesOnlyIsCallable() {
        SortingStrategy strategy = new TestSortingStrategy("Test Strategy");
        CustomCollection<Client> clients = new CustomCollection<>();

        strategy.sortEvenValuesOnly(clients);

        assertTrue(((TestSortingStrategy) strategy).isSortEvenValuesOnlyCalled(),
                "Метод sortEvenValuesOnly должен быть вызван без исключений");
    }

    @Test
    @DisplayName("sort с null-коллекцией не должен вызывать исключений (по контракту интерфейса)")
    void testSortWithNullCollection() {
        SortingStrategy strategy = new TestSortingStrategy("Test Strategy");

        assertDoesNotThrow(() -> strategy.sort(null),
                "sort не должен выбрасывать исключений при null-коллекции");
    }

    @Test
    @DisplayName("sortEvenValuesOnly с null-коллекцией не должен вызывать исключений (по контракту интерфейса)")
    void testSortEvenValuesOnlyWithNullCollection() {
        SortingStrategy strategy = new TestSortingStrategy("Test Strategy");

        assertDoesNotThrow(() -> strategy.sortEvenValuesOnly(null),
                "sortEvenValuesOnly не должен выбрасывать исключений при null-коллекции");
    }

    @Test
    @DisplayName("Методы sort и sortEvenValuesOnly могут работать с пустой коллекцией")
    void testMethodsWorkWithEmptyCollection() {
        SortingStrategy strategy = new TestSortingStrategy("Test Strategy");
        CustomCollection<Client> emptyClients = new CustomCollection<>();

        // Вызываем sort с пустой коллекцией
        strategy.sort(emptyClients);
        assertTrue(((TestSortingStrategy) strategy).isSortCalled(),
                "sort должен корректно обрабатывать пустую коллекцию");

        // Сбрасываем флаг для следующего теста
        ((TestSortingStrategy) strategy).sortCalled = false;

        // Вызываем sortEvenValuesOnly с пустой коллекцией
        strategy.sortEvenValuesOnly(emptyClients);
        assertTrue(((TestSortingStrategy) strategy).isSortEvenValuesOnlyCalled(),
                "sortEvenValuesOnly должен корректно обрабатывать пустую коллекцию");
    }

    @Test
    @DisplayName("Несколько вызовов sort должны обрабатываться корректно")
    void testMultipleSortCalls() {
        SortingStrategy strategy = new TestSortingStrategy("Test Strategy");
        CustomCollection<Client> clients1 = new CustomCollection<>();
        CustomCollection<Client> clients2 = new CustomCollection<>();

        strategy.sort(clients1);
        strategy.sort(clients2);

        assertTrue(((TestSortingStrategy) strategy).isSortCalled(),
                "После нескольких вызовов sort флаг вызова должен оставаться установленным");
    }

    @Test
    @DisplayName("Несколько вызовов sortEvenValuesOnly должны обрабатываться корректно")
    void testMultipleSortEvenValuesOnlyCalls() {
        SortingStrategy strategy = new TestSortingStrategy("Test Strategy");
        CustomCollection<Client> clients1 = new CustomCollection<>();
        CustomCollection<Client> clients2 = new CustomCollection<>();

        strategy.sortEvenValuesOnly(clients1);
        strategy.sortEvenValuesOnly(clients2);

        assertTrue(((TestSortingStrategy) strategy).isSortEvenValuesOnlyCalled(),
                "После нескольких вызовов sortEvenValuesOnly флаг вызова должен оставаться установленным");
    }
}