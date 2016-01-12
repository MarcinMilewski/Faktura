package com.my.warehouse;

import com.my.warehouse.operative.WarehouseOperative;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by marcin on 10.01.16.
 */
@Repository
@Transactional(readOnly=true)
public interface WarehouseRepository extends CrudRepository<Warehouse, Long>{
    Warehouse findByName(String name);


//    @Query("select w from Warehouse w where :id in (w.items.id)")
//    Set<Ware> findByItemsId(@Param("ids")Set<Long> ids);

    @Modifying
    @Query("update Warehouse w set w.warehouseOperatives = :warehouseOperatives where w.id = :id")
    void setOperatives(@Param("warehouseOperatives") List<WarehouseOperative> warehouseOperatives, @Param("id") Long id);

}
