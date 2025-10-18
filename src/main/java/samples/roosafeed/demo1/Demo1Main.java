package samples.roosafeed.demo1;

import samples.roosafeed.common.TaskRunner;
import samples.roosafeed.common.Utils;
import samples.roosafeed.common.Warmable;
import samples.roosafeed.common.impl.ConcurrentTaskRunner;
import samples.roosafeed.common.impl.ParallelTaskRunner;
import samples.roosafeed.common.impl.SingleTaskRunner;

import java.util.ArrayList;
import java.util.List;

import static samples.roosafeed.common.Utils.generateRandomNumbers;
import static samples.roosafeed.common.Utils.runTest;

// Single task vs Concurrent vs Parallel
public class Demo1Main {
    public static void main(String[] args) {
        // generate the test data
        List<Integer> testNumbers = generateRandomNumbers(500, 0, 10);

        // test classes
        TaskRunner singleRunner = new SingleTaskRunner();
        TaskRunner concurrentRunner = new ConcurrentTaskRunner();
        TaskRunner parallelRunner = new ParallelTaskRunner();

        // warm up the JVM
        List<Warmable> warmables = new ArrayList<>();
        warmables.add(singleRunner);
        warmables.add(concurrentRunner);
        warmables.add(parallelRunner);

        Utils.warmUp(warmables);

        System.out.printf("Single task finished in %d ms %n", runTest(singleRunner, testNumbers).toMillis());
        System.out.printf("Concurrent task finished in %d ms %n", runTest(concurrentRunner, testNumbers).toMillis());
        System.out.printf("Parallel task finished in %d ms %n", runTest(parallelRunner, testNumbers).toMillis());
    }
}
