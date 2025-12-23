package userInterface;

import dto.Client;
import input.CustomCollection;
import input.InputManager;
import input.Strategy.FileReaderStrategy;
import input.Strategy.ManualInputReaderStrategy;
import input.Strategy.RandomDataGeneratorStrategy;

import java.io.IOException;

public class AppController {

    private final InputManager inputManager = new InputManager();
    private final CustomCollection<Client> fullList = new CustomCollection<>();

    public CustomCollection<Client> getFullList() {
        return fullList;
    }

    public void showAllClientsInDefaultOrder(){
        for(Client client : fullList){
            System.out.println(client);
        }
    }

    public void startFileReaderStrategy(String path){
        inputManager.setStrategy(new FileReaderStrategy(path));
        try {
            CustomCollection<Client> fromFileList = inputManager.loadData();
            fullList.addAll(fromFileList);
            for(Client client : fromFileList){
                System.out.println(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startManualInputStrategy(){
        inputManager.setStrategy(new ManualInputReaderStrategy());
        try {
            CustomCollection<Client> manualList = inputManager.loadData();
            fullList.addAll(manualList);
            for(Client client : manualList){
                System.out.println(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRandomDataStrategy(int count){
        inputManager.setStrategy(new RandomDataGeneratorStrategy(count));
        try {
            CustomCollection<Client> randomList = inputManager.loadData();
            for(Client client : randomList){
                System.out.println(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
