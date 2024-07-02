package mushikStudy.com.mushikStudy.constants;

public class ApiV1 {
    private static final String VER = "/v1";

    public static final String LOAD_KANJI = VER + "/jp/kanji/load";
    public static final String LOAD_KANJI_RANDOM = VER + "/jp/kanji/load/random";

    public static final String ADD_KANJI = VER + "/jp/kanji/add";
    public static final String ADD_KANJI_PUSH = VER + "/jp/kanji/push";
    public static final String ADD_KANJI_REAR = VER + "/jp/kanji/rear";

    public static final String DELETE_KANJI = VER + "/jp/kanji/delete";
    public static final String DELETE_KANJI_FRONT = VER + "/jp/kanji/delete/front";
    public static final String DELETE_KANJI_REAR = VER + "/jp/kanji/delete/rear";

    private ApiV1() {
    }
}
