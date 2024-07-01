package mushikStudy.com.mushikStudy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MushikStudyApplication {

	private static final Logger logger = LogManager.getLogger(MushikStudyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MushikStudyApplication.class, args);
	}
}
