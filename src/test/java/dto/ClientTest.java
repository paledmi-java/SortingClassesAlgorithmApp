package dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    @DisplayName("Конструктор Client должен корректно инициализировать поля из ClientBuilder")
    void testClientConstructorInitializesFieldsCorrectly() {
        // Arrange
        Client.ClientBuilder builder = new Client.ClientBuilder()
                .name("Иван Иванов")
                .phoneNumber("+79991234567")
                .idNumber(1);

        // Act
        Client client = new Client(builder);

        // Assert
        assertEquals("Иван Иванов", client.getName());
        assertEquals("+79991234567", client.getPhoneNumber());
        assertEquals(1, client.getIdNumber());
    }

    @Test
    @DisplayName("Геттеры должны возвращать корректные значения полей")
    void testGettersReturnCorrectValues() {
        // Arrange
        Client.ClientBuilder builder = new Client.ClientBuilder()
                .name("Петр Петров")
                .phoneNumber("+78887654321")
                .idNumber(2);
        Client client = new Client(builder);

        // Act & Assert
        assertEquals("Петр Петров", client.getName());
        assertEquals("+78887654321", client.getPhoneNumber());
        assertEquals(2, client.getIdNumber());
    }

    @Test
    @DisplayName("Метод build() ClientBuilder должен создавать корректный объект Client")
    void testClientBuilderBuildCreatesCorrectClient() {
        // Arrange
        Client.ClientBuilder builder = new Client.ClientBuilder()
                .name("Мария Сидорова")
                .phoneNumber("+77776543210")
                .idNumber(3);

        // Act
        Client client = builder.build();

        // Assert
        assertNotNull(client);
        assertEquals("Мария Сидорова", client.getName());
        assertEquals("+77776543210", client.getPhoneNumber());
        assertEquals(3, client.getIdNumber());
    }

    @Test
    @DisplayName("Цепочка методов в ClientBuilder должна работать корректно")
    void testClientBuilderMethodChaining() {
        // Act
        Client client = new Client.ClientBuilder()
                .name("Алексей Козлов")
                .phoneNumber("+75554443322")
                .idNumber(4)
                .build();

        // Assert
        assertNotNull(client);
        assertEquals("Алексей Козлов", client.getName());
        assertEquals("+75554443322", client.getPhoneNumber());
        assertEquals(4, client.getIdNumber());
    }

    @Test
    @DisplayName("toString() должен возвращать строку в ожидаемом формате")
    void testToStringReturnsExpectedFormat() {
        // Arrange
        Client.ClientBuilder builder = new Client.ClientBuilder()
                .name("Анна Волкова")
                .phoneNumber("+73332221100")
                .idNumber(5);
        Client client = builder.build();
        String expectedString = "Client{name='Анна Волкова', phoneNumber='+73332221100', idNumber=5}";

        // Act
        String actualString = client.toString();

        // Assert
        assertEquals(expectedString, actualString);
    }

    @Test
    @DisplayName("Поля Client должны быть final и не изменяться после создания")
    void testClientFieldsAreImmutable() {
        // Arrange
        Client.ClientBuilder builder = new Client.ClientBuilder()
                .name("Сергей Новиков")
                .phoneNumber("+71112223344")
                .idNumber(6);
        Client client = builder.build();

        // Act & Assert (проверяем, что геттеры возвращают те же значения)
        assertEquals("Сергей Новиков", client.getName());
        assertEquals("+71112223344", client.getPhoneNumber());
        assertEquals(6, client.getIdNumber());

        // Попытка изменить состояние через builder не должна повлиять на существующий объект
        builder.name("Другое имя").idNumber(999);
        Client anotherClient = builder.build();

        // Проверяем, что первый клиент остался неизменным
        assertEquals("Сергей Новиков", client.getName());
        assertEquals(6, client.getIdNumber());

        // Проверяем, что новый клиент имеет новые значения
        assertEquals("Другое имя", anotherClient.getName());
        assertEquals(999, anotherClient.getIdNumber());
    }

    @Test
    @DisplayName("Два клиента с одинаковыми данными должны быть разными объектами")
    void testClientsWithSameDataAreDifferentObjects() {
        // Arrange
        Client.ClientBuilder builder1 = new Client.ClientBuilder()
                .name("Ольга Смирнова")
                .phoneNumber("+79998887766")
                .idNumber(7);
        Client.ClientBuilder builder2 = new Client.ClientBuilder()
                .name("Ольга Смирнова")
                .phoneNumber("+79998887766")
                .idNumber(7);

        // Act
        Client client1 = builder1.build();
        Client client2 = builder2.build();

        // Assert
        assertNotSame(client1, client2); // Разные объекты
        assertEquals(client1.getName(), client2.getName());
        assertEquals(client1.getPhoneNumber(), client2.getPhoneNumber());
        assertEquals(client1.getIdNumber(), client2.getIdNumber());
    }
}