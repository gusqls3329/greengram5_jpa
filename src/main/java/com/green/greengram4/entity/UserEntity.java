package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.common.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
//속성을 바꿀 경우 테이블 삭제 후 다시 만들기

@Data
@Entity
@Table(name = "t_user", uniqueConstraints = { @UniqueConstraint(columnNames = {"uid", "provider_type"})})
//uniqueConstraints = { @UniqueConstraint(columnNames = {"uid", "provider_type"})} = 복합키>????????? :""은 컬럼명으로 주어야함
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE) 오라클에서 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySql에서 mariaDb에서 사용
    private Long iuser;

    @Column( length = 10, name = "provider_type", nullable = false )
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'LOCAL'")
    //name : 아래 네임과 데이터베이스 컬럼명이 다를 경우 사용
    //length = 10 : 길이
    //nullable = false : null허용X, nullable =ture : null허용O
    private ProviderTypeEnum providerType;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(length = 2100, name = "firebase_token")
    private String firebaseToken;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;
}
