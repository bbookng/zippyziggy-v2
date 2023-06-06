package com.zippyziggy.monolithic.notice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlarmEntity is a Querydsl query type for AlarmEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmEntity extends EntityPathBase<AlarmEntity> {

    private static final long serialVersionUID = -2025675168L;

    public static final QAlarmEntity alarmEntity = new QAlarmEntity("alarmEntity");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    public final StringPath memberUuid = createString("memberUuid");

    public final StringPath url = createString("url");

    public QAlarmEntity(String variable) {
        super(AlarmEntity.class, forVariable(variable));
    }

    public QAlarmEntity(Path<? extends AlarmEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlarmEntity(PathMetadata metadata) {
        super(AlarmEntity.class, metadata);
    }

}

