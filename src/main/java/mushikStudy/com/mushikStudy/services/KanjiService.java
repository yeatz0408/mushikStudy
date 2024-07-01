package mushikStudy.com.mushikStudy.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mushikStudy.com.mushikStudy.dto.response.KanjiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
public class KanjiService {

    private final RestTemplate restTemplate;

    public KanjiResponse load(long pageNo, int pageSize) {
        final String JISHO_API_URL = "https://jisho.org/api/v1/search/words?keyword=";
        final String STUDY_MATERIAL = "人一大年二三日見出上行同多学社時本事知子自好手発間方自力新立場合心考高動通信声思体目者頭題分書数聞事物元後言次社用意京気入立田取町回品作同家教話社使事本者日自気分子体家言年長来田事場多会同日時新行見学後出間車持意気方部数読思語思料京何市使活強心問文頭会員内知外身入広自進数体品感社定社仕新田意者本心文声田日者会者国信持回内京分声取活定教広子京学心頭高言社回何作声手体動市話強作活言社同田京身場何作外聞数京新感立日家料後意学強身声言数教用外部持文感強日文用者同自知元数市作数文田文後意学用同後思考内動間読場体立京内持車料者動社同高者身気京自田作社声強内料問新意数自京立子場広信話者何文分意自考者会感京広同体部活子元京問者強広心者外会聞社意強持気新同定場学意文感持用自場社聞外体新京会者自料文内元体内回外学分学用声気強同自文学声定子高者内元後外会元作意自分子学強話新知";
        String response = restTemplate.getForObject(JISHO_API_URL + STUDY_MATERIAL.charAt(3), String.class);
        return new KanjiResponse(pageNo, pageSize, response);
    }


}
