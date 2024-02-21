package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFollowEntity is a Querydsl query type for UserFollowEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserFollowEntity extends EntityPathBase<UserFollowEntity> {

    private static final long serialVersionUID = 437106542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFollowEntity userFollowEntity = new QUserFollowEntity("userFollowEntity");

    public final QCreatedAtEntity _super = new QCreatedAtEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QUserEntity fromUserEntity;

    public final QUserEntity toUserEntity;

    public final QUserFollowIds userFollowIds;

    public QUserFollowEntity(String variable) {
        this(UserFollowEntity.class, forVariable(variable), INITS);
    }

    public QUserFollowEntity(Path<? extends UserFollowEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFollowEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFollowEntity(PathMetadata metadata, PathInits inits) {
        this(UserFollowEntity.class, metadata, inits);
    }

    public QUserFollowEntity(Class<? extends UserFollowEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fromUserEntity = inits.isInitialized("fromUserEntity") ? new QUserEntity(forProperty("fromUserEntity")) : null;
        this.toUserEntity = inits.isInitialized("toUserEntity") ? new QUserEntity(forProperty("toUserEntity")) : null;
        this.userFollowIds = inits.isInitialized("userFollowIds") ? new QUserFollowIds(forProperty("userFollowIds")) : null;
    }

}

