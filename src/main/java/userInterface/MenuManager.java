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
                case 2 -> printSortOfName();
                case 3 -> printSortOfID();
                case 4 -> printSortOfPhoneNumber();
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
    }

    private void fillFromFile(){
        System.out.println("Выбран способ ввода данных из файла");
    }

    private void fillRandom(){
        System.out.println("Выбран способ ввода случайных данных");
    }

    private void printDefaultOrder(){
        System.out.println("Список клиентов");
    }

    private void printSortOfName(){
        System.out.println("Сортировка по Имени");
    }

    private void printSortOfID(){
        System.out.println("Сортировка по ID");
    }

    private void printSortOfPhoneNumber(){
        System.out.println("Сортировка по номеру телефона");
    }
}
