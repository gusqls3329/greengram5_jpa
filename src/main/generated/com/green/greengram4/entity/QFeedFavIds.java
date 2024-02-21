package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFeedFavIds is a Querydsl query type for FeedFavIds
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFeedFavIds extends BeanPath<FeedFavIds> {

    private static final long serialVersionUID = 1259823402L;

    public static final QFeedFavIds feedFavIds = new QFeedFavIds("feedFavIds");

    public final NumberPath<Long> ifeed = createNumber("ifeed", Long.class);

    public final NumberPath<Long> iuser = createNumber("iuser", Long.class);

    public QFeedFavIds(String variable) {
        super(FeedFavIds.class, forVariable(variable));
    }

    public QFeedFavIds(Path<? extends FeedFavIds> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFeedFavIds(PathMetadata metadata) {
        super(FeedFavIds.class, metadata);
    }

}

