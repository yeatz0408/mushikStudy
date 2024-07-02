package mushikStudy.com.mushikStudy.util;

import lombok.experimental.UtilityClass;

import java.util.LinkedHashSet;
import java.util.Set;

@UtilityClass
public class FormatUtil {
    public String validateKanji(String terms) {
        String kanjiRegex = "[\\u4E00-\\u9FAF]+";
        StringBuilder kanjiOnly = new StringBuilder();
        for (char c : terms.toCharArray()) {
            if (String.valueOf(c).matches(kanjiRegex)) {
                kanjiOnly.append(c);
            }
        }
        return kanjiOnly.toString();
    }
    public String removeDuplicates(String terms) {
        char[] arrays = terms.toCharArray();
        Set<Character> uniqueChars = new LinkedHashSet<>();
        for (char c : arrays) {
            uniqueChars.add(c);
        }
        char[] result = new char[uniqueChars.size()];
        int i = 0;
        for (char c : uniqueChars) {
            result[i++] = c;
        }
        return new String(result);
    }
    public String removeDuplicates(String terms, String wholeText) {
        StringBuilder filteredTerms = new StringBuilder();
        for (char termChar : terms.toCharArray()) {
            if (wholeText.indexOf(termChar) == -1) {
                filteredTerms.append(termChar);
            }
        }
        return filteredTerms.toString();
    }
}
