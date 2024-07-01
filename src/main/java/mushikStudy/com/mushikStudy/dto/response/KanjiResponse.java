package mushikStudy.com.mushikStudy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import mushikStudy.com.mushikStudy.constants.KanjiTerms;
import org.json.JSONObject;

@Data
@AllArgsConstructor
public class KanjiResponse {
    long pageNo;
    int pageSize;
    String kanjiMaterial;
}
