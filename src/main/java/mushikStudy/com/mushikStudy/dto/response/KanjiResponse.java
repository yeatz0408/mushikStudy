package mushikStudy.com.mushikStudy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import mushikStudy.com.mushikStudy.dto.KanjiElement;
import mushikStudy.com.mushikStudy.dto.Meta;

import java.util.List;

@Data
@AllArgsConstructor
public class KanjiResponse {
    List<KanjiElement> body;
    Meta meta;
}
