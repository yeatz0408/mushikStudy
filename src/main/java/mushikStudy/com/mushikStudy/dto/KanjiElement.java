package mushikStudy.com.mushikStudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KanjiElement {
    String kanji;
    List<String> kunYomi;
    List<String> onYomi;
    List<String> meanings;
}
