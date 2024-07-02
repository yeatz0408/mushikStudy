package mushikStudy.com.mushikStudy.util;

import lombok.experimental.UtilityClass;
import mushikStudy.com.mushikStudy.constants.KanjiTerms;
import mushikStudy.com.mushikStudy.dto.LanguageSetter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@UtilityClass
public class TranslateUtil {

    public static String translate(String targetText, LanguageSetter langSetter, RestTemplate restTemplate) {
        String url = KanjiTerms.KanjiApi.TRANSLATION_API_URL_PREFIX + targetText + langSetter.getLangPair();
        ResponseEntity<Map> testResponse = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> responseBody = (Map<String, Object>) testResponse.getBody().get("responseData");
        return responseBody.get(KanjiTerms.KanjiApi.TRANSLATED_TEXT).toString();
    }

    public static String convertKunYomi(String kunYomi, String kanji) {
        if (kunYomi.contains(".")) {
            return kanji + kunYomi.substring(kunYomi.indexOf(".") + 1);
        } else {
            return kanji;
        }
    }
}
