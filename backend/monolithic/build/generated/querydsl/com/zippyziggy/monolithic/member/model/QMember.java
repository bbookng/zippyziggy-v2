package com.zippyziggy.monolithic.member.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1708383148L;

    public static final QMember member = new QMember("member1");

    public final BooleanPath activate = createBoolean("activate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final EnumPath<Platform> platform = createEnum("platform", Platform.class);

    public final StringPath platformId = createString("platformId");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath refreshToken = createString("refreshToken");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final EnumPath<RoleType> role = createEnum("role", RoleType.class);

    public final ComparablePath<java.util.UUID> userUuid = createComparable("userUuid", java.util.UUID.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

