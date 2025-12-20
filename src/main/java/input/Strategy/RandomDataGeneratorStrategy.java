package input.Strategy;

import com.github.javafaker.Faker;
import dto.Client;
import input.CustomCollection;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Стратегия для генерации случайных данных о клиентах.
 * Реализует интерфейс {@link ClientInputStrategy} для создания
 * коллекции клиентов со случайными данными.
 *
 * <p>Использует библиотеку JavaFaker для генерации реалистичных данных:</p>
 * <ul>
 *   <li>Имена клиентов на русском языке</li>
 *   <li>Телефонные номера в российском формате</li>
 *   <li>Уникальные идентификаторы в заданном диапазоне</li>
 * </ul>
 *
 * <p>Обеспечивает уникальность ID клиентов и соблюдение ограничений
 * на диапазон значений (0-1000).</p>
 *
 * @see ClientInputStrategy
 * @see Client
 * @see CustomCollection
 * @see Faker
 */
public class RandomDataGeneratorStrategy implements ClientInputStrategy {
    private int count;
    private Faker faker;
    private Set<Integer> userIds;

    public RandomDataGeneratorStrategy(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество случайных записей должно быть больше нуля");
        }
        this.count = count;
        this.faker = new Faker(new Locale("ru", "Ru"));
        this.userIds = new HashSet<>();
    }

    /**
     * Генерирует коллекцию клиентов со случайными данными.
     * Создает указанное количество клиентов, используя случайные имена,
     * телефонные номера и уникальные идентификаторы.
     *
     * <p>Метод использует Stream API для эффективной генерации данных:</p>
     * <ul>
     *   <li>Генерирует поток объектов-строителей клиентов</li>
     *   <li>Ограничивает поток заданным количеством элементов</li>
     *   <li>Преобразует строителей в готовые объекты Client</li>
     *   <li>Добавляет клиентов в результирующую коллекцию</li>
     * </ul>
     *
     * @return коллекция клиентов со случайными данными
     * @throws IOException если возникает ошибка ввода-вывода (в данной реализации
     *                     маловероятно, но требуется по контракту интерфейса)
     * @throws IllegalStateException если невозможно сгенерировать уникальный ID
     *                               (все возможные ID в диапазоне 0-1000 уже использованы)
     */
    @Override
    public CustomCollection<Client> getData() throws IOException {
        CustomCollection<Client> clients = new CustomCollection<>();

        Stream.generate(this::randomClientBuilder)
                .limit(count)
                .map(Client.ClientBuilder::build)
                .forEach(clients::add);

        return clients;
    }

    /**
     * Создает строитель клиента со случайными данными.
     * Генерирует:
     * <ul>
     *   <li>Случайное полное имя на русском языке</li>
     *   <li>Случайный телефонный номер в формате +79XXXXXXXXX</li>
     *   <li>Уникальный идентификатор в диапазоне 0-1000</li>
     * </ul>
     *
     * @return {@link Client.ClientBuilder} с заполненными случайными данными
     */
    private Client.ClientBuilder randomClientBuilder() {
        return new Client.ClientBuilder()
                .name(faker.name().fullName())
                .phoneNumber("+7" + faker.numerify("9#########"))
                .idNumber(generateUniqueId());
    }

    /**
     * Генерирует уникальный идентификатор в диапазоне от 0 до 1000.
     * Гарантирует, что каждый сгенерированный ID будет уникальным
     * в рамках данного экземпляра стратегии.
     *
     * <p>Алгоритм генерации:</p>
     * <ul>
     *   <li>Проверяет, не достигнут ли лимит уникальных ID</li>
     *   <li>Генерирует случайное число в диапазоне 0-1000</li>
     *   <li>Проверяет уникальность сгенерированного числа</li>
     *   <li>Повторяет генерацию, если число уже использовано</li>
     * </ul>
     *
     * @return уникальный идентификатор клиента
     * @throws IllegalStateException если все возможные ID в диапазоне 0-1000
     *                               уже были сгенерированы
     */
    private int generateUniqueId() {
        if (userIds.size() >= 1000) {
            throw new IllegalStateException("Не осталось свободных ID");
        }

        int id;
        do {
            id = faker.random().nextInt(0, 1000);
        } while (userIds.contains(id));

        userIds.add(id);
        return id;
    }
}
