package samples.roosafeed.common;

import samples.roosafeed.common.Warmable;

import java.util.List;

public interface TaskRunner extends Warmable {
    Integer getSum(List<Integer> numList);
}
