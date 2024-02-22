package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedEntity is a Querydsl query type for FeedEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedEntity extends EntityPathBase<FeedEntity> {

    private static final long serialVersionUID = 1243171696L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedEntity feedEntity = new QFeedEntity("feedEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<FeedPicsEntity, QFeedPicsEntity> feedPicsEntityList = this.<FeedPicsEntity, QFeedPicsEntity>createList("feedPicsEntityList", FeedPicsEntity.class, QFeedPicsEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> ifeed = createNumber("ifeed", Long.class);

    public final StringPath location = createString("location");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUserEntity userEntity;

    public QFeedEntity(String variable) {
        this(FeedEntity.class, forVariable(variable), INITS);
    }

    public QFeedEntity(Path<? extends FeedEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedEntity(PathMetadata metadata, PathInits inits) {
        this(FeedEntity.class, metadata, inits);
    }

    public QFeedEntity(Class<? extends FeedEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

