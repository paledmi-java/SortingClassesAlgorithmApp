package input.Strategy;

import dto.Client;
import input.CustomCollection;

import java.io.IOException;

/**
 * Интерфейс стратегии для ввода данных клиентов.
 * Реализует паттерн Стратегия для различных способов получения объектов {@link Client}.
 * Каждая реализация представляет конкретный способ получения данных
 * Реализации должны гарантировать, что возвращаемые объекты {@link Client}
 * являются валидными
 */
public interface ClientInputStrategy {
    CustomCollection<Client> getData() throws IOException;
}
