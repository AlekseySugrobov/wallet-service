package com.leovegas.walletservice.repositories;

import com.querydsl.core.types.EntityPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QueryDslRepository<T, QT extends EntityPath<?>, ID> extends JpaRepository<T, ID>,
        QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<QT> {
    @Override
    default void customize(QuerydslBindings bindings, QT root) {
        
    }
}
