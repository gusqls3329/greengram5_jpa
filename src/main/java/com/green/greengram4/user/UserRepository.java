package com.green.greengram4.user;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {//<관련 테이블 entity, pk타입>
    Optional<UserEntity> findByProviderTypeAndUid(ProviderTypeEnum providerType, String uid);
    //findByProviderTypeAndUid의  by뒤와 and앞의 ProviderType과 ProviderTypeEnum의 ProviderType, 필드명까지 꼭같아야함

}
