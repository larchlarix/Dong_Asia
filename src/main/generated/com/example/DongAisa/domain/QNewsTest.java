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

    public final NumberPath<Long> newsCategory = createNumber("newsCategory", Long.class);

    public final StringPath newsContents = createString("newsContents");

    public final StringPath newsCountry = createString("newsCountry");

    public final StringPath newsDate = createString("newsDate");

    public final StringPath newsImageURL = createString("newsImageURL");

    public final StringPath newsLink = createString("newsLink");

    public final NumberPath<Long> newsPublisher = createNumber("newsPublisher", Long.class);

    public final NumberPath<Long> newsTestId = createNumber("newsTestId", Long.class);

    public final StringPath newsTitle = createString("newsTitle");

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

