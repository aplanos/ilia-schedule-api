package com.ilia.schedule.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("batidas-api-1.0")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(
                        ant("/api/v1/**")
                )
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("API para controle de ponto")
                .description("Tratase de uma API para controle de ponto, seguindo o contrato, " +
                        "que permite realizar as seguintes ações: Registrar os horários da " +
                        "jornada diária de trabalho. Apenas 4 horários podem ser registrados por dia. " +
                        "Deve haver no mínimo 1 hora de almoço. Sábado e domingo não são permitidos " +
                        "como dia de trabalho.")
                .version("1.0.0")
                .build();
    }
}
