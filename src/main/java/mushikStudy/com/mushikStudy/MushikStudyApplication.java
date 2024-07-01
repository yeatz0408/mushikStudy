package mushikStudy.com.mushikStudy;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class MushikStudyApplication {

	private static final Logger logger = LogManager.getLogger(MushikStudyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MushikStudyApplication.class, args);
	}
}
