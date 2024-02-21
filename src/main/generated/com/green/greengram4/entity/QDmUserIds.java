package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDmUserIds is a Querydsl query type for DmUserIds
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDmUserIds extends BeanPath<DmUserIds> {

    private static final long serialVersionUID = -299917323L;

    public static final QDmUserIds dmUserIds = new QDmUserIds("dmUserIds");

    public final NumberPath<Long> idm = createNumber("idm", Long.class);

    public final NumberPath<Long> iuser = createNumber("iuser", Long.class);

    public QDmUserIds(String variable) {
        super(DmUserIds.class, forVariable(variable));
    }

    public QDmUserIds(Path<? extends DmUserIds> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDmUserIds(PathMetadata metadata) {
        super(DmUserIds.class, metadata);
    }

}

