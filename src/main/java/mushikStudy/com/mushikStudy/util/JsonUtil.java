package mushikStudy.com.mushikStudy.util;

import lombok.experimental.UtilityClass;
import org.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class JsonUtil {
    public static List<String> extractList(JSONArray array) {
        return IntStream.range(0, array.length())
                .mapToObj(array::getString)
                .collect(Collectors.toList());
    }
}
