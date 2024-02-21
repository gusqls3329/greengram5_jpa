package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeed;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @ToString.Exclude
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST)
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany
    private List<FeedFavEntity> feedFavList = new ArrayList<>();
}
