package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromptClick is a Querydsl query type for PromptClick
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromptClick extends EntityPathBase<PromptClick> {

    private static final long serialVersionUID = -782133932L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromptClick promptClick = new QPromptClick("promptClick");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final QPrompt prompt;

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public QPromptClick(String variable) {
        this(PromptClick.class, forVariable(variable), INITS);
    }

    public QPromptClick(Path<? extends PromptClick> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromptClick(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromptClick(PathMetadata metadata, PathInits inits) {
        this(PromptClick.class, metadata, inits);
    }

    public QPromptClick(Class<? extends PromptClick> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prompt = inits.isInitialized("prompt") ? new QPrompt(forProperty("prompt")) : null;
    }

}

