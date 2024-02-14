package com.green.greengram4.security;

import com.green.greengram4.security.oauth2.CustomeOAuth2UserService;
import com.green.greengram4.security.oauth2.OAth2AuthenticationSuccessHandler;
import com.green.greengram4.security.oauth2.OAuth2AuthenticationRequestBasedOnCookieRepository;
import com.green.greengram4.security.oauth2.Oauth2AuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationRequestBasedOnCookieRepository oAuth2AuthenticationRequestBasedOnCookieRepository;
    private final OAth2AuthenticationSuccessHandler oAth2AuthenticationSuccessHandler;
    private final CustomeOAuth2UserService customeOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //authorizeHttpRequests의 앞 코드는  authorizeHttpRequests에서 허용되는 사이트말고 다 막아줌
        /*return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //session을 사용하지 않겠다 : 로그인처리를 session으로 하지않기 때문에
                //session의 단점 : 서버측 메모리에 저장을 하기때문에(로그인을 하지않아도 로그인 메모리가 사용됨) 사용자가 많이 접속이 되면 공간이 더 늘어나며, 접속이 많을 수록 메모리를 많이 차지함
                .httpBasic(http -> http.disable()) // security자체에서 제공하는 로그인 화면을 제거  : 리소스를 확보하기 위해서
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable()) //기본으로 제공하는 보안기능 끔 :
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                        "/api/feed"
                                            , "/api/feed/comment"
                                        , "api/dm"
                                       , "api/dm/msg"
                                ).permitAll() //requestMatchers permitAll : permitAll, ""사이트를 인증없이 무사 통과 시키겠다, permitAll만 허용하고 나머진 막음
                                //위 코드는 로그인을 안해도 사용할 수있고 아래는 로그인을 해야한 할 수 있는 코드
                                .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //필터가 jwtAuthenticationFilter 거치고 뒤에있는 .class로 감
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .build();*/
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable()) //  HTTP 기본 인증을 사용하지 않도록 비활성화
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable()) // 스프링에서 기본적으로 제공해주는 보안기능, 여기까지는 막는기능
                // Cross-Site Request Forgery(CSRF) 공격을 방지하기 위해 CSRF 보호를 비활성화
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                        "/api/feed",
                                        "/api/feed/comment",
                                        "/api/dm",
                                        "api/dm/msg"
                                ).authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/user/signout",
                                        "/api/user/follow").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/user").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/user/pic").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/feed/fav").hasAnyRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 필터 앞에 jwtAuthenticationFilter 끼워넣고 UsernamePasswordAuthenticationFilter 실행
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .oauth2Login(oauth2 -> oauth2.authorizationEndpoint(auth -> auth.baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(oAuth2AuthenticationRequestBasedOnCookieRepository))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/*/oauth2/code/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customeOAuth2UserService))
                        .successHandler(oAth2AuthenticationSuccessHandler)
                        .failureHandler(oauth2AuthenticationFailureHandler)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //BCryptPasswordEncoder()있는 메소드를 사용 :  암호화, 비번 비교 등을 사용할 수 있음
        // 기술이 바껴도 암호화가 되도록 : 다른곳에서도 사용할 수 있도록.
        return new BCryptPasswordEncoder();
    }
}
