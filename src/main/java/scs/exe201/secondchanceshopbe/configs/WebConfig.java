package scs.exe201.secondchanceshopbe.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Áp dụng CORS cho tất cả các URL
                .allowedOrigins("http://localhost:3000")  // Cho phép React từ localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Cho phép các phương thức HTTP
                .allowedHeaders("*")  // Cho phép tất cả các headers
                .allowCredentials(true);  // Cho phép gửi cookie (credentials)
    }
    
}
