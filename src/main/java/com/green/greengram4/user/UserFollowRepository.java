package com.green.greengram4.user;

import com.green.greengram4.entity.UserFollowEntity;
import com.green.greengram4.entity.UserFollowIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollowEntity, UserFollowIds> {
}
