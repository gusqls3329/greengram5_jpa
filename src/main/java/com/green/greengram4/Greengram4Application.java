package com.green.greengram4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //  자동으로 updateAt, createAt을 하려면 @EntityListeners(AuditingEntityListener.class) 을 같이 사용해 주어야함
@ConfigurationPropertiesScan //ConfigurationProperties 가 있는곳을 Scan하겠다
@SpringBootApplication
public class Greengram4Application {

    public static void main(String[] args) {
        SpringApplication.run(Greengram4Application.class, args);
    }

}
