package input.strategy;

import dto.Client;
import input.CustomCollection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Стратегия для чтения данных о клиентах из текстового файла.
 * Реализует интерфейс {@link ClientInputStrategy} для получения
 * коллекции клиентов из внешнего файла.
 *
 * <p>Формат файла:</p>
 * <ul>
 *   <li>Каждая строка представляет одного клиента</li>
 *   <li>Формат строки: {@code Имя|Телефон|ID}</li>
 *   <li>Разделитель полей: вертикальная черта ({@code |})</li>
 *   <li>Кодировка файла: UTF-8</li>
 * </ul>
 *
 * <p>Требования к данным:</p>
 * <ul>
 *   <li>Имя: непустая строка</li>
 *   <li>Телефон: формат {@code +7XXXXXXXXXX} (11 цифр после {@code +7})</li>
 *   <li>ID: целое число</li>
 * </ul>
 *
 * <p>Обработка ошибок:</p>
 * <ul>
 *   <li>Пропускает некорректные строки с выводом сообщения об ошибке</li>
 *   <li>Игнорирует пустые строки</li>
 *   <li>Генерирует исключение при проблемах с файлом</li>
 * </ul>
 *
 * <p>Пример содержимого файла:</p>
 * <pre>
 * Иван Иванов|+79991234567|1
 * Мария Петрова|+79997654321|2
 * </pre>
 *
 * @see ClientInputStrategy
 * @see Client
 * @see CustomCollection
 */
public class FileReaderStrategy implements ClientInputStrategy {
    private final String filePath;

    public FileReaderStrategy(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Читает данные о клиентах из файла и возвращает их в виде коллекции.
     * Файл должен содержать строки в формате: Имя|Телефон|ID
     * Телефон должен соответствовать формату: +7XXXXXXXXXX (11 цифр после +7)
     *
     * @return коллекция клиентов, прочитанных из файла
     * @throws RuntimeException если файл не найден или произошла ошибка ввода-вывода
     */
    @Override
    public CustomCollection<Client> getData() {
        CustomCollection<Client> clients = new CustomCollection<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new RuntimeException("Файл не найден: " + filePath);
        }

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines
                    .filter(line -> !line.trim().isEmpty())
                    .map(this::parseToClient)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(Client.ClientBuilder::build)
                    .forEach(clients::add);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    /**
     * Парсит строку из файла в объект ClientBuilder.
     * Строка должна быть в формате: Имя|Телефон|ID
     *
     * @param line строка из файла для парсинга
     * @return Optional содержащий ClientBuilder если строка валидна,
     *         или пустой Optional если строка содержит ошибки формата
     */
    public Optional<Client.ClientBuilder> parseToClient(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length != 3) {
                throw new IllegalArgumentException("неверный формат данных");
            }
            String name = parts[0].trim();
            String phoneNumber = parts[1].trim();
            int idNumber;

            try {
                idNumber = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("ID не число: " + line);
                return Optional.empty();
            }

            if (name.isEmpty()) {
                System.out.println("Пустое имя в строке: " + line);
                return Optional.empty();
            }

            if (!phoneNumber.matches("^\\+7\\d{10}$")) {
                System.out.println("Неверный формат номера телефона в строке: " + line);
                return Optional.empty();
            }

            Client.ClientBuilder builder = new Client.ClientBuilder()
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .idNumber(idNumber);
            return Optional.of(builder);

        } catch (Exception e) {
            System.out.println("Ошибка в строке: " + line);
            return Optional.empty();
        }
    }

    public String getFilePath() {
        return filePath;
    }
}
