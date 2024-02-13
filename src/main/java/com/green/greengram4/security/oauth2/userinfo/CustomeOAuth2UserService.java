package com.green.greengram4.security.oauth2.userinfo;

import com.green.greengram4.security.oauth2.SocialProviderType;
import com.green.greengram4.user.UserMapper;
import com.green.greengram4.user.model.UserEntity;
import com.green.greengram4.user.model.UserSelDto;
import com.green.greengram4.user.model.UserSignupProcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomeOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final Oauth2UserInfoFactory factory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialProviderType socialProviderType = SocialProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        Oauth2UserInfo oauth2UserInfo = factory.getOauth2UserInfo(socialProviderType, user.getAttributes());
        UserSelDto dto = UserSelDto.builder()
                .providerType(socialProviderType.name())
                .uid(oauth2UserInfo.getId()).build();
        UserEntity savedUser = mapper.selUser(dto);

        if(savedUser == null){//한번도 로그인한적이 없다면, 회원가입 처리
            savedUser = signupUser(oauth2UserInfo, socialProviderType);
        }


        return null;
    }
    private UserEntity signupUser(Oauth2UserInfo oauth2UserInfo, SocialProviderType socialProviderType){
        UserSignupProcDto dto = new UserSignupProcDto();
        dto.setProviderType(socialProviderType.name());
        dto.setUid(oauth2UserInfo.getId());
        dto.setUpw("social");
        dto.setNm(oauth2UserInfo.getName());
        dto.setPic(oauth2UserInfo.getImageUrl());
        dto.setRole("USER");

        int result = mapper.insUser(dto);
        UserEntity entity = new UserEntity();
        entity.setIuser(dto.getIuser());
        entity.setRole(dto.getRole());

        return entity;
    }
}