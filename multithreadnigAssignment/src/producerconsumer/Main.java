package producerconsumer;

public class Main {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue();

        for (int i = 0; i < 3; i++) {
            new Thread(new MessageProducer(queue)).start();
            new Thread(new MessageReceiver(queue)).start();
        }
    }
}
