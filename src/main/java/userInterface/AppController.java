package userInterface;

import dto.Client;
import enums.Field;
import input.CustomCollection;
import input.InputManager;
import sorting.MergeSortDefaultStrategy;
import sorting.MergeSortDynamicStrategy;
import sorting.SortingManager;

import java.io.IOException;

public class AppController {

    private final InputManager inputManager = new InputManager();
    private final CustomCollection<Client> fullList = new CustomCollection<>();
    private final SortingManager sortingManager = new SortingManager();

    public void startDefaultSorting(){
        sortingManager.setStrategy(new MergeSortDefaultStrategy());
        sortingManager.getCurrentStrategy().sort(fullList);
        showAllClients();
    }

    public void startEvenIdsSorting(){
        sortingManager.setStrategy(new MergeSortDefaultStrategy());
        sortingManager.getCurrentStrategy().sortEvenValuesOnly(fullList);
        showAllClients();
    }

    public void startDynamicSorting(Field field){
        sortingManager.setStrategy(new MergeSortDynamicStrategy(field, true));
        sortingManager.getCurrentStrategy().sort(fullList);
        showAllClients();
    }

    public CustomCollection<Client> getFullList() {
        return fullList;
    }

    public void showAllClients(){
        for(Client client : fullList){
            System.out.println(client);
        }
    }

    public void startFileReaderStrategy(String path){
        inputManager.setStrategy(inputManager.createFileStrategy(path));
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
        inputManager.setStrategy(inputManager.createManualStrategy());
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
        inputManager.setStrategy(inputManager.createRandomStrategy(count));
        try {
            CustomCollection<Client> randomList = inputManager.loadData();
            fullList.addAll(randomList);
            for(Client client : randomList){
                System.out.println(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
