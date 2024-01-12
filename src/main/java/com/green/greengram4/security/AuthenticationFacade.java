package com.green.greengram4.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
//로그인하면 토큰에 정보를 담아 암호화 하였는데 그 토큰을 프론트에 보내는 데 그걸 복호화해서 정보를 다시 꺼내서 AuthenticationFacade에 정보를 넣음
public class AuthenticationFacade { //서비스나 컨트롤러 중 하나만 사용해야함.> 서비스를 추천
    public MyUserDetails getLoginUser(){
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //return (MyUserDetails) auth.getPrincipal();
        return (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public int getLoginUserPk(){
        MyUserDetails myUserDetails = getLoginUser();
        return myUserDetails == null ? 0 :  myUserDetails.getMyPrincipal().getIuser();
    }
}
