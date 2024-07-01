package mushikStudy.com.mushikStudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mushikStudy.com.mushikStudy.constants.KanjiTerms;
import mushikStudy.com.mushikStudy.util.JsonUtil;
import mushikStudy.com.mushikStudy.util.TranslateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class KanjiElement {
    String kanji;
    List<String> onYomi;
    List<String> kunYomi;
    List<String> meanings;

    public static KanjiElement of(JSONObject json, RestTemplate restTemplate) {

        if (!json.has(KanjiTerms.KANJI) || !json.has(KanjiTerms.KanjiApi.KUN_READINGS) || !json.has(KanjiTerms.KanjiApi.ON_READINGS) || !json.has(KanjiTerms.MEANINGS)) {
            log.error("Wrong json format.");
            return null;
        }

        JSONArray onArray = json.getJSONArray(KanjiTerms.KanjiApi.ON_READINGS);
        JSONArray kunArray = json.getJSONArray(KanjiTerms.KanjiApi.KUN_READINGS);
        JSONArray meaningArray = json.getJSONArray(KanjiTerms.MEANINGS);

        String kanji = json.getString(KanjiTerms.KANJI);
        List<String> onList = JsonUtil.extractList(onArray);
        List<String> kunList = JsonUtil.extractList(kunArray);
        List<String> meaningList = JsonUtil.extractList(meaningArray).stream().map(s -> TranslateUtil.translate(s, restTemplate)).toList();

        return new KanjiElement(kanji, onList, kunList, meaningList);
    }
}
