package com.my.repository;

import com.my.item.Item;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Marcin on 10.01.2016.
 */
@NoRepositoryBean
public interface ItemRepository extends AbstractRepository<Item> {
}
