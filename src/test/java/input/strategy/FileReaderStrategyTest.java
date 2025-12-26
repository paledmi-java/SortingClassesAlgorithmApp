package input.strategy;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderStrategyTest {

    private static final String TEST_FILE_PATH = "test_clients.txt";
    private Path testFilePath;

    @BeforeEach
    void setUp() throws IOException {
        testFilePath = Paths.get(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        // Удаляем тестовый файл после каждого теста, если он существует
        if (Files.exists(testFilePath)) {
            try {
                Files.delete(testFilePath);
            } catch (IOException e) {
                // Игнорируем ошибки удаления в tearDown
            }
        }
    }

    @Test
    @DisplayName("getData должен прочитать корректные данные из файла")
    void testGetDataReadsValidClients() throws IOException {
        // Arrange: создаём файл с корректными данными
        String content = "Иван Иванов|+79991234567|1\n" +
                "Мария Петрова|+79997654321|2";
        Files.write(testFilePath, content.getBytes());

        ClientInputStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert
        assertEquals(2, clients.size());
        assertEquals("Иван Иванов", clients.get(0).getName());
        assertEquals("+79991234567", clients.get(0).getPhoneNumber());
        assertEquals(1, clients.get(0).getIdNumber());

        assertEquals("Мария Петрова", clients.get(1).getName());
        assertEquals("+79997654321", clients.get(1).getPhoneNumber());
        assertEquals(2, clients.get(1).getIdNumber());
    }

    @Test
    @DisplayName("getData должен пропускать пустые строки")
    void testGetDataSkipsEmptyLines() throws IOException {
        // Arrange: файл с пустыми строками и одним корректным клиентом
        String content = "\n\n" +
                "Иван Иванов|+79991234567|1\n" +
                "\n";
        Files.write(testFilePath, content.getBytes());

        ClientInputStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: должна быть прочитана только одна запись
        assertEquals(1, clients.size());
        assertEquals("Иван Иванов", clients.get(0).getName());
    }

    @Test
    @DisplayName("getData должен пропускать строки с неверным форматом (не 3 поля)")
    void testGetDataSkipsInvalidFormatLines() throws IOException {
        // Arrange: строка с двумя полями вместо трёх
        String content = "Иван Иванов|+79991234567\n" +
                "Мария Петрова|+79997654321|2";
        Files.write(testFilePath, content.getBytes());

        ClientInputStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: только вторая строка должна быть прочитана
        assertEquals(1, clients.size());
        assertEquals("Мария Петрова", clients.get(0).getName());
    }

    @Test
    @DisplayName("getData должен пропускать строки с невалидным ID (не число)")
    void testGetDataSkipsInvalidId() throws IOException {
        // Arrange
        String content = "Иван Иванов|+79991234567|abc\n" +
                "Мария Петрова|+79997654321|2";
        Files.write(testFilePath, content.getBytes());

        ClientInputStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: первая строка с невалидным ID пропускается
        assertEquals(1, clients.size());
        assertEquals("Мария Петрова", clients.get(0).getName());
    }

    @Test
    @DisplayName("getData должен пропускать строки с пустым именем")
    void testGetDataSkipsEmptyName() throws IOException {
        // Arrange
        String content = "|+79991234567|1\n" +
                "Мария Петрова|+79997654321|2";
        Files.write(testFilePath, content.getBytes());

        ClientInputStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: первая строка пропускается изза пустого имени
        assertEquals(1, clients.size());
        assertEquals("Мария Петрова", clients.get(0).getName());
    }

    @Test
    @DisplayName("getData должен пропускать строки с невалидным номером телефона")
    void testGetDataSkipsInvalidPhone() throws IOException {
        // Arrange: номер без +7 и неправильный формат
        String content = "Иван Иванов|79991234567|1\n" +  // без +7
                "Мария Петрова|+7999765432|2\n" + // 9 цифр вместо 10
                "Анна Сидорова|+79998887766|3"; // корректный
        Files.write(testFilePath, content.getBytes());

        ClientInputStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: прочитан только последний клиент
        assertEquals(1, clients.size());
        assertEquals("Анна Сидорова", clients.get(0).getName());
    }

    @Test
    @DisplayName("getData должен выбрасывать RuntimeException, если файл не найден")
    void testGetDataThrowsExceptionWhenFileNotFound() {
        ClientInputStrategy strategy = new FileReaderStrategy("nonexistent.txt");

        assertThrows(RuntimeException.class,
                strategy::getData,
                "getData должен выбрасывать RuntimeException для несуществующего файла"
        );
    }

    @Test
    @DisplayName("getData должен выбрасывать RuntimeException при ошибке вводавывода")
    void testGetDataThrowsExceptionOnIOError() throws IOException {
        // Arrange: создаём файл и удаляем его перед чтением
        Files.createFile(testFilePath);
        Files.delete(testFilePath); // файл удаляется, чтобы вызвать ошибку

        ClientInputStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        assertThrows(RuntimeException.class,
                strategy::getData,
                "getData должен выбрасывать RuntimeException при IO ошибке"
        );
    }

    @Test
    @DisplayName("parseToClient должен возвращать Optional.empty для строки с неверным форматом")
    void testParseToClientReturnsEmptyForInvalidFormat() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);
        Optional<Client.ClientBuilder> result = strategy.parseToClient("Иван Иванов|+79991234567");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("parseToClient должен возвращать Optional.empty для строки с невалидным ID")
    void testParseToClientReturnsEmptyForInvalidId() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);
        Optional<Client.ClientBuilder> result = strategy.parseToClient("Иван Иванов|+79991234567|abc");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("parseToClient должен возвращать Optional.empty для строки с пустым именем")
    void testParseToClientReturnsEmptyForEmptyName() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);
        Optional<Client.ClientBuilder> result = strategy.parseToClient("|+79991234567|1");
        assertFalse(result.isPresent());
    }


    @Test
    @DisplayName("parseToClient должен возвращать Optional.empty для строки с некорректным форматом телефона")
    void testParseToClientReturnsEmptyForInvalidPhoneFormat() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Тестируем разные варианты некорректного телефона
        Optional<Client.ClientBuilder> result1 = strategy.parseToClient("Иван Иванов|79991234567|1"); // нет +7
        Optional<Client.ClientBuilder> result2 = strategy.parseToClient("Иван Иванов|+7123|1"); // недостаточно цифр
        Optional<Client.ClientBuilder> result3 = strategy.parseToClient("Иван Иванов|+89991234567|1"); // не +7

        assertFalse(result1.isPresent(), "Должен возвращать Optional.empty, если отсутствует +7 в номере");
        assertFalse(result2.isPresent(), "Должен возвращать Optional.empty, если недостаточно цифр в номере");
        assertFalse(result3.isPresent(), "Должен возвращать Optional.empty, если код страны не +7");
    }

    @Test
    @DisplayName("parseToClient должен возвращать Optional.empty для строки с неверным количеством полей")
    void testParseToClientReturnsEmptyForWrongFieldCount() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        // Слишком мало полей
        Optional<Client.ClientBuilder> result1 = strategy.parseToClient("Иван Иванов|+79991234567");
        // Слишком много полей
        Optional<Client.ClientBuilder> result2 = strategy.parseToClient("Иван Иванов|+79991234567|1|extra");

        assertFalse(result1.isPresent(), "Должен возвращать Optional.empty, если полей меньше трёх");
        assertFalse(result2.isPresent(), "Должен возвращать Optional.empty, если полей больше трёх");
    }

    @Test
    @DisplayName("parseToClient должен возвращать Optional.empty для пустой строки")
    void testParseToClientReturnsEmptyForEmptyString() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        Optional<Client.ClientBuilder> result = strategy.parseToClient("");

        assertFalse(result.isPresent(), "Должен возвращать Optional.empty для пустой строки");
    }

    @Test
    @DisplayName("parseToClient должен возвращать Optional.empty для строки, состоящей только из пробелов")
    void testParseToClientReturnsEmptyForWhitespaceString() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        Optional<Client.ClientBuilder> result = strategy.parseToClient("   ");

        assertFalse(result.isPresent(), "Должен возвращать Optional.empty для строки из пробелов");
    }

    @Test
    @DisplayName("parseToClient должен корректно парсить валидную строку")
    void testParseToClientParsesValidLineCorrectly() {
        FileReaderStrategy strategy = new FileReaderStrategy(TEST_FILE_PATH);

        Optional<Client.ClientBuilder> result = strategy.parseToClient("Иван Иванов|+79991234567|1");

        assertTrue(result.isPresent(), "Должен возвращать непустой Optional для валидной строки");

        Client.ClientBuilder builder = result.get();
        assertEquals("Иван Иванов", builder.getName(), "Имя должно быть корректно извлечено из строки");
        assertEquals("+79991234567", builder.getPhoneNumber(), "Номер телефона должен быть корректно извлечён из строки");
        assertEquals(1, builder.getIdNumber(), "ID должен быть корректно преобразован в число");
    }

}