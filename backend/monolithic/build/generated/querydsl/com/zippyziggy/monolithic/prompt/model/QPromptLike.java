package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromptLike is a Querydsl query type for PromptLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromptLike extends EntityPathBase<PromptLike> {

    private static final long serialVersionUID = 1360508491L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromptLike promptLike = new QPromptLike("promptLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final QPrompt prompt;

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public QPromptLike(String variable) {
        this(PromptLike.class, forVariable(variable), INITS);
    }

    public QPromptLike(Path<? extends PromptLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromptLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromptLike(PathMetadata metadata, PathInits inits) {
        this(PromptLike.class, metadata, inits);
    }

    public QPromptLike(Class<? extends PromptLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prompt = inits.isInitialized("prompt") ? new QPrompt(forProperty("prompt")) : null;
    }

}

