package com.zippyziggy.monolithic.talk.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTalk is a Querydsl query type for Talk
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTalk extends EntityPathBase<Talk> {

    private static final long serialVersionUID = -1323343916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTalk talk = new QTalk("talk");

    public final NumberPath<Long> hit = createNumber("hit", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> likeCnt = createNumber("likeCnt", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public final com.zippyziggy.monolithic.prompt.model.QPrompt prompt;

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QTalk(String variable) {
        this(Talk.class, forVariable(variable), INITS);
    }

    public QTalk(Path<? extends Talk> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTalk(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTalk(PathMetadata metadata, PathInits inits) {
        this(Talk.class, metadata, inits);
    }

    public QTalk(Class<? extends Talk> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prompt = inits.isInitialized("prompt") ? new com.zippyziggy.monolithic.prompt.model.QPrompt(forProperty("prompt")) : null;
    }

}

