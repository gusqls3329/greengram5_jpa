package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserFollowIds is a Querydsl query type for UserFollowIds
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserFollowIds extends BeanPath<UserFollowIds> {

    private static final long serialVersionUID = 281437965L;

    public static final QUserFollowIds userFollowIds = new QUserFollowIds("userFollowIds");

    public final NumberPath<Long> fromIuser = createNumber("fromIuser", Long.class);

    public final NumberPath<Long> toIuser = createNumber("toIuser", Long.class);

    public QUserFollowIds(String variable) {
        super(UserFollowIds.class, forVariable(variable));
    }

    public QUserFollowIds(Path<? extends UserFollowIds> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserFollowIds(PathMetadata metadata) {
        super(UserFollowIds.class, metadata);
    }

}

