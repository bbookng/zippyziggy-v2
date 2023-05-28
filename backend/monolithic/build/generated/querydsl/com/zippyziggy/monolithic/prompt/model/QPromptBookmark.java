package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromptBookmark is a Querydsl query type for PromptBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromptBookmark extends EntityPathBase<PromptBookmark> {

    private static final long serialVersionUID = 744632810L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromptBookmark promptBookmark = new QPromptBookmark("promptBookmark");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final QPrompt prompt;

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public QPromptBookmark(String variable) {
        this(PromptBookmark.class, forVariable(variable), INITS);
    }

    public QPromptBookmark(Path<? extends PromptBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromptBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromptBookmark(PathMetadata metadata, PathInits inits) {
        this(PromptBookmark.class, metadata, inits);
    }

    public QPromptBookmark(Class<? extends PromptBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prompt = inits.isInitialized("prompt") ? new QPrompt(forProperty("prompt")) : null;
    }

}

