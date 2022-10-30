package sky.pro.course3.homeworks;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HomeworksApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworksApplication.class, args);
    }

}
