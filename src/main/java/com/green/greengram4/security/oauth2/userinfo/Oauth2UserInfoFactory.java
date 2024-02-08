package com.green.greengram4.security.oauth2.userinfo;

import com.green.greengram4.security.oauth2.SocialProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Oauth2UserInfoFactory {
    public Oauth2UserInfo getOauth2UserInfo(SocialProviderType socialProviderType,
                                                                                Map<String, Object> attrs){

        return switch(socialProviderType){
            case KAKAO -> new KakaoOAuth2UserInfo(attrs);
            case NAVER -> new NaverOAuth2UserInfo(attrs);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };

    }

}
