package mushikStudy.com.mushikStudy.services;

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
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class KanjiService {
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final Environment env;
    private final String targetMaterial;

    public KanjiService(RestTemplate restTemplate, ResourceLoader resourceLoader, Environment env) {
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
        this.env = env;
        this.targetMaterial = FileUtil.loadFile(env, resourceLoader);
    }

    public KanjiResponse load(long pageNo, int pageSize) {
        StopWatch watch = new StopWatch();
        watch.start();
        int index = (int) pageNo * pageSize;
        int lastIndexOfPage = (int) pageNo * pageSize + pageSize;

        if (lastIndexOfPage > targetMaterial.length()) {
            index = targetMaterial.length() - pageSize;
            lastIndexOfPage = targetMaterial.length();
        }

        if (pageSize > 50 || pageSize > targetMaterial.length()) {
            pageSize = Math.min(targetMaterial.length(), 50);
        }
        List<String> targetKanjis = new ArrayList<>();
        for (; index < lastIndexOfPage ; index++) {
            targetKanjis.add(String.valueOf(targetMaterial.charAt(index)));
        }

        List<String> responses = targetKanjis.stream().parallel().map(k -> restTemplate.getForObject(KanjiTerms.KanjiApi.KANJI_API_URL + k, String.class)).toList();
        List<KanjiElement> body = responses.stream().parallel().map(s -> KanjiElement.of(new JSONObject(s), restTemplate)).toList();

        watch.stop();
        return new KanjiResponse(body, Meta.of(pageNo, pageSize, watch.getTotalTimeSeconds()));
    }
}
