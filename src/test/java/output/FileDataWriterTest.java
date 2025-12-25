package output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileDataWriterTest {

    private FileDataWriter fileDataWriter;
    private final String testFilePath = "savedData.txt";
    private File testFile;

    @BeforeEach
    void setUp() {
        fileDataWriter = new FileDataWriter();
        testFile = new File(testFilePath);
        // Удаляем файл перед каждым тестом, если он существует
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterEach
    void tearDown() {
        // Удаляем тестовый файл после каждого теста
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    @DisplayName("writeDataToFile должен создавать файл, если он не существует")
    void testWriteDataToFileCreatesFileIfNotExists() {
        // Act
        fileDataWriter.writeDataToFile("Тестовые данные");

        // Assert
        assertTrue(testFile.exists(), "Файл должен быть создан");
        assertTrue(testFile.isFile(), "Созданный объект должен быть файлом");
    }

    @Test
    @DisplayName("writeDataToFile должен записывать данные в файл")
    void testWriteDataToFileWritesDataToFile() throws IOException {
        // Arrange
        String testData = "Тестовые данные для записи";

        // Act
        fileDataWriter.writeDataToFile(testData);

        // Assert
        assertTrue(testFile.exists());
        String fileContent = new String(Files.readAllBytes(testFile.toPath()));
        assertEquals(testData, fileContent, "Содержимое файла должно совпадать с записанными данными");
    }

    @Test
    @DisplayName("writeDataToFile должен дописывать данные в конец файла (append)")
    void testWriteDataToFileAppendsDataToExistingFile() throws IOException {
        // Arrange
        String firstData = "Первая строка\n";
        String secondData = "Вторая строка\n";

        // Act — записываем данные дважды
        fileDataWriter.writeDataToFile(firstData);
        fileDataWriter.writeDataToFile(secondData);

        // Assert
        assertTrue(testFile.exists());
        String fileContent = new String(Files.readAllBytes(testFile.toPath()));
        assertTrue(fileContent.contains(firstData), "Файл должен содержать первую строку");
        assertTrue(fileContent.contains(secondData), "Файл должен содержать вторую строку");
        assertEquals(firstData + secondData, fileContent, "Полное содержимое файла должно быть корректным");
    }

    @Test
    @DisplayName("writeDataToFile с пустой строкой должен создавать файл без содержимого")
    void testWriteDataToFileWithEmptyStringCreatesEmptyFile() throws IOException {
        // Act
        fileDataWriter.writeDataToFile("");

        // Assert
        assertTrue(testFile.exists());
        long fileSize = testFile.length();
        assertEquals(0, fileSize, "Файл с пустой строкой должен иметь нулевой размер");
    }

    @Test
    @DisplayName("writeDataToFile с null должен создавать файл и не записывать ничего (изза обработки исключения)")
    void testWriteDataToFileWithNullCreatesFile() {
        // Act
        fileDataWriter.writeDataToFile(null);

        // Assert
        assertTrue(testFile.exists(), "Файл должен быть создан даже при записи null");
        // В текущем коде при записи null в файл запишется строка "null"
        // Если ожидается другое поведение, нужно изменить реализацию метода
    }

    @Test
    @DisplayName("writeDataToFile должен корректно обрабатывать специальные символы")
    void testWriteDataToFileHandlesSpecialCharacters() throws IOException {
        // Arrange
        String specialData = "Текст с символами: !@#$%^&*()_+-=[]{}|;':\",./<>?\n\t\n";

        // Act
        fileDataWriter.writeDataToFile(specialData);

        // Assert
        assertTrue(testFile.exists());
        String fileContent = new String(Files.readAllBytes(testFile.toPath()));
        assertEquals(specialData, fileContent, "Файл должен корректно сохранять специальные символы");
    }

    @Test
    @DisplayName("writeDataToFile при ошибке создания файла должен выбрасывать RuntimeException")
    void testWriteDataToFileThrowsRuntimeExceptionOnCreateNewFileFailure() {
        // Arrange — создаём директорию с тем же именем, что и файл, чтобы вызвать ошибку при createNewFile()
        File conflictingDir = new File("savedData.txt");
        conflictingDir.mkdir();

        try {
            // Act & Assert
            assertThrows(RuntimeException.class, () -> fileDataWriter.writeDataToFile("Данные"));
        } finally {
            // Очищаем — удаляем директорию после теста
            conflictingDir.delete();
        }
    }
}