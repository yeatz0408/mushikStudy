package mushikStudy.com.mushikStudy.controllers;

import lombok.RequiredArgsConstructor;
import mushikStudy.com.mushikStudy.constants.ApiV1;
import mushikStudy.com.mushikStudy.dto.response.KanjiResponse;
import mushikStudy.com.mushikStudy.services.KanjiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KanjiController {

    private final KanjiService service;

    @GetMapping(ApiV1.LOAD_KANJI)
    public ResponseEntity<KanjiResponse> loadKanji(@RequestParam(name="pageNo") long pageNo, @RequestParam(name="pageSize") int pageSize) {
        return ResponseEntity.ok(service.load(pageNo, pageSize));
    }

    @GetMapping(ApiV1.LOAD_KANJI_RANDOM)
    public ResponseEntity<KanjiResponse> loadRandomKanji(@RequestParam(name="pageSize") int pageSize) {
        return null;
    }


    @GetMapping(ApiV1.ADD_KANJI)
    public ResponseEntity<String> addKanji(@RequestParam(name="kanjis") String kanjis, @RequestParam(name="index") int index) {
        return null;
    }

    @GetMapping(ApiV1.ADD_KANJI_PUSH)
    public ResponseEntity<String> pushKanji(@RequestParam(name="kanjis") String kanjis) {
        return null;
    }

    @GetMapping(ApiV1.ADD_KANJI_REAR)
    public ResponseEntity<String> pushKanjiRear(@RequestParam(name="kanjis") String kanjis) {
        return null;
    }


    @GetMapping(ApiV1.DELETE_KANJI)
    public ResponseEntity<String> deleteKanji(@RequestParam(name="kanjis") String kanjis) {
        return null;
    }

    @GetMapping(ApiV1.DELETE_KANJI_FRONT)
    public ResponseEntity<String> deleteKanjiFront() {
        return null;
    }

    @GetMapping(ApiV1.DELETE_KANJI_REAR)
    public ResponseEntity<String> deleteKanjiRear() {
        return null;
    }
}
