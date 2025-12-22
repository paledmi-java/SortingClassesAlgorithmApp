import dto.Client;
import input.CustomCollection;
import input.InputManager;
import input.Strategy.FileReaderStrategy;
import input.Strategy.ManualInputReaderStrategy;
import input.Strategy.RandomDataGeneratorStrategy;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
////
////        System.out.println("=== ТЕСТ FileReader  ===");
////
////        try {
////            FileReaderStrategy reader = new FileReaderStrategy("test_clients.txt");
////            CustomCollection<Client> clients = reader.getData();
////
////            System.out.println("\n=== РЕЗУЛЬТАТ ===");
////            System.out.println("Загружено клиентов: " + clients.size());
////            System.out.println("Содержимое:");
////
////            int i = 1;
////            for (Client client : clients) {
////                System.out.println(i + ". " + client);
////                i++;
////            }
////
////        } catch (Exception e) {
////            System.out.println("ФАТАЛЬНАЯ ОШИБКА: " + e.getMessage());
////            e.printStackTrace();
////        }
////
////        System.out.println("=== КОНЕЦ ТЕСТА === \n");
////
////        System.out.println("=== ТЕСТ RandomDataGenerator ===");
////
////        // Тест 1: Базовая генерация
////        RandomDataGeneratorStrategy generator = new RandomDataGeneratorStrategy(5);
////        CustomCollection<Client> clients = generator.getData();
////
////        System.out.println("Сгенерировано: " + clients.size() + " клиентов");
////        clients.forEach(client -> System.out.println("  - " + client));
////
////        // Тест 2: С уникальными параметрами
////        System.out.println("\n=== Тест с кастомным диапазоном ===");
////        RandomDataGeneratorStrategy generator2 = new RandomDataGeneratorStrategy(3);
////        CustomCollection<Client> clients2 = generator2.getData();
////
////        clients2.forEach(client ->
////                System.out.println("  - " + client.getName() + ", ID: " + client.getIdNumber()));
////
////        System.out.println("=== КОНЕЦ ТЕСТА ===");
//
//        System.out.println("=== ТЕСТ ManualInputReader ===\n");
//
//        ManualInputReaderStrategy reader = new ManualInputReaderStrategy();
//        CustomCollection<Client> clients = reader.getData();
//
//        System.out.println("\n=== РЕЗУЛЬТАТ ===");
//        System.out.println("Всего клиентов: " + clients.size());
//
//        if (!clients.isEmpty()) {
//            System.out.println("Список:");
//            int i = 1;
//            for (Client client : clients) {
//                System.out.println(i + ". " + client);
//                i++;
//            }
//        }
//        System.out.println("=== КОНЕЦ ТЕСТА ===");

        InputManager inputManager = new InputManager();
        inputManager.setStrategy(new FileReaderStrategy("test_clients.txt"));
        CustomCollection<Client> clients = inputManager.loadData();
        clients.forEach(System.out::println);


//        inputManager.setStrategy(inputManager.createManualStrategy());
//        CustomCollection<Client> clients2 = inputManager.loadData();
//        clients2.forEach(System.out::println);
    }
}

