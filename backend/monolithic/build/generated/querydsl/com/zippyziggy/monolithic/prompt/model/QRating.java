package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRating is a Querydsl query type for Rating
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRating extends EntityPathBase<Rating> {

    private static final long serialVersionUID = -106408755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRating rating = new QRating("rating");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final QPrompt prompt;

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public QRating(String variable) {
        this(Rating.class, forVariable(variable), INITS);
    }

    public QRating(Path<? extends Rating> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRating(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRating(PathMetadata metadata, PathInits inits) {
        this(Rating.class, metadata, inits);
    }

    public QRating(Class<? extends Rating> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prompt = inits.isInitialized("prompt") ? new QPrompt(forProperty("prompt")) : null;
    }

}

