package samples.roosafeed.common.impl;

import samples.roosafeed.common.TaskRunner;
import samples.roosafeed.common.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Truly parallel: fixed thread pool sized to available processors (cores)
public class ParallelTaskRunner extends TaskRunner {
    private int TASK_COUNT_MULTIPLIER = 2;

    @Override
    public void warmup() {
        getSumInternal(List.of(1, 2, 3, 4, 5, 6), 2);
    }

    // just to prevent printing while warming up
    public Integer run(List<Integer> numList) {
        int cores = Runtime.getRuntime().availableProcessors();
        int taskCount = cores * TASK_COUNT_MULTIPLIER;
        System.out.println("=== PARALLEL (fixed thread pool, cores=" + cores + ") ===");

        return this.getSumInternal(numList, taskCount);
    }

    // just to prevent printing while warming up
    private Integer getSumInternal(List<Integer> numList, int taskCount) {
        Integer sum = 0;
        List<List<Integer>> inputParts = Utils.splitList(numList, taskCount);

        try (ExecutorService executor = Executors.newFixedThreadPool(taskCount)) {
            List<Future<Integer>> futures = new ArrayList<>();

            for (int i = 0; i < taskCount; i++) {
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

    public void setTaskMultiplier(int multiplier) {
        if (multiplier < 1) {
            throw new IllegalArgumentException("multiplier should be greater than 0");
        }
        this.TASK_COUNT_MULTIPLIER = multiplier;
    }

}
