package com.my.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Marcin on 10.01.2016.
 */
@Repository
public interface ItemRepository extends CrudRepository<Item,Long>{
}
