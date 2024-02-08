package com.green.greengram4.security.oauth2.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

//소셜로그인 통신후 데이터를 받을 수 있는 구조를 만드는 클래스
@AllArgsConstructor
@Getter
public abstract class Oauth2UserInfo {
    protected Map<String, Object> attributes;

    public  abstract String getId();
    public  abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
//상속받아서 만듦 > 넘어오는 json의 형태가 다르기때문에 통을시키기 위함
