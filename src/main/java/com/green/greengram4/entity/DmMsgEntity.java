package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_dm_msg")//, indexes = {@Index(name = "t_dm", columnList = "idm"), @Index(name = "t_iuser", columnList = "iuser")})
public class DmMsgEntity extends CreatedAtEntity {
    @EmbeddedId
    private DmMsgIds dmMsgIds;

    @ManyToOne
    @JoinColumn(name = "iuser",nullable = false)//인너조인,
    // 일반적인 외래키는 부모의 성질을 그대로 가져오기때문에 columnDefinition = "BIGINT UNSIGNED"를 사용하지 않아도 괜찮음
    private UserEntity userEntity;

    @ManyToOne
    @MapsId("idm")
    @JoinColumn(name = "idm", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    // 복합키는 columnDefinition = "BIGINT UNSIGNED"를 적어줘야함
    private DmEntity dmEntity;

    @Column(length = 2000, nullable = false)
    private String msg;




}
