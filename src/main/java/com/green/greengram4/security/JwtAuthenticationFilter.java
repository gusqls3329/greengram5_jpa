package com.green.greengram4.security;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
//상속받게되면 filter가 여러번 실행될 수 있는데 그 사항을 없에고 한번만 실행 될 수 있도록 _인증은 한번만 필요하기 떄문에
public class JwtAuthenticationFilter extends OncePerRequestFilter {//Filter는 Servletd앞에서  필터를거침
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "피드 등록", description = "피드등록 처리")
    @PostMapping
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolbeToken(request);
        log.info("JwtAuthentication-Token: {}",token);
        //아직 만료기간이 지나지 않았다면
        if(token != null && jwtTokenProvider.isValidateToken(token)){
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            if(auth != null){
                SecurityContextHolder.getContext().setAuthentication(auth);//getContext():개인공간
            }
        }
        //다음필터에 request,response를 넘겨라
        filterChain.doFilter(request,response);
    }
}
