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
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Log4j2
@Service
public class KanjiService {
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final Environment env;
    private final String targetMaterial;
    private static List<String> history = new ArrayList<>();

    public KanjiService(RestTemplate restTemplate, ResourceLoader resourceLoader, Environment env) {
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
        this.env = env;
        this.targetMaterial = FileUtil.loadFile(env, resourceLoader);
    }

    public KanjiResponse load(long pageNo, int pageSize) {
        StopWatch watch = new StopWatch();
        watch.start();

        pageSize = Math.min(Math.min(pageSize, 50), targetMaterial.length());
        int index = Math.max((int) pageNo * pageSize, 0);
        int lastIndexOfPage = Math.min(index + pageSize, targetMaterial.length());

        List<String> terms = new ArrayList<>();
        for (; index < lastIndexOfPage ; index++) {
            terms.add(String.valueOf(targetMaterial.charAt(index)));
        }
        List<KanjiElement> elements = load(terms);
        watch.stop();
        return new KanjiResponse(elements, Meta.of(pageNo, pageSize, watch.getTotalTimeSeconds()));
    }

    public KanjiResponse loadRandom(int pageSize) {
        StopWatch watch = new StopWatch();
        watch.start();
        pageSize = Math.min(Math.min(pageSize, 50), targetMaterial.length());
        List<String> terms = extractUniqueCharacters(targetMaterial, pageSize);
        List<KanjiElement> elements = load(terms);
        watch.stop();
        return new KanjiResponse(elements, Meta.of(-1, pageSize, watch.getTotalTimeSeconds()));
    }
    private List<String> extractUniqueCharacters(String target, int pageSize) {
        List<Character> characters = new ArrayList<>();
        for (char c : target.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters, new Random());
        List<String> uniqueCharacters = new ArrayList<>();
        for (int i = 0; uniqueCharacters.size() < pageSize && i < targetMaterial.length(); i++) {
            if (!history.contains(String.valueOf(characters.get(i)))) {
                uniqueCharacters.add(String.valueOf(characters.get(i)));
            }
        }
        if (history.size() >= targetMaterial.length()) {
            history.clear();
        }
        return uniqueCharacters;
    }

    private List<KanjiElement> load(List<String> terms) {
        List<String> responses = terms.stream().parallel().map(k -> restTemplate.getForObject(KanjiTerms.KanjiApi.KANJI_API_URL + k, String.class)).toList();
        return responses.stream().parallel().map(s -> KanjiElement.of(new JSONObject(s), restTemplate)).toList();
    }



//    public KanjiResponse load(long pageNo, int pageSize) {
//        StopWatch watch = new StopWatch();
//        watch.start();
//
//        pageSize = Math.min(Math.min(pageSize, 50), targetMaterial.length());
//        int index = Math.max((int) pageNo * pageSize, 0);
//        int lastIndexOfPage = Math.min(index + pageSize, targetMaterial.length());
//
//        List<String> targetKanjis = new ArrayList<>();
//        for (; index < lastIndexOfPage ; index++) {
//            targetKanjis.add(String.valueOf(targetMaterial.charAt(index)));
//        }
//
//        List<String> responses = targetKanjis.stream().parallel().map(k -> restTemplate.getForObject(KanjiTerms.KanjiApi.KANJI_API_URL + k, String.class)).toList();
//        List<KanjiElement> body = responses.stream().parallel().map(s -> KanjiElement.of(new JSONObject(s), restTemplate)).toList();
//
//        watch.stop();
//        return new KanjiResponse(body, Meta.of(pageNo, pageSize, watch.getTotalTimeSeconds()));
//    }
}
