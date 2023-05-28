package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromptTemp is a Querydsl query type for PromptTemp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromptTemp extends EntityPathBase<PromptTemp> {

    private static final long serialVersionUID = 1360743048L;

    public static final QPromptTemp promptTemp = new QPromptTemp("promptTemp");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final StringPath description = createString("description");

    public final StringPath example = createString("example");

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final NumberPath<Long> originPromptUuId = createNumber("originPromptUuId", Long.class);

    public final StringPath prefix = createString("prefix");

    public final StringPath promptUuid = createString("promptUuid");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath suffix = createString("suffix");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updDt = createDateTime("updDt", java.time.LocalDateTime.class);

    public QPromptTemp(String variable) {
        super(PromptTemp.class, forVariable(variable));
    }

    public QPromptTemp(Path<? extends PromptTemp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromptTemp(PathMetadata metadata) {
        super(PromptTemp.class, metadata);
    }

}

