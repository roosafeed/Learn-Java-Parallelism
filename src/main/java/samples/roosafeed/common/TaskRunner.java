package samples.roosafeed.common;

import java.util.List;

public interface TaskRunner extends Warmable {
    Integer getSum(List<Integer> numList);
}
