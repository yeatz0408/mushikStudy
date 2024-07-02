package mushikStudy.com.mushikStudy.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@UtilityClass
public class FileUtil {
    public static String loadFile(Environment env, ResourceLoader resourceLoader) {
        try {
            String fileName = env.getProperty("project.file.path");
            Resource resource = resourceLoader.getResource("file:" + fileName);
            byte[] fileBytes = StreamUtils.copyToByteArray(resource.getInputStream());
            log.info("File loaded");
            return new String(fileBytes, StandardCharsets.UTF_8).trim();
        } catch (Exception e) {
            log.error("Problem occurred while reading file : {}", e.getMessage());
            return null;
        }
    }
    public static boolean writeFile(Environment env, String terms) {
        String fileName = env.getProperty("project.file.path");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(terms);
            return true;
        } catch (IOException e) {
            log.error("Problem occurred while writing file: {}", e.getMessage());
            return false;
        }
    }
}
