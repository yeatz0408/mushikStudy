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
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        ExecutorService executorService = Executors.newFixedThreadPool(50);
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
        List<CompletableFuture<String>> futures = targetKanjis.stream().map(
                s -> CompletableFuture.supplyAsync(
                        () -> restTemplate.getForObject(KanjiTerms.KanjiApi.KANJI_API_URL + s, String.class), executorService))
                .toList();
        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();
        List<KanjiElement> body = results.stream().parallel().map(s -> KanjiElement.of(new JSONObject(s), restTemplate, executorService)).toList();

        executorService.shutdown();
        watch.stop();
        return new KanjiResponse(body, Meta.of(pageNo, pageSize, watch.getTotalTimeSeconds()));
    }
}
