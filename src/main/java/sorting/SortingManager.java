package sorting;

public class SortingManager {
    private SortingStrategy currentStrategy;

    public SortingManager(){}

    public SortingManager(SortingStrategy initialStrategy) {
        if (initialStrategy == null) {
            throw new IllegalArgumentException("Стратегия не может быть null");
        }
        currentStrategy = initialStrategy;
    }

    public void setStrategy(SortingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Стратегия не может быть null");
        }
        this.currentStrategy = strategy;
    }

    public SortingStrategy getCurrentStrategy() {
        return currentStrategy;
    }
}
