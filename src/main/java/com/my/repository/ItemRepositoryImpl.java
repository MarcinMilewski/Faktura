package com.my.repository;

import com.my.item.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Marcin on 10.01.2016.
 */
@Repository
@Transactional(readOnly = true)
public class ItemRepositoryImpl extends AbstractRepositoryImpl<Item> implements ItemRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public ItemRepositoryImpl() {
        super(Item.class);
    }

    @Transactional
    @Override
    public void create(Item item) {
        entityManager.persist(item);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
