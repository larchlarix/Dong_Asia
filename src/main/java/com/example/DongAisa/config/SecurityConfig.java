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
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.io.IOException;
import java.net.URLEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
                            // .failureUrl("/user/login/error")
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
        provider.setUserDetailsService(userService);
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

                String errorMessage;

                if(exception instanceof BadCredentialsException) {
                    errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
                } else if (exception instanceof InternalAuthenticationServiceException) {
                    errorMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요. ";
                } else if (exception instanceof UsernameNotFoundException) {
                    errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
                } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
                    errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
                } else {
                    errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
                }

                errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); /* 한글 인코딩 깨진 문제 방지 */
                setDefaultFailureUrl("/user/login?error=true&exception="+errorMessage);
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





