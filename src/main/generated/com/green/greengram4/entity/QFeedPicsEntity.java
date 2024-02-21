package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedPicsEntity is a Querydsl query type for FeedPicsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedPicsEntity extends EntityPathBase<FeedPicsEntity> {

    private static final long serialVersionUID = 820618873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedPicsEntity feedPicsEntity = new QFeedPicsEntity("feedPicsEntity");

    public final QCreatedAtEntity _super = new QCreatedAtEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFeedEntity feedEntity;

    public final NumberPath<Long> ifeedPics = createNumber("ifeedPics", Long.class);

    public final StringPath pic = createString("pic");

    public QFeedPicsEntity(String variable) {
        this(FeedPicsEntity.class, forVariable(variable), INITS);
    }

    public QFeedPicsEntity(Path<? extends FeedPicsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedPicsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedPicsEntity(PathMetadata metadata, PathInits inits) {
        this(FeedPicsEntity.class, metadata, inits);
    }

    public QFeedPicsEntity(Class<? extends FeedPicsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feedEntity = inits.isInitialized("feedEntity") ? new QFeedEntity(forProperty("feedEntity"), inits.get("feedEntity")) : null;
    }

}

