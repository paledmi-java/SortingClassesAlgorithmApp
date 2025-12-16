package userInterface;

import java.util.Scanner;

public class MenuManager implements Printable {


    private final Scanner scanner = new Scanner(System.in);

    public void run(){

        printMainMenu();
        String input = scanner.nextLine();
        int mainOption = Integer.parseInt(input);

        switch (mainOption){
            case 1 -> printFillingDatabaseMenu();
            case 2 -> printSortingMenu();
        }

        switch (mainOption){
            case 1 -> System.out.println("Future custom collection");
            case 2 -> printNameSortingOptions();
            case 3 -> printIdSortingOptions();
            case 4 -> printPhoneSortingOptions();
        }

        // ПРОДОЛЖИТЬ
    }

    @Override
    public void printFillingDatabaseMenu() {
        Printable.super.printFillingDatabaseMenu();
        String input = scanner.nextLine().trim();
        try {
            int fillOption = Integer.parseInt(input);

            switch (fillOption){
                case 1 -> fillManually();
                case 2 -> fillFromFile();
                case 3 -> fillRandom();
                case 0 -> System.out.println("Возврат в главное меню...");
            }
        } catch (NumberFormatException e){
            System.out.println("Пожалуйста, введите цифру для выбора действия или выхода в меню");
        }

    }

    @Override
    public void printSortingMenu() {
        Printable.super.printSortingMenu();
        String input = scanner.nextLine().trim();
        try {
            int fillSortOption = Integer.parseInt(input);

            switch (fillSortOption){
                case 1 -> printDefaultOrder();
                case 2 -> printNameSortingOptions();
                case 3 -> printIdSortingOptions();
                case 4 -> printPhoneSortingOptions();
                case 5 -> System.out.println("Возврат в главное меню...");
            }
        } catch (NumberFormatException e){
            System.out.println("Пожалуйста, введите цифру для выбора действия или выхода в меню");
        }
    }

    @Override
    public void printNameSortingOptions() {
        Printable.super.printNameSortingOptions();
        String input = scanner.nextLine().trim();
    }

    @Override
    public void printIdSortingOptions() {
        Printable.super.printIdSortingOptions();
        String input = scanner.nextLine().trim();
    }

    @Override
    public void printPhoneSortingOptions() {
        Printable.super.printPhoneSortingOptions();
        String input = scanner.nextLine().trim();
    }
    private void fillManually(){
        System.out.println("Выбран способ ввода данных вручную");
        System.out.println("Введите данные...");
        String input = scanner.nextLine().trim();
        System.out.println("Данные успешно сохранены");

    }

    private void fillFromFile(){
        System.out.println("Выбран способ ввода данных из файла\n");
        System.out.println("Введите путь к файлу: ");
        String filePath = scanner.nextLine().trim();
        System.out.println("Загрузка из файла: " + filePath);

    }

    private void fillRandom(){
        System.out.println("Выбран способ ввода случайных данных");
        System.out.println("Сколько записей создать? ");
        String input = scanner.nextLine().trim();
        try {
            int count = Integer.parseInt(input);
            if (count > 0) {
                System.out.println("Будет создано " + count + "Случайных записей");
            }
            else {
                System.out.println("Должна быть как минимум одна запись!");
            }
        } catch (NumberFormatException e){
            System.out.println("Пожалуйста, введите количество записей");
        }
    }

    private void printDefaultOrder(){
        System.out.println("Список клиентов");
    }
}
