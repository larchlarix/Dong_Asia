package com.example.DongAisa.config;

import com.example.DongAisa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.io.IOException;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig   {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private AuthenticationFailureHandler authenticationFailureHandler;

    private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
               /*

                .authorizeHttpRequests((authorizeRequests) ->
                  authorizeRequests
                    .requestMatchers("/swagger-resources/**").permitAll()
                    .requestMatchers("/","/user/**","/news/**","/analysis","/keyword_analysis").permitAll()
                          .requestMatchers("/admin/**").hasRole("ADMIN")
                          .anyRequest().authenticated()
                )*/
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN"))

                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )


                .headers((headerConfig)->
                        headerConfig.frameOptions(frameOptionsConfig->
                                frameOptionsConfig.disable()

                        )

                )


                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .formLogin((formLogin) -> {
                    /* 권한이 필요한 요청은 해당 url로 리다이렉트 */
                    formLogin.loginPage("/user/login")
                             .defaultSuccessUrl("/")
                             .usernameParameter("userEmail")
                             .passwordParameter("userPassword")
                             .failureUrl("/user/login/error")
                             .failureHandler(authenticationFailureHandler);

                })
                .logout((logout) ->{
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                });

        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
                // 로그 추가
                log.error("로그인 실패: {}", exception.getMessage());

                // 여기에 실패 시 처리할 로직을 추가하세요.
                // 예: 에러 메시지 설정 등
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }

    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }
}





