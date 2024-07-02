package mushikStudy.com.mushikStudy.repositories;

import mushikStudy.com.mushikStudy.util.FileUtil;
import mushikStudy.com.mushikStudy.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class KanjiRepository {

    private final Environment env;
    private final ResourceLoader resourceLoader;

    private String data;

    @Autowired
    public KanjiRepository(Environment env, ResourceLoader resourceLoader) {
        this.env = env;
        this.resourceLoader = resourceLoader;

        data = FileUtil.loadFile(env, resourceLoader);
    }

    public String load() {
        return data;
    }

    public String add(String terms, int index) {
        if (index < 0 || index > data.length()) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        StringBuilder sb = new StringBuilder(data);
        terms = FormatUtil.removeDuplicates(FormatUtil.removeDuplicates(terms), data);
        sb.insert(index, terms);

        if (FileUtil.writeFile(env, sb.toString())) {
            data = sb.toString();
            return sb.toString();
        } else {
            return null;
        }
    }
    public String delete(String terms) {
        char[] array = FormatUtil.removeDuplicates(terms).toCharArray();
        StringBuilder result = new StringBuilder();
        for (char i : array) {
            for (char c : data.toCharArray()) {
                if (c != i) {
                    result.append(c);
                }
            }
        }
        String toReturn = result.toString();
        if (FileUtil.writeFile(env, toReturn)) {
            data = toReturn;
            return toReturn;
        } else {
            return null;
        }
    }
    public String delete(int start, int end) {
        String toReturn = data.substring(start, end);
        if (FileUtil.writeFile(env, toReturn)) {
            data = toReturn;
            return toReturn;
        } else {
            return null;
        }
    }
}
