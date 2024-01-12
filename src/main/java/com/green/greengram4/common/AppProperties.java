package com.green.greengram4.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Jwt jwt = new Jwt();
    @Getter
    @Setter
    public class Jwt{ //innerclass로 외부에서 사용을 하려고 하면 사용을 할 수 있지만, 주로 그안의 class에서 사용이 된다
        private String secret;
        private String headerSchemeName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        //innerclass를 사용하려면 밖의 class에서 객체화를 해야함

        private int refreshTokenCookieMaxAge;

        public void setRefreshTokenEcpiry(long refreshTokenExpiry){
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = (int) refreshTokenExpiry /1000;
        }
    }
}
