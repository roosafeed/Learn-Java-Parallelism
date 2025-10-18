package samples.roosafeed.common;

import java.util.List;

public abstract class TaskRunner implements Warmable {
    public abstract Integer run(List<Integer> numList);

    protected Integer task(List<Integer> numList) {
        Integer sum = 0;
        for (Integer num : numList) {
            sum += num;
            try {
                // simulate an I/O op
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
                // ignore the exception and move on
            }
        }

        return sum;
    }
}
