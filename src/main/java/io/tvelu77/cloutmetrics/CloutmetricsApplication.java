package io.tvelu77.cloutmetrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main application.<br>
 * It's where all the magic begins (aka, spring boot is started).
 */
@SpringBootApplication
public class CloutmetricsApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloutmetricsApplication.class, args);
  }
  
  /**
   * Configures CORS policy.
   *
   * @return WebMvcConfigurer, enables cors policy.
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
      }
    };
  }

}
