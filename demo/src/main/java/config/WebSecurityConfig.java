//package config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecu {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().and() // Enable CORS and integrate with Spring Security
//                .csrf().disable() // Disable CSRF protection for non-browser clients like WebSocket
//                .authorizeRequests()
//                .antMatchers("/ws/**").permitAll() // Permit all requests to the WebSocket endpoint
//                .anyRequest().authenticated(); // Secure other endpoints
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Replace with your client's URL
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
