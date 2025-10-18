package samples.roosafeed.common.impl;

import samples.roosafeed.common.TaskRunner;
import samples.roosafeed.common.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Use virtual threads so tasks overlap
public class ConcurrentTaskRunner extends TaskRunner {
    private static final int TASK_COUNT = 15;

    @Override
    public void warmup() {
        getSumInternal(List.of(1, 2, 3, 4, 5, 6));
    }

    public Integer run(List<Integer> numList) {
        System.out.println("=== CONCURRENT (" + TASK_COUNT + " virtual threads) ===");

        return this.getSumInternal(numList);
    }

    // just to prevent printing while warming up
    private Integer getSumInternal(List<Integer> numList) {
        Integer sum = 0;

        List<List<Integer>> inputParts = Utils.splitList(numList, TASK_COUNT);

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();

            for (int i = 0; i < TASK_COUNT; i++) {
                List<Integer> input = inputParts.get(i);
                futures.add(executor.submit(() -> this.task(input)));
            }

            // wait for all the parts to finish
            for (Future<Integer> future : futures) {
                try {
                    sum += future.get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return sum;
    }
}
