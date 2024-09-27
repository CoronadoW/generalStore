/*

Se elimina esta clase al haber incompatibilidad de la dependencia de springfox , la cual borre para evitar imcompatibilidades
Reemplazo dependencia springfox por :
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>
        </dependency>
Al hacer esto ya no es necesario la configuracion de esta clase : SwaggerConfig.




Esta configuracion solo es necesaria con la dependencia springfox. 
Al ser eliminada esta dependencia , esta configuracion ya no es necesaria.

package com.todocodeacademy.bazar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage( "com.todocodeacademy.bazar.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
*/