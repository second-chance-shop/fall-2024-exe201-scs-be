package scs.exe201.secondchanceshopbe.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Example API")
                        .version("1.0.0")
                        .description("This is a simple API example using OpenAPI 3.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
//        @Bean
//        CorsConfigurationSource corsConfigurationSource() {
//            CorsConfiguration configuration = new CorsConfiguration();
//            configuration.setAllowedOrigins(Arrays.asList("*"));
//            configuration.setAllowedMethods(Arrays.asList("*"));
//            configuration.setAllowedHeaders(Arrays.asList("*"));
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**", configuration);
//            return source;
//        }
    @Bean
    public CorsFilter corsFilter()  {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        //  Cấu  hình cho phép cross-origin
        config.setAllowCredentials(true);  //  Cho phép cookie
        config.setAllowedOriginPatterns(List.of("*"));  // Hoặc chỉ định  danh sách  domain cụ thể
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**",  config);
        return  new  CorsFilter(source);
    }
}
