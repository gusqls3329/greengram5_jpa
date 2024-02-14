package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_feed_comment")
public class FeedCommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED", name = "ifeed_comment")
    private Long ifeedComment;

    @Column(length = 500, nullable = false)
    private String comment;

    //ManyToOne : 테이블간의 관계 설정 (폴인키 건다 : 당사자 = FeedCommentEntity : 상대방 =userEntity
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "iuser" , nullable = false)
    private  UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ifeed" , nullable = false)
    private  FeedEntity feedEntity;
}
