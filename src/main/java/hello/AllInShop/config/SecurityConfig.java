package hello.AllInShop.config;

import hello.AllInShop.handler.LoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig  {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //로그인 테스트
    /*@Bean
    public InMemoryUserDetailsManager userDetailService() {
        UserDetails user = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("1111"))
                .roles("USER")
                .build();

        log.info("userDetailService........................");
        log.info(String.valueOf(user));

        return new InMemoryUserDetailsManager(user);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> {
            auth.antMatchers("/home/all").permitAll();
            auth.antMatchers("/home/member").hasRole("USER");
        });

        http.formLogin(); //인증,인가 문제시 에러화면이 아닌 로그인 화면으로 이동하게 해주는 로직
        http.csrf().disable();
        http.logout();
        http.oauth2Login().successHandler(successHandler());

        return http.build();
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler(passwordEncoder());
    }


}
