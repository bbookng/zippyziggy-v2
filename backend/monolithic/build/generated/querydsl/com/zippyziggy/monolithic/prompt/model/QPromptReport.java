package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromptReport is a Querydsl query type for PromptReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromptReport extends EntityPathBase<PromptReport> {

    private static final long serialVersionUID = 1946844904L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromptReport promptReport = new QPromptReport("promptReport");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final QPrompt prompt;

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public QPromptReport(String variable) {
        this(PromptReport.class, forVariable(variable), INITS);
    }

    public QPromptReport(Path<? extends PromptReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromptReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromptReport(PathMetadata metadata, PathInits inits) {
        this(PromptReport.class, metadata, inits);
    }

    public QPromptReport(Class<? extends PromptReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prompt = inits.isInitialized("prompt") ? new QPrompt(forProperty("prompt")) : null;
    }

}

