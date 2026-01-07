package refactoredProducerconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue();
        ExecutorService producers = Executors.newFixedThreadPool(3);
        ExecutorService consumers = Executors.newFixedThreadPool(3);


        for (int i = 1; i <= 3; i++) {
            producers.submit(new MessageProducer(queue));
            consumers.submit(new MessageReceiver(queue));
        }

        producers.shutdown();
        consumers.shutdown();
        queue.clear();
        
    }
}
