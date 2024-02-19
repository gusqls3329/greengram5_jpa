package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user_follow")
public class UserFollowEntity extends CreatedAtEntity{

    @EmbeddedId //복합키때문에
    private UserFollowIds userFollowIds;

    @ManyToOne // 폴인키걸려고
    @MapsId("fromIuser") //복합키 멤버필드명 (컬럼명X)
    @JoinColumn(name = "from_iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity fromUserEntity;

    @ManyToOne
    @MapsId("toIuser") //멤버필드명 (컬럼명X)
    @JoinColumn(name = "to_iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity toUserEntity;

}
