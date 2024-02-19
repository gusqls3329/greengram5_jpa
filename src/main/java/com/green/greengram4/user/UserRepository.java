package com.green.greengram4.user;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {//<관련 테이블 entity, pk타입>
    Optional<UserEntity> findByProviderTypeAndUid(ProviderTypeEnum providerType, String uid);
    //피드안에있는 멤버필드명과 같으면 됨
}
