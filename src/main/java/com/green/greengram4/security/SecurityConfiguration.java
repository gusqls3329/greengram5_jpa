package com.green.greengram4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //authorizeHttpRequests의 앞 코드는  authorizeHttpRequests에서 허용되는 사이트말고 다 막아줌
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //session을 사용하지 않겠다 : 로그인처리를 session으로 하지않기 때문에
                //session의 단점 : 서버측 메모리에 저장을 하기때문에(로그인을 하지않아도 로그인 메모리가 사용됨) 사용자가 많이 접속이 되면 공간이 더 늘어나며, 접속이 많을 수록 메모리를 많이 차지함
                .httpBasic(http -> http.disable()) // security자체에서 제공하는 로그인 화면을 제거  : 리소스를 확보하기 위해서
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable()) //기본으로 제공하는 보안기능 끔 :
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "api/user/signin" //""메소드 상관없이 주소만 허용 : get, delete, post, put상관없이 허용하겠다.
                        , "api/user/signup"
                        , "api/user/firebase-token"
                        , "/error"
                        , "err"
                        , "/index.html"
                        , "/"
                        , "/static/**" //리엑트 static url부분을 허용 : static밑의 모든 폴더
                        , "/swagger.html" //,"/swagger.html"로 접근하면 "/swagger-ui/**"로 사이트가 바뀌기 때문에 둘다 작성해야함
                        , "/swagger-ui/**"
                        , "v3/api-docs/**"//허용을 안하면 라이브러리로 접근이 안됨 꼭 넣기
                ).permitAll() //requestMatchers permitAll : permitAll, ""사이트를 인증없이 무사 통과 시키겠다, permitAll만 허용하고 나머진 막음
                //위 코드는 로그인을 안해도 사용할 수있고 아래는 로그인을 해야한 할 수 있는 코드
                 .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //필터가 jwtAuthenticationFilter 거치고 뒤에있는 .class로 감
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){ //BCryptPasswordEncoder()있는 메소드를 사용 :  암호화, 비번 비교 등을 사용할 수 있음
        // 기술이 바껴도 암호화가 되도록 : 다른곳에서도 사용할 수 있도록.
        return new BCryptPasswordEncoder();
    }
}
