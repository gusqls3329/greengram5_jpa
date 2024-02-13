package com.green.greengram4.security.oauth2;

import com.green.greengram4.common.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.green.greengram4.security.oauth2.OAuth2AuthenticationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final CookieUtils cookieUtils;
    private final OAuth2AuthenticationRequestBasedOnCookieRepository repository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("Oauth2AuthenticationFailureHandler - onAuthenticationFailure");
        String targetUrl = cookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue).orElse("/");
        exception.printStackTrace();
        targetUrl = UriComponentsBuilder.fromOriginHeader(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();
        repository.removeAuthorizationRequestCookies(response);

        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }
}
