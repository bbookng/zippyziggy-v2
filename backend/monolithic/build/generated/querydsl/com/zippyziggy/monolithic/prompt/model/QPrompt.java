package com.zippyziggy.monolithic.prompt.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPrompt is a Querydsl query type for Prompt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPrompt extends EntityPathBase<Prompt> {

    private static final long serialVersionUID = -148112236L;

    public static final QPrompt prompt = new QPrompt("prompt");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final StringPath description = createString("description");

    public final StringPath example = createString("example");

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Languages> languages = createEnum("languages", Languages.class);

    public final NumberPath<Long> likeCnt = createNumber("likeCnt", Long.class);

    public final ComparablePath<java.util.UUID> memberUuid = createComparable("memberUuid", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> originPromptUuid = createComparable("originPromptUuid", java.util.UUID.class);

    public final StringPath prefix = createString("prefix");

    public final ListPath<PromptComment, QPromptComment> promptComments = this.<PromptComment, QPromptComment>createList("promptComments", PromptComment.class, QPromptComment.class, PathInits.DIRECT2);

    public final ComparablePath<java.util.UUID> promptUuid = createComparable("promptUuid", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final EnumPath<StatusCode> statusCode = createEnum("statusCode", StatusCode.class);

    public final StringPath suffix = createString("suffix");

    public final ListPath<com.zippyziggy.monolithic.talk.model.Talk, com.zippyziggy.monolithic.talk.model.QTalk> talks = this.<com.zippyziggy.monolithic.talk.model.Talk, com.zippyziggy.monolithic.talk.model.QTalk>createList("talks", com.zippyziggy.monolithic.talk.model.Talk.class, com.zippyziggy.monolithic.talk.model.QTalk.class, PathInits.DIRECT2);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updDt = createDateTime("updDt", java.time.LocalDateTime.class);

    public QPrompt(String variable) {
        super(Prompt.class, forVariable(variable));
    }

    public QPrompt(Path<? extends Prompt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrompt(PathMetadata metadata) {
        super(Prompt.class, metadata);
    }

}

