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

import java.util.ArrayList;
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
        List<String> meaningList = new ArrayList<>();
        meaningList.addAll(kunList.stream().limit(2).map(s -> TranslateUtil.translate(TranslateUtil.convertKunYomi(s, kanji), restTemplate)).toList());
        meaningList.add(TranslateUtil.translate(kanji, restTemplate));
        meaningList = meaningList.stream().distinct().toList();
//        meaningList.add(TranslateUtil.translate(kanji, restTemplate));
//        meaningList.add(TranslateUtil.translate(TranslateUtil.convertKunYomi(kunList.get(0), kanji), restTemplate));
//        meaningList = kunList.stream().limit(2).map(s -> TranslateUtil.translate(TranslateUtil.convertKunYomi(s, kanji), restTemplate)).toList();
//                kunList.stream().limit(1).map(s -> TranslateUtil.translate(TranslateUtil.convertKunYomi(s, kanji), restTemplate)).toList();

        return new KanjiElement(kanji, onList, kunList, meaningList);
    }
}
