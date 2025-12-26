package userInterface;

import dto.Client;
import enums.Field;
import input.CustomCollection;
import input.InputManager;
import input.strategy.ManualInputReaderStrategy;
import output.FileDataWriter;
import sorting.MergeSortDefaultStrategy;
import sorting.MergeSortDynamicStrategy;
import sorting.SortingManager;

import java.io.File;
import java.io.IOException;

public class AppController {

    private final InputManager inputManager = new InputManager();
    private final CustomCollection<Client> fullList = new CustomCollection<>();
    private final SortingManager sortingManager = new SortingManager();
    private final FileDataWriter fileDataWriter = new FileDataWriter();
    ConcurrentCounter concurrentCounter = new ConcurrentCounter();

    public void startDefaultSorting(){
        sortingManager.setStrategy(new MergeSortDefaultStrategy());
        sortingManager.getCurrentStrategy().sort(fullList);
        showAndWriteAllClients();
    }

    public void startEvenIdsSorting(){
        sortingManager.setStrategy(new MergeSortDefaultStrategy());
        sortingManager.getCurrentStrategy().sortEvenValuesOnly(fullList);
        showAndWriteAllClients();
    }

    public void startDynamicSorting(Field field){
        sortingManager.setStrategy(new MergeSortDynamicStrategy(field, true));
        sortingManager.getCurrentStrategy().sort(fullList);
        showAndWriteAllClients();
    }

    public CustomCollection<Client> getFullList() {
        return fullList;
    }

    public void showAndWriteAllClients(){
        for(Client client : fullList){
            System.out.println(client);
            fileDataWriter.writeDataToFile(client + "\n");
        }
        fileDataWriter.writeDataToFile("\n");
    }

    public void startFileReaderStrategy(String path){
        inputManager.setStrategy(inputManager.createFileStrategy(path));
        try {
            CustomCollection<Client> fromFileList = inputManager.loadData();
            int countOfAlexes = concurrentCounter.countAlexes(fromFileList);
            fullList.addAll(fromFileList);
            for(Client client : fromFileList){
                System.out.println(client);
            }
            System.out.println("Добавлено Алексеев: " + countOfAlexes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File path: " + path);
        System.out.println("File exists: " + new File(path).exists());
    }

    public void startManualInputStrategy(){
        inputManager.setStrategy(inputManager.createManualStrategy());
        try {
            CustomCollection<Client> manualList = inputManager.loadData();
            int countOfAlexes = concurrentCounter.countAlexes(manualList);
            fullList.addAll(manualList);
            for(Client client : manualList){
                System.out.println(client);
            }
            System.out.println("Добавлено Алексеев: " + countOfAlexes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRandomDataStrategy(int count){
        inputManager.setStrategy(inputManager.createRandomStrategy(count));
        try {
            CustomCollection<Client> randomList = inputManager.loadData();
            int countOfAlexes = concurrentCounter.countAlexes(randomList);
            fullList.addAll(randomList);
            for(Client client : randomList){
                System.out.println(client);
            }
            System.out.println("Добавлено Алексеев: " + countOfAlexes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void startManualInputStrategy(ManualInputReaderStrategy strategy){
        inputManager.setStrategy(strategy);
        try {
            CustomCollection<Client> manualList = inputManager.loadData();
            int countOfAlexes = concurrentCounter.countAlexes(manualList);
            fullList.addAll(manualList);
            for(Client client : manualList){
                System.out.println(client);
            }
            System.out.println("Добавлено Алексеев: " + countOfAlexes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
