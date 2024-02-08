package com.green.greengram4.security.oauth2.userinfo;
import java.util.Map;

public class NaverOAuth2UserInfo extends Oauth2UserInfo{
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {//"id": 카카오에서 넘어온 id값이 들어감
        Map<String, Object> res = (Map<String, Object>)attributes.get("response");
        return res == null ? null : (String)res.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> res = (Map<String, Object>)attributes.get("response");
        return res == null ? null : (String)res.get("name");
    }

    @Override
    public String getEmail() {
        Map<String, Object> res = (Map<String, Object>)attributes.get("response");
        return res == null ? null : (String)res.get("rmail");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> res = (Map<String, Object>)attributes.get("properties");
        return res == null ? null : (String)res.get("thumbnail_image");
    }
}
