package com.my.warehouse;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by marcin on 10.01.16.
 */
@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long>{
    Warehouse findByName(String name);
}
