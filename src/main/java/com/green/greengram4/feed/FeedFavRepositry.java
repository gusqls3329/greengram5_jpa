package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedFavIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedFavRepositry extends JpaRepository<FeedFavEntity, FeedFavIds> {
}
