package com.my.warehouse.operative;

import com.my.order.OrderItem;
import com.my.warehouse.Warehouse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by marcin on 10.01.16.
 */
@Repository
@Transactional(readOnly=true)
public interface WarehouseOperativeRepository extends CrudRepository<WarehouseOperative, Long> {
    WarehouseOperative findByFirstName(String firstName);

    @Modifying
    @Query("update WarehouseOperative w set w.firstName = :firstName where w.id = :id")
    void setName(@Param("firstName") String firstName, @Param("id") Long id);

    //@Query("select WarehouseOperative w inner join w.orderItems o where o.id in :ids")
    //Set<WarehouseOperative> findByOrderComponentsIds(@Param("ids") Set<Long> orderChildrenIds);
    Iterable<WarehouseOperative> findByOrderItems(Set<OrderItem> orderItems);
}
