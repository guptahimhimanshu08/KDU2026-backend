package task3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableFuture{

    public static void main(String[] args) throws Exception {

        int N = 100;

        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int i = 1; i <= N; i++) {
                sum += i;
            }
            return sum;
        };
        
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(sumTask);
       
        System.out.println("Task submitted, waiting for result...");
        
        int result = future.get();
        
        System.out.println("Sum from 1 to " + N + " = " + result);

        executor.shutdown();

        try{
            if(!executor.awaitTermination(10, TimeUnit.SECONDS)){
                executor.shutdownNow();
            }

            
        }catch(InterruptedException e){
            LoggerFactory.getLogger(main.class).error(e);
        }
    }
}
