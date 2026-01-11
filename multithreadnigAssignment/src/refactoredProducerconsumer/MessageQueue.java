package refactoredProducerconsumer;
import java.util.*;
import java.util.concurrent.locks.*;

public class MessageQueue {
    private final Queue<String> messageQueue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public void put(String message) {
        lock.lock();
        try {
            messageQueue.add(message);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String take() {
        lock.lock();
        try {
            while (messageQueue.isEmpty()) {
                notEmpty.await();
            }
            return messageQueue.poll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Consumer interrupted, shutting down");
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();
        try {
            messageQueue.clear();
        } finally {
            lock.unlock();
        }
    }
}
