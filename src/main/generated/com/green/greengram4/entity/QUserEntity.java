package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -1698939939L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath firebaseToken = createString("firebaseToken");

    public final NumberPath<Long> iuser = createNumber("iuser", Long.class);

    public final StringPath nm = createString("nm");

    public final StringPath pic = createString("pic");

    public final EnumPath<com.green.greengram4.common.ProviderTypeEnum> providerType = createEnum("providerType", com.green.greengram4.common.ProviderTypeEnum.class);

    public final EnumPath<com.green.greengram4.common.RoleEnum> role = createEnum("role", com.green.greengram4.common.RoleEnum.class);

    public final StringPath uid = createString("uid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath upw = createString("upw");

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

