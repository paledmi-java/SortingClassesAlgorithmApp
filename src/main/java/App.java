import userInterface.MenuManager;

import java.io.IOException;
import java.io.PrintStream;


public class App {
    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        MenuManager menuManager = new MenuManager();

        menuManager.run();
    }
}

