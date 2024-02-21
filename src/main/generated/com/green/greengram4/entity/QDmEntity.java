package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDmEntity is a Querydsl query type for DmEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDmEntity extends EntityPathBase<DmEntity> {

    private static final long serialVersionUID = 1744838107L;

    public static final QDmEntity dmEntity = new QDmEntity("dmEntity");

    public final QCreatedAtEntity _super = new QCreatedAtEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> idm = createNumber("idm", Long.class);

    public final StringPath lastMsg = createString("lastMsg");

    public final DateTimePath<java.time.LocalDateTime> lastMsgAt = createDateTime("lastMsgAt", java.time.LocalDateTime.class);

    public QDmEntity(String variable) {
        super(DmEntity.class, forVariable(variable));
    }

    public QDmEntity(Path<? extends DmEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDmEntity(PathMetadata metadata) {
        super(DmEntity.class, metadata);
    }

}

