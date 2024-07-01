package mushikStudy.com.mushikStudy.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Log4j2
@UtilityClass
public class FileUtil {
    public static String loadFile(ResourceLoader resourceLoader) {
        try {
            Resource resource = resourceLoader.getResource("file:" + "kanji.txt");
            byte[] fileBytes = StreamUtils.copyToByteArray(resource.getInputStream());
            log.info("File loaded");
            return new String(fileBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Problem occurred while reading file : {}", e.getMessage());
            return null;
        }
    }
}
