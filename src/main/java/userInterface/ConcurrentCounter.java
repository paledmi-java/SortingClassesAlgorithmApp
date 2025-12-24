package userInterface;

import dto.Client;
import input.CustomCollection;

import java.util.concurrent.*;

public class ConcurrentCounter {

    public int countAlexes(CustomCollection<Client> clients){
        CustomCollection<Client> clients1 = new CustomCollection<>();
        CustomCollection<Client> clients2 = new CustomCollection<>();

        for (int i = 0; i < clients.size()/2 ; i++){
            clients1.add(clients.get(i));
        }

        for (int i = clients.size()/2; i < clients.size(); i++){
            clients2.add(clients.get(i));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Long> count1 = executorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return clients1.stream().filter(c-> c.getName().contains("Алексей")).count();
            }
        });

        Future<Long> count2 = executorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return clients2.stream().filter(c-> c.getName().contains("Алексей")).count();
            }
        });

        executorService.shutdown();

        try {
            long result = count1.get() + count2.get();
            return Math.toIntExact(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
