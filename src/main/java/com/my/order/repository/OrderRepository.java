package com.my.order.repository;

import com.my.order.OrderComponent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by marcin on 09.01.16.
 */
//extends CrudRepository<OrderComponent, Long>
@Repository
public interface OrderRepository extends CrudRepository<OrderComponent,Long> {

}
