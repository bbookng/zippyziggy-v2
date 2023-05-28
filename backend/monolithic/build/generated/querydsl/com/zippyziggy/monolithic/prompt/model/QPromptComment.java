package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromptComment is a Querydsl query type for PromptComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromptComment extends EntityPathBase<PromptComment> {

    private static final long serialVersionUID = 78445355L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromptComment promptComment = new QPromptComment("promptComment");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final QPrompt prompt;

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updDt = createDateTime("updDt", java.time.LocalDateTime.class);

    public QPromptComment(String variable) {
        this(PromptComment.class, forVariable(variable), INITS);
    }

    public QPromptComment(Path<? extends PromptComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromptComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromptComment(PathMetadata metadata, PathInits inits) {
        this(PromptComment.class, metadata, inits);
    }

    public QPromptComment(Class<? extends PromptComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prompt = inits.isInitialized("prompt") ? new QPrompt(forProperty("prompt")) : null;
    }

}

