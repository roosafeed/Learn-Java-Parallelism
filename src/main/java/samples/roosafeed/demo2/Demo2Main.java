package samples.roosafeed.demo2;

import samples.roosafeed.common.Utils;
import samples.roosafeed.common.impl.ParallelTaskRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static samples.roosafeed.common.Utils.generateRandomNumbers;
import static samples.roosafeed.common.Utils.runTest;

// See if we can keep on increasing the number of threads to gain performance
public class Demo2Main {
    private static final int MIN_THREAD_COUNT_MULTIPLIER = 1;

    private static final int MAX_THREAD_COUNT_MULTIPLIER = 15;

    public static void main(String[] args) {
        // generate the test data
        List<Integer> testNumbers = generateRandomNumbers(600, 0, 10);
        ParallelTaskRunner parallelRunner = new ParallelTaskRunner();
        Map<Integer, Long> result = new HashMap<>();

        // Warm up the JVM
        Utils.warmUp(List.of(parallelRunner));

        for (int i = MIN_THREAD_COUNT_MULTIPLIER; i < MAX_THREAD_COUNT_MULTIPLIER; i++) {
            System.out.println("--------------------------------------------------------------");

            System.out.printf("Thread multiplier = %d %n", i);
            parallelRunner.setTaskMultiplier(i);
            Long duration = runTest(parallelRunner, testNumbers).toMillis();
            System.out.printf("Parallel task (multiplier= %d) finished in %d ms %n",
                    i,
                    duration
            );
            result.put(i, duration);
//            System.out.println("--------------------------------------------------------------");
        }

        System.out.println("-----------------------[RESULTS]-----------------------");
        result.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        System.out.printf("Thread multiplier=%2d -> avg time taken=%4d ms%n",
                                entry.getKey(), entry.getValue())
                );
    }
}
