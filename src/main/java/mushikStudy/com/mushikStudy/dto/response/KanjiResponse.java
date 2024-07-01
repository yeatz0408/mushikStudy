package mushikStudy.com.mushikStudy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KanjiResponse {
    long pageNo;
    int pageSize;
    String kanjiMaterial;
}
