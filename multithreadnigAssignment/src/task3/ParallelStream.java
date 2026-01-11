package task3;

import java.util.List;
import java.util.stream.IntStream;

public class ParallelStream {
        
        private static final long N = 1_000_000;

        public static void main(String[] args) {

                warmUp();

                
                long sequentialSum = measeureSequential();
                
                long parallelSum = measureParallel();
                

                System.out.println("Sequential execution time: " + sequentialTime + " ms");
                System.out.println("Parallel execution time:   " + parallelTime + " ms");
        }

        private static long measureSequential() {
                long start = System.nanoTime();

                long sum = LongStream
                        .rangeClosed(1, N)
                        .sum();

                long duration = System.nanoTime() - start;
                System.out.println("Sequential Sum: " + sum);
                return duration / 1_000_000;
        }
        private static long measureParallel() {
                long start = System.nanoTime();

                long sum = LongStream
                        .rangeClosed(1, N)
                        .parallel()
                        .sum();

                long duration = System.nanoTime() - start;
                System.out.println("Parallel Sum:   " + sum);
                return duration / 1_000_000;
        }

        private static void warmUp() {
                // Trigger JIT optimizations
                for (int i = 0; i < 3; i++) {
                LongStream.rangeClosed(1, 100_000).sum();
                LongStream.rangeClosed(1, 100_000).parallel().sum();
                }
        }
}
