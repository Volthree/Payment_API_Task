package vladislavmaltsev.paymenttaskapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vladislavmaltsev.paymenttaskapi.component.CustomAuth;
import vladislavmaltsev.paymenttaskapi.component.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuth customAuth;
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(
            CustomAuth customAuth,
            JwtFilter jwtFilter,
                          AuthenticationProvider authenticationProvider) {
        this.customAuth = customAuth;
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("Enter securityFilterChain in config");
        httpSecurity.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(customAuth)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
//                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ;
        System.out.println("End securityFilterChain in config");
        return httpSecurity.build();
    }
}
