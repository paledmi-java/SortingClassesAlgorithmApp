package classesToSort;

import interfaces.Printable;

import java.util.Scanner;

public class App implements Printable {


    public static void main(String[] args) {
        ClientSortingSystem clientSortingSystem = new ClientSortingSystem();
        Scanner scanner = new Scanner(System.in);
        App app = new App();

        app.printMainMenu();
        String input = scanner.nextLine();
        int mainOption = Integer.parseInt(input);

        switch (mainOption){
            case 1 -> System.out.println(clientSortingSystem.getClientList());
            case 2 -> app.printNameSortingOptions();
            case 3 -> app.printIdSortingOptions();
            case 4 -> app.printCredentialsMenu();
        }
    }
}
