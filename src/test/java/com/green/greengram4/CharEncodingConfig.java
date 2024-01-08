package com.green.greengram4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class CharEncodingConfig { //통합테스트에서 사용
    // MocMvcConfig와 코드만 다를뿐 한글이 깨지지 않도록 하는것. 둘중하나를 골라서 사용하기
    @Bean
    public CharacterEncodingFilter characterEncodingFilter(){
        return new CharacterEncodingFilter("UTF-8",true);
    }
}
