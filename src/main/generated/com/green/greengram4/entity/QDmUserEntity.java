package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDmUserEntity is a Querydsl query type for DmUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDmUserEntity extends EntityPathBase<DmUserEntity> {

    private static final long serialVersionUID = -1410140794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDmUserEntity dmUserEntity = new QDmUserEntity("dmUserEntity");

    public final QDmEntity dmEntity;

    public final QDmUserIds dmUserIds;

    public final QUserEntity userEntity;

    public QDmUserEntity(String variable) {
        this(DmUserEntity.class, forVariable(variable), INITS);
    }

    public QDmUserEntity(Path<? extends DmUserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDmUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDmUserEntity(PathMetadata metadata, PathInits inits) {
        this(DmUserEntity.class, metadata, inits);
    }

    public QDmUserEntity(Class<? extends DmUserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dmEntity = inits.isInitialized("dmEntity") ? new QDmEntity(forProperty("dmEntity")) : null;
        this.dmUserIds = inits.isInitialized("dmUserIds") ? new QDmUserIds(forProperty("dmUserIds")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

