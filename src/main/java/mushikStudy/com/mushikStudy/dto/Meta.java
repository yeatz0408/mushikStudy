package mushikStudy.com.mushikStudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meta {
    long pageNo;
    int pageSize;

    public static Meta of(long pageNo, int pageSize) {
        return new Meta(pageNo, pageSize);
    }
}
