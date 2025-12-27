import userInterface.MenuManager;

import java.io.IOException;
import java.io.PrintStream;


public class App {
    public static void main(String[] args) throws IOException {

        MenuManager menuManager = new MenuManager();

        menuManager.run();
    }
}

