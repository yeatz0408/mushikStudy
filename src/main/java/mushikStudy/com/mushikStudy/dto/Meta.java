package mushikStudy.com.mushikStudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meta {
    long pageNo;
    int pageSize;
    long total;
    double took;


    public static Meta of(long pageNo, int pageSize, long total, double took) {
        return new Meta(pageNo, pageSize, total, took);
    }
}
