package com.green.greengram4.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

@Component
public class CookieUtils {
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }
    //쿠키담기
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");//루트 주소값으로 쿠키가 만들어진다.
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);//초값
        response.addCookie(cookie); // 응답하면 쿸키가 브라우저에서 생성되거 request일떼미디 쿠키가 담겨서 넘어옴

    }
    public void deleteCookie(HttpServletResponse response, String name){
        Cookie cookie = new Cookie(name,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    public String serialize(Object obj){
        //Base64.Encoder enc = Base64.getUrlEncoder();
        //enc.encodeToString(SerializationUtils.serialize(obj));
        return Base64.getEncoder().encodeToString(SerializationUtils.serialize(obj));
    }

    public <T> T deserialize(Cookie cookie, Class<T> cls){
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
