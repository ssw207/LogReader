import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class EtcTest {
    
    @Test
    public void 정렬테스트() throws Exception {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 50);
        map.put("b", 10);
        map.put("c", 40);

        Comparator<Map.Entry<String, Integer>> comparator = (e1, e2) -> Integer.compare(e2.getValue(), e1.getValue());

        List<Map.Entry<String, Integer>> collect = map.entrySet().stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        System.out.println(collect);
    }

}