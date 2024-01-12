package com.green.greengram4.common;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Greengram Ver.4"
        ,description = "인스타그램 클론 코딩 v4"
                ,version = "4.0.0")
,security = @SecurityRequirement(name = "authorization")
        )
@SecurityScheme(
        type = SecuritySchemeType.HTTP
        ,name = "authorization"
        ,in = SecuritySchemeIn.HEADER
        ,bearerFormat = "JWT"
        ,scheme = "Bearer"
)
public class SwaggerConfiguration {
    //위,아래랑 같지만 어노테이션이 더 많이 사용한다
    /*@Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Greengram Ver.3")
                        .description("인스타그램 클론 코딩 v3")
                        .version("3.0.0")
                );
    }*/
}
