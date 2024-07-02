package mushikStudy.com.mushikStudy.services;

import lombok.extern.log4j.Log4j2;
import mushikStudy.com.mushikStudy.constants.KanjiTerms;
import mushikStudy.com.mushikStudy.dto.KanjiElement;
import mushikStudy.com.mushikStudy.dto.Meta;
import mushikStudy.com.mushikStudy.dto.response.KanjiResponse;
import mushikStudy.com.mushikStudy.repositories.KanjiRepository;
import mushikStudy.com.mushikStudy.util.FormatUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Log4j2
@Service
public class KanjiService {
    private final RestTemplate restTemplate;
    private final KanjiRepository repository;

    private static List<String> history = new ArrayList<>();

    public KanjiService(RestTemplate restTemplate, KanjiRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public KanjiResponse load(long pageNo, int pageSize) {
        StopWatch watch = new StopWatch();
        watch.start();
        String targetMaterial = repository.load();

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
        String targetMaterial = repository.load();
        pageSize = Math.min(Math.min(pageSize, 50), targetMaterial.length());
        List<String> terms = extractUniqueCharacters(targetMaterial, pageSize);
        List<KanjiElement> elements = load(terms);
        watch.stop();
        return new KanjiResponse(elements, Meta.of(-1, pageSize, watch.getTotalTimeSeconds()));
    }
    private List<String> extractUniqueCharacters(String target, int pageSize) {
        String targetMaterial = repository.load();
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

    public String add(String terms, int index) {
        return repository.add(FormatUtil.validateKanji(terms), index);
    }
    public String push(String terms) {
        return repository.add(FormatUtil.validateKanji(terms), 0);
    }
    public String pushRear(String terms) {
        return repository.add(FormatUtil.validateKanji(terms), repository.load().length()-1);
    }

    public String delete(String terms) {
        return repository.delete(FormatUtil.validateKanji(terms));
    }
    public String deleteFront(int index) {
        return repository.delete(index, repository.load().length());
    }
    public String deleteRear(int index) {
        return repository.delete(0, repository.load().length()-index);
    }
}
