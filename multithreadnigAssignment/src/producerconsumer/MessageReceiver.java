package producerconsumer;

public class MessageReceiver implements Runnable {
    private final MessageQueue queue;

    public MessageReceiver(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            String message = queue.take();
            System.out.println("Consumed: " + message);
        }
    }
}

