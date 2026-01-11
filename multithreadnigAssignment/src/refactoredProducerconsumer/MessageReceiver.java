package refactoredProducerconsumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver implements Runnable {
    private final MessageQueue queue;

    public MessageReceiver(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            for (int i = 1; i <= 5; i++) {
                String message = queue.take();
                if (message == null) {
                    break;
                }
                System.out.println("Consumed: " + message);
            }
        }catch(Exception e){
            Logger log = LoggerFactory.getLogger(MessageReceiver.class);
            log.error("Consumer error: "+ e);
        }
    }
}