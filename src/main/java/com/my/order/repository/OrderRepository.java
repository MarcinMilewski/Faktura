package com.my.order.repository;

import com.my.order.OrderComponent;
import com.my.order.OrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by marcin on 09.01.16.
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderComponent,Long> {
    @Query("select o from OrderSummary o where o.customer.id = :id ")
    public List<OrderComponent>  findByCustomerId(@Param("id") Long id);
}
