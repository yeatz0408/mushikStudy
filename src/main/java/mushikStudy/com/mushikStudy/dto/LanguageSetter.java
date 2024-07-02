package mushikStudy.com.mushikStudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mushikStudy.com.mushikStudy.constants.KanjiTerms;
import mushikStudy.com.mushikStudy.constants.Str;
import mushikStudy.com.mushikStudy.constants.enums.Lang;

@Data
@AllArgsConstructor
public class LanguageSetter {
    private Lang depart;
    private Lang arrive;

    public static LanguageSetter of(Lang depart, Lang arrive) {
        return new LanguageSetter(depart, arrive);
    }

    public String getLangPair() {
        return Str.AND + KanjiTerms.KanjiApi.LANGPAIR + Str.EQUAL + depart.getCode() + Str.OR + arrive.getCode();
    }
}
