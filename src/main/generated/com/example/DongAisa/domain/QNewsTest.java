package com.example.DongAisa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewsTest is a Querydsl query type for NewsTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewsTest extends EntityPathBase<NewsTest> {

    private static final long serialVersionUID = -1492959444L;

    public static final QNewsTest newsTest = new QNewsTest("newsTest");

    public final StringPath newsTestBody = createString("newsTestBody");

    public final StringPath newsTestDate = createString("newsTestDate");

    public final NumberPath<Long> newsTestId = createNumber("newsTestId", Long.class);

    public final StringPath newsTestTitle = createString("newsTestTitle");

    public QNewsTest(String variable) {
        super(NewsTest.class, forVariable(variable));
    }

    public QNewsTest(Path<? extends NewsTest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewsTest(PathMetadata metadata) {
        super(NewsTest.class, metadata);
    }

}

