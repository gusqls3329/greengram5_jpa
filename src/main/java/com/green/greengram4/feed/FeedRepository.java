package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

    //By뒤가 where절로 피드안에있는 멤버필드명과 같으면 됨(컬럼명X)을 작성해야하만 OrderBy : 정렬
public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    @EntityGraph(attributePaths = {"userEntity"})
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);
}
