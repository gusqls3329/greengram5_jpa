package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_dm_user")
public class DmUserEntity {
    @EmbeddedId
    private DmUserIds dmUserIds;

    @ManyToOne // 폴인키걸려고
    @MapsId("iuser") //멤버필드명 (컬럼명X)
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @ManyToOne // 폴인키걸려고
    @MapsId("idm") //멤버필드명 (컬럼명X)
    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED") //name = "idm"을 작성하지 않으면  아래멤버필드인  dmEntity로 네임이 작성된다.
    private DmEntity dmEntity;

}
