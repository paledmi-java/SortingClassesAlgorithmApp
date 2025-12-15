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
}
