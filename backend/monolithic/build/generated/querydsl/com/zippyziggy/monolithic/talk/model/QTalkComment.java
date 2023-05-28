package com.zippyziggy.monolithic.talk.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTalkComment is a Querydsl query type for TalkComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTalkComment extends EntityPathBase<TalkComment> {

    private static final long serialVersionUID = -805343253L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTalkComment talkComment = new QTalkComment("talkComment");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final QTalk talk;

    public final DateTimePath<java.time.LocalDateTime> updDt = createDateTime("updDt", java.time.LocalDateTime.class);

    public QTalkComment(String variable) {
        this(TalkComment.class, forVariable(variable), INITS);
    }

    public QTalkComment(Path<? extends TalkComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTalkComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTalkComment(PathMetadata metadata, PathInits inits) {
        this(TalkComment.class, metadata, inits);
    }

    public QTalkComment(Class<? extends TalkComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.talk = inits.isInitialized("talk") ? new QTalk(forProperty("talk"), inits.get("talk")) : null;
    }

}

