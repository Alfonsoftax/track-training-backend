package com.track.training.app.interfaces;

import jakarta.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.Querydsl;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;

public class QuerydslHibernate6 extends Querydsl {

    private final EntityManager entityManager;

    public QuerydslHibernate6(EntityManager entityManager, PathBuilder<?> builder) {
        super(entityManager, builder);
        this.entityManager = entityManager;
    }

    @Override
    public <T> AbstractJPAQuery<T, JPAQuery<T>> createQuery() {
        // https://github.com/querydsl/querydsl/issues/3428
        return new JPAQuery<>(this.entityManager, JPQLTemplates.DEFAULT);
    }

}
