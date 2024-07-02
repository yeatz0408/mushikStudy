package mushikStudy.com.mushikStudy.controllers;

import lombok.RequiredArgsConstructor;
import mushikStudy.com.mushikStudy.constants.ApiV1;
import mushikStudy.com.mushikStudy.dto.response.KanjiResponse;
import mushikStudy.com.mushikStudy.services.KanjiService;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok(service.loadRandom(pageSize));
    }


    @GetMapping(ApiV1.ADD_KANJI)
    public ResponseEntity<String> add(@RequestParam(name="terms") String terms, @RequestParam(name="index") int index) {
        String toReturn = service.add(terms, index);
        if (toReturn == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while adding the terms.");
        } else {
            return ResponseEntity.ok(toReturn);
        }
    }

    @GetMapping(ApiV1.ADD_KANJI_PUSH)
    public ResponseEntity<String> push(@RequestParam(name="terms") String terms) {
        String toReturn = service.push(terms);
        if (toReturn == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while adding the terms.");
        } else {
            return ResponseEntity.ok(toReturn);
        }
    }

    @GetMapping(ApiV1.ADD_KANJI_REAR)
    public ResponseEntity<String> pushRear(@RequestParam(name="terms") String terms) {
        String toReturn = service.pushRear(terms);
        if (toReturn == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while adding the terms.");
        } else {
            return ResponseEntity.ok(toReturn);
        }
    }


    @GetMapping(ApiV1.DELETE_KANJI)
    public ResponseEntity<String> delete(@RequestParam(name="terms") String terms) {
        String toReturn = service.delete(terms);
        if (toReturn == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while adding the terms.");
        } else {
            return ResponseEntity.ok(toReturn);
        }
    }

    @GetMapping(ApiV1.DELETE_KANJI_FRONT)
    public ResponseEntity<String> deleteFront(@RequestParam(name="number") int number) {
        String toReturn = service.deleteFront(number);
        if (toReturn == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while adding the terms.");
        } else {
            return ResponseEntity.ok(toReturn);
        }
    }

    @GetMapping(ApiV1.DELETE_KANJI_REAR)
    public ResponseEntity<String> deleteRear(@RequestParam(name="number") int number) {
        String toReturn = service.deleteRear(number);
        if (toReturn == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while adding the terms.");
        } else {
            return ResponseEntity.ok(toReturn);
        }
    }
}
