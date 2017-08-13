package org.hhorton.utility;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/17/17.
 */
public class SortUtility {
    public static void sortList(List<Map<String, Object>> result, String sortByColumn) {
        result.sort((Map<String, Object> map, Map<String, Object> map2) -> {
            long m1SortColumn = (long) map.get(sortByColumn);
            long m2SortColumn = (long) map2.get(sortByColumn);

            if (m1SortColumn < m2SortColumn) {
                return 1;
            } else if (m1SortColumn > m2SortColumn) {
                return -1;
            } else {
                return 0;
            }
        });
    }
}
