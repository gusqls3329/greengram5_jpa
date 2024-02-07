package com.green.greengram4.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {
    private final Apartment apartment = new Apartment();

    @Getter
    @Setter
    public class Apartment{
        private String baseUrl;//baseUrl : base-url -는 뒤를 자동으로 대문자로 만들어줌
        private String dataUrl;
        private String serviceKey;

    }
}
