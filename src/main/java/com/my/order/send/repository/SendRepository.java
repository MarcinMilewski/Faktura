package com.my.order.send.repository;

import com.my.order.OrderComponent;
import com.my.order.send.SendStrategy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Marcin on 15.01.2016.
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface SendRepository extends CrudRepository<SendStrategy,Long> {

}
