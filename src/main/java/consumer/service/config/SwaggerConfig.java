package consumer.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Service API")
                        .version("1.0")
                        .description("API documentation for Product Service")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }//http://localhost:8082/swagger-ui.html
}
