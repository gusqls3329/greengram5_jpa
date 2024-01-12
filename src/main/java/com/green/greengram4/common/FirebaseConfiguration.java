package com.green.greengram4.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfiguration {
/*
a사람 : ㄱ,ㄴ,ㄷ
b사람 : 1,2,3
c사람 : a,b,c
내가 1,2,3인사람 응답해 하면 b가 응답하는 방식
 */
    @Value("${fcm.certification}")
    private String googleApplicationCredentials;

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount =
                    new ClassPathResource(googleApplicationCredentials).getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                log.info("FirebaseApp Initialization Complete !!!");
                FirebaseApp.initializeApp(options);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}