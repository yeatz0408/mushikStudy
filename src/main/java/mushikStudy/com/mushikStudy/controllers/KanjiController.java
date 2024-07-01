package mushikStudy.com.mushikStudy.controllers;

import lombok.RequiredArgsConstructor;
import mushikStudy.com.mushikStudy.constants.ApiV1;
import mushikStudy.com.mushikStudy.dto.response.KanjiResponse;
import mushikStudy.com.mushikStudy.services.KanjiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KanjiController {

    private final KanjiService service;

    @GetMapping(ApiV1.LOAD_KANJI)
    public KanjiResponse loadKanji(@RequestParam(name="pageNo") long pageNo, @RequestParam(name="pageSize") int pageSize) {
        return service.load(pageNo, pageSize);
    }

}
