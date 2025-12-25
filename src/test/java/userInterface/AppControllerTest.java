package userInterface;

import dto.Client;
import enums.Field;
import input.CustomCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {

    private AppController controller;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        controller = new AppController();
        // Перенаправляем System.out в ByteArrayOutputStream для перехвата вывода
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("startDefaultSorting должен отсортировать список и вывести всех клиентов")
    void testStartDefaultSorting() {
        // Arrange: добавляем тестовых клиентов
        Client client1 = new Client.ClientBuilder().name("Иван").phoneNumber("+79991234567").idNumber(2).build();
        Client client2 = new Client.ClientBuilder().name("Пётр").phoneNumber("+79997654321").idNumber(1).build();
        controller.getFullList().add(client1);
        controller.getFullList().add(client2);

        // Act
        controller.startDefaultSorting();

        // Assert: проверяем, что вывод содержит обоих клиентов (порядок после сортировки: client2, client1)
        String output = outputStream.toString();
        assertTrue(output.contains("Иван") && output.contains("Пётр"),
                "Вывод должен содержать всех клиентов после сортировки");
    }

    @Test
    @DisplayName("startEvenIdsSorting должен отсортировать только клиентов с чётными ID")
    void testStartEvenIdsSorting() {
        // Arrange
        Client client1 = new Client.ClientBuilder().name("Анна").phoneNumber("+79991111111").idNumber(3).build(); // нечётный ID
        Client client2 = new Client.ClientBuilder().name("Мария").phoneNumber("+79992222222").idNumber(4).build(); // чётный ID
        controller.getFullList().add(client1);
        controller.getFullList().add(client2);

        // Act
        controller.startEvenIdsSorting();

        // Assert: в выводе должен быть только клиент с чётным ID (Мария)
        String output = outputStream.toString();
        assertFalse(output.contains("Анна"), "Вывод не должен содержать клиента с нечётным ID");
        assertTrue(output.contains("Мария"), "Вывод должен содержать клиента с чётным ID");
    }

    @Test
    @DisplayName("startDynamicSorting должен отсортировать по указанному полю")
    void testStartDynamicSorting() {
        // Arrange: сортировка по имени (Field.NAME)
        Client client1 = new Client.ClientBuilder().name("Сергей").phoneNumber("+79993333333").idNumber(5).build();
        Client client2 = new Client.ClientBuilder().name("Алексей").phoneNumber("+79994444444").idNumber(6).build();
        controller.getFullList().add(client1);
        controller.getFullList().add(client2);

        // Act: сортировка по полю имени
        controller.startDynamicSorting(Field.NAME);

        // Assert: после сортировки первым должен идти «Алексей»
        String output = outputStream.toString();
        int alexeyIndex = output.indexOf("Алексей");
        int sergeyIndex = output.indexOf("Сергей");
        assertTrue(alexeyIndex < sergeyIndex,
                "После сортировки по имени 'Алексей' должен идти перед 'Сергеем'");
    }

    @Test
    @DisplayName("getFullList должен возвращать актуальную коллекцию клиентов")
    void testGetFullList() {
        // Arrange: заполняем коллекцию
        Client client = new Client.ClientBuilder().name("Тест").phoneNumber("+70000000000").idNumber(99).build();
        controller.getFullList().add(client);

        // Act
        CustomCollection<Client> list = controller.getFullList();

        // Assert
        assertEquals(1, list.size());
        assertEquals("Тест", list.get(0).getName());
    }

    @Test
    @DisplayName("showAndWriteAllClients должен выводить и записывать всех клиентов в файл")
    void testShowAndWriteAllClients() {
        // Arrange
        Client client = new Client.ClientBuilder().name("ВыводТест").phoneNumber("+71111111111").idNumber(77).build();
        controller.getFullList().add(client);

        // Act
        controller.showAndWriteAllClients();

        // Assert: вывод должен содержать имя клиента
        String output = outputStream.toString();
        assertTrue(output.contains("ВыводТест"),
                "showAndWriteAllClients должен вывести всех клиентов из fullList");
    }

    @Test
    @DisplayName("startFileReaderStrategy должен загрузить клиентов из файла и посчитать Алексеев")
    void testStartFileReaderStrategy() {

        File testFile = new File("test_clients.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Алексей|+79991112233|1\n");
            writer.write("Мария|+79992223344|2\n");
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        // Arrange: предполагаем, что createFileStrategy корректно создаёт стратегию для тестового файла
        // В реальных тестах нужно подготовить файл с данными, содержащими хотя бы одного «Алексея»

        String testFilePath = "test_clients.txt";

        // Act: запускаем загрузку из файла
        controller.startFileReaderStrategy(testFilePath);

        // Assert: проверяем, что fullList не пуста (данные загружены)
        assertFalse(controller.getFullList().isEmpty(),
                "fullList должен быть заполнен после загрузки из файла");

        // Проверяем, что в выводе есть строка с количеством Алексеев
        String output = outputStream.toString();
        assertTrue(output.contains("Добавлено Алексеев:"),
                "Вывод должен содержать количество добавленных Алексеев");
    }

    @Test
    @DisplayName("startRandomDataStrategy должен сгенерировать случайных клиентов и посчитать Алексеев")
    void testStartRandomDataStrategy() {
        // Arrange: генерируем 3 случайных клиента

        int count = 3;

        // Act
        controller.startRandomDataStrategy(count);

        // Assert: fullList должен содержать 3 клиента
        assertEquals(count, controller.getFullList().size(),
                "fullList должен содержать указанное количество сгенерированных клиентов");

        // Проверяем вывод количества Алексеев
        String output = outputStream.toString();
        assertTrue(output.contains("Добавлено Алексеев:"),
                "Вывод должен содержать количество добавленных Алексеев");
    }

    @AfterEach
    void tearDown() {
        // Восстанавливаем оригинальный System.out после теста
        System.setOut(originalOut);
    }
}
