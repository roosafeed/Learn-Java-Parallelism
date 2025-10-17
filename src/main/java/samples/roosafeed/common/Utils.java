package samples.roosafeed.common;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {
    // The JIT compiler optimizes the bytecode after it’s been executed a few times.
    // The first run will include class loading, interpreter execution, and compilation overhead,
    //  making it slower and inconsistent.
    // By warming up, we ensure all methods are JIT-compiled and cached before we measure timing
    public static void warmUp(List<Warmable> warmables) {
        System.out.println("Warming up...");
        for (int i = 0; i < 5; i++) {
            for (Warmable warmable : warmables) {
                warmable.run();
            }
        }
    }

    public static <T> List<List<T>> splitList(List<T> list, int numberOfParts) {
        List<List<T>> result = new ArrayList<>();
        if (list == null || list.isEmpty() || numberOfParts <= 0) {
            return result;
        }

        int totalSize = list.size();
        int baseSize = totalSize / numberOfParts;     // minimum size per part
        int remainder = totalSize % numberOfParts;    // distribute this remainder one by one

        int start = 0;
        for (int i = 0; i < numberOfParts; i++) {
            int extra = (i < remainder) ? 1 : 0;      // some parts get one extra item
            int end = start + baseSize + extra;

            if (start >= totalSize) {
                // if there are more parts than elements, just add empty lists
                result.add(Collections.emptyList());
            } else {
                result.add(list.subList(start, Math.min(end, totalSize)));
            }

            start = end;
        }

        return result;
    }

    public static List<Integer> generateRandomNumbers(int count, int min, int max) {
        List<Integer> numbers = new ArrayList<>(count);
        Random random = new Random();
        int i = 0;

        while (i < count) {
            Integer number = random.nextInt(max - min + 1) + min;
            numbers.add(number);
            i++;
        }

        return numbers;
    }

    public static Duration runTest(TaskRunner taskRunner, List<Integer> input) {
        System.out.println("==========================================");
        System.out.println("Running task " + taskRunner.getClass().getSimpleName());
        List<Duration> durations = new ArrayList<>();

        int totalIterations = 5;
        for (int i = 0; i < totalIterations; i++) {
            Duration duration = runTestInternal(taskRunner, input, i+1, totalIterations);
            durations.add(duration);
        }

        System.out.println("==========================================");

        return averageDuration(durations);
    }

    private static Duration runTestInternal(TaskRunner taskRunner, List<Integer> input, int iteration, int totalIteration) {
        // run the garbage collector to cleanup any residues before timing
        System.gc();

        Instant start = Instant.now();

        // run the actual task
        Integer result = taskRunner.getSum(input);

        Instant end = Instant.now();

        System.out.printf("[%d/%d] Task start: %d | Task end: %d%n",
                iteration,
                totalIteration,
                start.getEpochSecond(),
                end.getEpochSecond());
//        System.out.printf("Task returned: %d %n", result);

        return Duration.between(start, end);
    }

    private static Duration averageDuration(List<Duration> durations) {
        if (durations == null || durations.isEmpty()) {
            return Duration.ZERO;
        }

        // Sum all durations in nanoseconds
        long totalNanos = 0;
        for (Duration d : durations) {
            totalNanos += d.toNanos();
        }

        // Compute average
        long avgNanos = totalNanos / durations.size();

        return Duration.ofNanos(avgNanos);
    }
}
