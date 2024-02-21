package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedCommentEntity is a Querydsl query type for FeedCommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedCommentEntity extends EntityPathBase<FeedCommentEntity> {

    private static final long serialVersionUID = 2040965877L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedCommentEntity feedCommentEntity = new QFeedCommentEntity("feedCommentEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFeedEntity feedEntity;

    public final NumberPath<Long> ifeedComment = createNumber("ifeedComment", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUserEntity userEntity;

    public QFeedCommentEntity(String variable) {
        this(FeedCommentEntity.class, forVariable(variable), INITS);
    }

    public QFeedCommentEntity(Path<? extends FeedCommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedCommentEntity(PathMetadata metadata, PathInits inits) {
        this(FeedCommentEntity.class, metadata, inits);
    }

    public QFeedCommentEntity(Class<? extends FeedCommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feedEntity = inits.isInitialized("feedEntity") ? new QFeedEntity(forProperty("feedEntity"), inits.get("feedEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

