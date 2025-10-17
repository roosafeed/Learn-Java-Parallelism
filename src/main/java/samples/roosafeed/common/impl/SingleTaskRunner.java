package samples.roosafeed.common.impl;

import samples.roosafeed.common.TaskRunner;

import java.util.List;

public class SingleTaskRunner implements TaskRunner {
    @Override
    public Integer getSum(List<Integer> numList) {
        Integer sum = 0;
        for (Integer num : numList) {
            sum += num;
        }

        return sum;
    }

    @Override
    public void run() {
        getSum(List.of(1, 2, 3, 4, 5, 6));
    }
}
