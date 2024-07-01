package mushikStudy.com.mushikStudy.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mushikStudy.com.mushikStudy.constants.KanjiTerms;
import mushikStudy.com.mushikStudy.dto.KanjiElement;
import mushikStudy.com.mushikStudy.dto.Meta;
import mushikStudy.com.mushikStudy.dto.response.KanjiResponse;
import mushikStudy.com.mushikStudy.util.FileUtil;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class KanjiService {
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final Environment env;

    public KanjiResponse load(long pageNo, int pageSize) {
        String targetMaterial = FileUtil.loadFile(env, resourceLoader);
        int index = (int) pageNo * pageSize;
        int lastIndexOfPage = (int) pageNo * pageSize + pageSize;

        if (lastIndexOfPage > targetMaterial.length()) {
            index = targetMaterial.length() - pageSize;
            lastIndexOfPage = targetMaterial.length();
        }

        List<KanjiElement> body = new ArrayList<>();
        for (; index < lastIndexOfPage ; index++) {
            char kanji = targetMaterial.charAt(index);
            String response = restTemplate.getForObject(KanjiTerms.KanjiApi.KANJI_API_URL + kanji, String.class);
            JSONObject jsonObject = new JSONObject(response);
            body.add(KanjiElement.of(jsonObject));
        }
        return new KanjiResponse(body, Meta.of(pageNo, pageSize));
    }
}
