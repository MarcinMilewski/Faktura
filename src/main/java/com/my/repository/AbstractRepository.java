package com.my.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by marcin on 09.11.15.
 */
@NoRepositoryBean
public interface AbstractRepository<T> {
    void create(T entity);

    void edit(T entity);

    void remove(T entity);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

    int count();
}
