package com.my.warehouse;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by marcin on 10.01.16.
 */
@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long>{
    Warehouse findByName(String name);


//    @Query("select w from Warehouse w where :id in (w.items.id)")
//    Set<Ware> findByItemsId(@Param("ids")Set<Long> ids);
}
