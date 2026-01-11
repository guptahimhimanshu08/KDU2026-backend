package producerconsumer;

public class Main {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            Thread producer = new Thread(new MessageProducer(queue));

            Thread consumer = new Thread(new MessageReceiver(queue));

            threads.add(producer);
            threads.add(consumer);

            producer.start();
            consumer.start();

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            LoggerFactory.getLogger(main.class).info("All producers and consumers finished.");


        }
    }
}
