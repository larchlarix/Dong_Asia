package com.example.DongAisa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = 759550842L;

    public static final QNews news = new QNews("news");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Long> newsCategory = createNumber("newsCategory", Long.class);

    public final StringPath newsContents = createString("newsContents");

    public final StringPath newsCountry = createString("newsCountry");

    public final StringPath newsDate = createString("newsDate");

    public final NumberPath<Long> newsId = createNumber("newsId", Long.class);

    public final StringPath newsImageURL = createString("newsImageURL");

    public final StringPath newsLink = createString("newsLink");

    public final NumberPath<Long> newsPublisher = createNumber("newsPublisher", Long.class);

    public final StringPath newsTitle = createString("newsTitle");

    public QNews(String variable) {
        super(News.class, forVariable(variable));
    }

    public QNews(Path<? extends News> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNews(PathMetadata metadata) {
        super(News.class, metadata);
    }

}

