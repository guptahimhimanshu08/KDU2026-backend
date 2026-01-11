package producerconsumer;

import java.time.LocalDateTime;

public class MessageProducer implements Runnable {
    private final MessageQueue queue;

    public MessageProducer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            String message = "Message " + i + " produced at " + LocalDateTime.now();
            queue.put(message);
            System.out.println("Produced: " + message);
        }
    }
}
