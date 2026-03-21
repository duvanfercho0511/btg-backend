package duvan_vargas.btg_backend.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Se deja swaggerConfig pese a que lo hace OpenApi
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("BTG Backend API")
                        .version("1.0")
                        .description("Documentación de la API"));
    }
}