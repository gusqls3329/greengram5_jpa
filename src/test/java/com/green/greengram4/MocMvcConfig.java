package com.green.greengram4;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Test할때 한글을 사용할 수 있도록 아래 코드작성
// MocMvc : 개발한 웹 프로그램을 실제 서버에 배포하지 않고도 테스트를 위한 요청을 제공하는 수단입니다.
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureMockMvc
@Import({MocMvcConfig.MocMvc.class}) //{} : 여러개 작성가능
public @interface MocMvcConfig {
    // CharEncodingConfig와 코드만 다를뿐 한글이 깨지지 않도록 하는것. 둘중하나를 골라서 사용하기
    class MocMvc {
        @Bean
        MockMvcBuilderCustomizer utf8Config() {
            return builder -> builder.addFilter(new CharacterEncodingFilter("UTF-8", true)); //람다식
        }
    }
}
