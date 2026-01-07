package task3;

import java.util.List;
import java.util.stream.IntStream;

public class ParallelStream {

    public static void main(String[] args) {

        List<Integer> numbers = IntStream
                .rangeClosed(1, 1_000_000_000)
                .boxed()
                .toList();


        long start = System.currentTimeMillis();
        long sequentialSum = numbers.stream()
                .mapToLong(Integer::intValue)
                .sum();
        long sequentialTime = System.currentTimeMillis() - start;

        System.out.println("Sequential Sum: " + sequentialSum);
        System.out.println("Sequential Time: " + sequentialTime + " ms");

        start = System.currentTimeMillis();
        long parallelSum = numbers.parallelStream()
                .mapToLong(Integer::intValue)
                .sum();
        long parallelTime = System.currentTimeMillis() - start;

        System.out.println("Parallel Sum: " + parallelSum);
        System.out.println("Parallel Time: " + parallelTime + " ms");
    }
}
