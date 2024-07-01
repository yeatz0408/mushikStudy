package mushikStudy.com.mushikStudy.constants;

public class KanjiTerms {

    public static class KanjiApi {
        public static final String KANJI_API_URL = "https://kanjiapi.dev/v1/kanji/";
        public static final String TRANSLATION_API_URL_PREFIX = "https://api.mymemory.translated.net/get?q=";
        public static final String TRANSLATION_API_URL_SUFFIX = "&langpair=ja|ko";
        public static final String KUN_READINGS = "kun_readings";
        public static final String ON_READINGS = "on_readings";
        private KanjiApi() {
        }
    }

    public static final String MEANINGS = "meanings";
    public static final String KANJI = "kanji";
    public static final String KUNYOMI = "kunYomi";
    public static final String ONYOMI = "onYomi";
}
