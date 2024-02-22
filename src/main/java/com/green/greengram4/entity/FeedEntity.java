package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인클리먼트
    private Long ifeed;

    @ManyToOne(fetch = FetchType.LAZY)
    //디폴트 (fetch = FetchType.EAGER) : 즉시로딩
    //(fetch = FetchType.LAZY) : 지연로딩
    @JoinColumn(name = "iuser",nullable = false)
    private UserEntity userEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @ToString.Exclude//string으로 변환시 얘는 제외하고 변환  //양방향이라 무한루프 돌아서 넣음
    @OneToMany(mappedBy = "feedEntity",cascade = CascadeType.PERSIST)  //양방향 설정으로 영속성 전이(유지) picsRepository안만들어도됨
    // —> 영속성 전이 이게 빠져있으면 insert할 때 피드, 사진 두번 insert를 해야 하는데 내가 직접날리지않고 알아서 매핑하여 같이 보내줌
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList();

    //FeedEntity (부모 엔티티): cascade = CascadeType.PERSIST가 설정되어 있으므로 FeedEntity가 영속 상태가 되면, 그와 연관된
    // FeedPicsEntity 역시 함께 영속 상태가 됩니다. 다시 말해, FeedEntity를 저장할 때 관련된 FeedPicsEntity도 함께 저장됩니다.

    //FeedPicsEntity (자식 엔티티): FeedEntity에 속하는 여러 개의 FeedPicsEntity가 있을 수 있습니다. 이들은 FeedEntity의
    // feedPicsEntityList 필드를 통해 연결되어 있습니다. 이 경우 @OneToMany(mappedBy = "feedEntity", cascade =
    // CascadeType.PERSIST)가 설정되어 있으므로, FeedEntity가 저장될 때 해당 FeedEntity에 속한 FeedPicsEntity도 함께 저장됩니다.

    /*@ToString.Exclude
    @OneToMany(mappedBy = "feedEntity")
    private List<FeedFavEntity> feedFavList = new ArrayList();*/
}