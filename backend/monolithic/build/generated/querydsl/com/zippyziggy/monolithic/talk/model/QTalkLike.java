package com.zippyziggy.monolithic.talk.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTalkLike is a Querydsl query type for TalkLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTalkLike extends EntityPathBase<TalkLike> {

    private static final long serialVersionUID = 1344764299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTalkLike talkLike = new QTalkLike("talkLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final QTalk talk;

    public QTalkLike(String variable) {
        this(TalkLike.class, forVariable(variable), INITS);
    }

    public QTalkLike(Path<? extends TalkLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTalkLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTalkLike(PathMetadata metadata, PathInits inits) {
        this(TalkLike.class, metadata, inits);
    }

    public QTalkLike(Class<? extends TalkLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.talk = inits.isInitialized("talk") ? new QTalk(forProperty("talk"), inits.get("talk")) : null;
    }

}

