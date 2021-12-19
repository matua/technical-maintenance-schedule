package ug.payway.technicalmaintenanceschedule.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneralConfiguration {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
