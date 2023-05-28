package com.zippyziggy.monolithic.member.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVisitedMemberCount is a Querydsl query type for VisitedMemberCount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVisitedMemberCount extends EntityPathBase<VisitedMemberCount> {

    private static final long serialVersionUID = 660598725L;

    public static final QVisitedMemberCount visitedMemberCount = new QVisitedMemberCount("visitedMemberCount");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nowDate = createString("nowDate");

    public final NumberPath<Long> visitedCount = createNumber("visitedCount", Long.class);

    public QVisitedMemberCount(String variable) {
        super(VisitedMemberCount.class, forVariable(variable));
    }

    public QVisitedMemberCount(Path<? extends VisitedMemberCount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVisitedMemberCount(PathMetadata metadata) {
        super(VisitedMemberCount.class, metadata);
    }

}

