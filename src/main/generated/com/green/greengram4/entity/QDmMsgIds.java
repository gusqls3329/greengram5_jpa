package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDmMsgIds is a Querydsl query type for DmMsgIds
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDmMsgIds extends BeanPath<DmMsgIds> {

    private static final long serialVersionUID = 1978070383L;

    public static final QDmMsgIds dmMsgIds = new QDmMsgIds("dmMsgIds");

    public final NumberPath<Long> idm = createNumber("idm", Long.class);

    public final NumberPath<Long> sep = createNumber("sep", Long.class);

    public QDmMsgIds(String variable) {
        super(DmMsgIds.class, forVariable(variable));
    }

    public QDmMsgIds(Path<? extends DmMsgIds> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDmMsgIds(PathMetadata metadata) {
        super(DmMsgIds.class, metadata);
    }

}

