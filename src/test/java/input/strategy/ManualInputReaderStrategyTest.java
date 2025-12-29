package input.strategy;

import dto.Client;
import input.CustomCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManualInputReaderStrategyTest {

    private InputStream originalSystemIn;
    private final ByteArrayInputStream testInput = new ByteArrayInputStream(new byte[0]);

    @BeforeEach
    void setUp() {
        // Сохраняем оригинальный System.in
        originalSystemIn = System.in;
    }

    @AfterEach
    void tearDown() {
        // Восстанавливаем оригинальный System.in после теста
        System.setIn(originalSystemIn);
    }

    @Test
    @DisplayName("getData должен вернуть пустую коллекцию при отказе от добавления первого клиента")
    void testGetDataReturnsEmptyCollectionWhenUserDeclinesFirstClient() throws IOException {
        // Arrange: пользователь отказывается добавлять первого клиента
        String input = "н\n"; // 'нет' на вопрос «Добавляем клиента?»
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ClientInputStrategy strategy = new ManualInputReaderStrategy();

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert
        assertEquals(0, clients.size());
    }

    @Test
    @DisplayName("getData должен корректно добавить одного клиента с валидными данными")
    void testGetDataAddsOneValidClient() throws IOException {
        // Arrange: последовательность ввода для одного клиента
        String input = "да\n" +           // добавляем клиента
                "Иван Иванов\n" +   // имя
                "+79991234567\n" + // телефон
                "1\n" +             // ID
                "да\n" +           // подтверждение данных
                "нет\n";          // отказ от добавления следующего клиента
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ClientInputStrategy strategy = new ManualInputReaderStrategy();

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert
        assertEquals(1, clients.size());
        Client client = clients.get(0);
        assertEquals("Иван Иванов", client.getName());
        assertEquals("+79991234567", client.getPhoneNumber());
        assertEquals(1, client.getIdNumber());
    }

    @Test
    @DisplayName("getData должен пропускать клиента при отмене подтверждения данных")
    void testGetDataSkipsClientWhenUserCancelsConfirmation() throws IOException {
        // Arrange: добавляем клиента, но отменяем подтверждение данных
        String input = "да\n" +
                "Мария Петрова\n" +
                "+79997654321\n" +
                "2\n" +
                "нет\n" +          // отмена подтверждения
                "нет\n";         // отказ от следующего клиента
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ClientInputStrategy strategy = new ManualInputReaderStrategy();

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: клиент не добавлен
        assertEquals(0, clients.size());
    }

    @Test
    @DisplayName("promptForNumber должен запрашивать повторный ввод при неверном формате телефона")
    void testPromptForNumberRepeatsOnInvalidPhoneFormat() throws IOException {
        // Arrange: неверный формат телефона, затем корректный
        String input = "да\n" +
                "Анна Сидорова\n" +
                "79998887766\n" +  // без +7
                "+79998887766\n" + // корректный формат
                "3\n" +
                "да\n" +
                "нет\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ClientInputStrategy strategy = new ManualInputReaderStrategy();

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: клиент добавлен с корректным номером
        assertEquals(1, clients.size());
        assertEquals("+79998887766", clients.get(0).getPhoneNumber());
    }

    @Test
    @DisplayName("promptForId должен отклонять занятый ID и запрашивать новый")
    void testPromptForIdRejectsDuplicateId() throws IOException {
        // Arrange: попытка ввести дублирующий ID
        String input = "да\n" +
                "Иван Иванов\n" +
                "+79991234567\n" +
                "1\n" +
                "да\n" +         // подтверждение первого клиента
                "да\n" +       // добавляем второго клиента
                "Мария Петрова\n" +
                "+79997654321\n" +
                "1\n" +         // дублирующий ID
                "2\n" +         // новый ID
                "да\n" +
                "нет\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ClientInputStrategy strategy = new ManualInputReaderStrategy();

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: два клиента с разными ID
        assertEquals(2, clients.size());
        assertEquals(1, clients.get(0).getIdNumber());
        assertEquals(2, clients.get(1).getIdNumber());
    }

    @Test
    @DisplayName("promptForId должен отклонять ID вне диапазона 0–1000")
    void testPromptForIdRejectsOutOfRangeId() throws IOException {
        // Arrange: ID вне диапазона, затем корректный
        String input = "да\n" +
                "Анна Сидорова\n" +
                "+79998887766\n" +
                "-5\n" +        // меньше 0
                "1500\n" +     // больше 1000
                "5\n" +       // корректный ID
                "да\n" +
                "нет\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ClientInputStrategy strategy = new ManualInputReaderStrategy();

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: клиент добавлен с ID=5
        assertEquals(1, clients.size());
        assertEquals(5, clients.get(0).getIdNumber());
    }

    @Test
    @DisplayName("getData должен остановить ввод при достижении лимита в 1000 клиентов")
    void testGetDataStopsAtLimitOf1000Clients() throws IOException {
        // Arrange: имитируем достижение лимита
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            inputBuilder.append("да\n");
            inputBuilder.append("Иван Иванов").append("\n");
            inputBuilder.append("+7").append(String.format("%010d", i)).append("\n");
            inputBuilder.append(i).append("\n");
            inputBuilder.append("да\n");
        }
        inputBuilder.append("да\n"); // попытка добавить 1001-го клиента

        System.setIn(new ByteArrayInputStream(inputBuilder.toString().getBytes()));

        ClientInputStrategy strategy = new ManualInputReaderStrategy();

        // Act
        CustomCollection<Client> clients = strategy.getData();

        // Assert: добавлено ровно 1000 клиентов
        assertEquals(1000, clients.size());
    }


        }