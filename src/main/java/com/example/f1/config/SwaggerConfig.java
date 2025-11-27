package com.example.f1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("F1 Korea API")
                        .description("OpenF1 API 통합 백엔드 서비스 - 실시간 F1 데이터 제공")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("F1 Korea Team")
                                .url("https://f1korea.vercel.app/")
                                .email("support@f1korea.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("로컬 개발 서버"),
                        new Server().url("https://api.f1korea.com").description("프로덕션 서버")
                ));
    }
}
