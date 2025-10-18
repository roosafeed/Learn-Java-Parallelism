package samples.roosafeed.common.impl;

import samples.roosafeed.common.TaskRunner;

import java.util.List;

public class SingleTaskRunner extends TaskRunner {
    public Integer run(List<Integer> numList) {
        return this.task(numList);
    }

    @Override
    public void warmup() {
        run(List.of(1, 2, 3, 4, 5, 6));
    }
}
