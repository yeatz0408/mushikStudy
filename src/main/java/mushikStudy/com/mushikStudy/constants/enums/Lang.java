package mushikStudy.com.mushikStudy.constants.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Lang {

    English("en"), Japanese("ja"), Korean("ko"), UNKNOWN("unknown");

    private final String code;

    public static Lang byCode(String lang) {
        for (Lang itemType : Lang.values()) {
            if (itemType.getCode().equals(lang.toLowerCase())) {
                return itemType;
            }
        }
        return UNKNOWN;
    }


}
