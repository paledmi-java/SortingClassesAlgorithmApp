package input;

import input.strategy.*;
import dto.Client;

import java.io.IOException;

/**
 * Менеджер ввода данных, реализующий паттерн Strategy.
 * Выступает в роли Context для управления различными стратегиями ввода данных о клиентах.
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Управление текущей стратегией ввода</li>
 *   <li>Загрузка данных с использованием выбранной стратегии</li>
 *   <li>Создание стратегий различных типов</li>
 * </ul>
 *
 * @see ClientInputStrategy
 * @see FileReaderStrategy
 * @see ManualInputReaderStrategy
 * @see RandomDataGeneratorStrategy
 * @see CustomCollection
 * @see Client
 */

public class InputManager {
    private ClientInputStrategy currentStrategy;


    public InputManager() {
        // Инициализация без стратегии
    }

    public InputManager(ClientInputStrategy initialStrategy) {
        if (initialStrategy == null) {
            throw new IllegalArgumentException("Стратегия не может быть null");
        }
        this.currentStrategy = initialStrategy;
    }

    public void setStrategy(ClientInputStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Стратегия не может быть null");
        }
        this.currentStrategy = strategy;
    }

    public ClientInputStrategy getCurrentStrategy() {
        return currentStrategy;
    }

    /**
     * Загружает данные о клиентах с использованием текущей стратегии.
     *
     * @return коллекция клиентов, загруженных с помощью текущей стратегии
     * @throws IllegalStateException если стратегия не установлена
     * @throws IOException если возникает ошибка ввода-вывода при загрузке данных
     */
    public CustomCollection<Client> loadData() throws IOException {
        if (currentStrategy == null) {
            throw new IllegalStateException("Стратегия ввода не установлена. " +
                    "Используйте setStrategy() перед вызовом loadData()");
        }
        return currentStrategy.getData();
    }
}