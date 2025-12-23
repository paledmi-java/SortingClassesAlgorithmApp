package userInterface;

import java.util.Scanner;

public class MenuManager implements Printable {


    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    public void run() {
        while (running) {
            try {
                printMainMenu();
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.out.println("Пожалуйста, выберите номер опции");
                    continue;
                }

                int mainOption = Integer.parseInt(input);

                switch (mainOption) {
                    case 1 -> printFillingDatabaseMenu();
                    case 2 -> printSortingMenu();
                    case 0 -> exitByChoice();
                    default -> System.out.println("Неверный выбор. Пожалуйста, выберите 1, 2 или 0");
                }
            }
            catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, введите корректный номер опции.");
            }
        }
    }

    @Override
    public void printFillingDatabaseMenu() {
        boolean backToMain = false;
        while (!backToMain && running) {
            Printable.super.printFillingDatabaseMenu();
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Пожалуйста, введите номер опции.");
                continue;
            }
            try {
                int fillOption = Integer.parseInt(input);

                switch (fillOption) {
                    case 1 -> fillManually();
                    case 2 -> fillFromFile();
                    case 3 -> fillRandom();
                    case 0 -> {
                        System.out.println("Возврат в главное меню...");
                        backToMain = true;
                    }
                    default -> System.out.println("Неверный выбор. Пожалуйста, выберите от 0 до 3");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите цифру для выбора действия");
            }
        }

    }

    @Override
    public void printSortingMenu() {
        boolean backToMain = false;
        while (!backToMain && running) {
            Printable.super.printSortingMenu();
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Пожалуйста, введите номер опции.");
                continue;
            }
            try {
                int sortOption = Integer.parseInt(input);

                switch (sortOption) {
                    case 1 -> printDefaultOrder();
                    case 2 -> printNameSortingOptions();
                    case 3 -> printIdSortingOptions();
                    case 4 -> printPhoneSortingOptions();
                    case 0 -> {
                        System.out.println("Возврат в главное меню...");
                        backToMain = true;
                    }
                    default -> System.out.println("Неверный выбор. Пожалуйста, выберите от 0 до 4");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите цифру для выбора действия.");
            }
        }
    }

    @Override
    public void printIdSortingOptions() {
        boolean backToMain = false;
        while (!backToMain && running) {
            Printable.super.printIdSortingOptions();
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Пожалуйста, введите номер опции.");
                continue;
            }

            try {
                int idSortOption = Integer.parseInt(input);

                switch (idSortOption) {
                    case 1 -> sortIDByAscending();
                    case 2 -> sortIDbyEven();
                    case 0 -> {
                        System.out.println("Назад...");
                        backToMain = true;
                    }
                    default -> System.out.println("Неверный выбор. Пожалуйста, выберите от 0 до 3");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите цифру для выбора действия.");
            }
        }
    }

    @Override
    public void printNameSortingOptions() {
        boolean backToMain = false;
        while (!backToMain && running) {
            Printable.super.printNameSortingOptions();
            backToMain = true;
        }
    }

    @Override
    public void printPhoneSortingOptions() {
        boolean backToMain = false;
        while (!backToMain && running) {
            Printable.super.printPhoneSortingOptions();
            backToMain = true;
        }
    }

    private void sortIDByAscending() {
        System.out.println("Клиенты отсортированы по возрастанию ID");
    }

    private void sortIDbyEven() {
        System.out.println("Клиенты отсортированы по четным ID");
    }

    private void fillManually() {
        System.out.println("Выбран способ ввода данных вручную");
        System.out.println("Введите данные...");
        System.out.println("Данные успешно сохранены");

    }

    private void fillFromFile() {
        System.out.println("Выбран способ ввода данных из файла\n");
        System.out.println("Введите путь к файлу: ");
        String filePath = scanner.nextLine().trim();
        System.out.println("Загрузка из файла: " + filePath);

    }

    private void fillRandom() {
        System.out.println("Выбран способ ввода случайных данных");
        int count = inputLengthOfValue("Сколько записей создать? ", 10);
        System.out.println("Создано: " + count + " записей");
    }

    private void printDefaultOrder() {
        System.out.println("Список клиентов");
    }

    private void exitByChoice() {
        System.out.println("Выход из программы...");
        running = false;
        scanner.close();
    }

    private int inputLengthOfValue(String prompt, int defaultValue) {
        while (true) {
            System.out.println(prompt + "(по умолчанию: " + defaultValue + "): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Значение по умолчанию: " + defaultValue);
                return defaultValue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                else if (value == 0) {
                    System.out.println("Используется значение по умолчанию: " + defaultValue);
                    return defaultValue;
                }
                else {
                    System.out.println("Число должно быть положительным! Попробуйте еще раз.");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число! Попробуйте еще раз");
            }
        }
    }
}
