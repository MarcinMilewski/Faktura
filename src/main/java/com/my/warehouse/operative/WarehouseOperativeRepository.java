package com.my.warehouse.operative;

import com.my.warehouse.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by marcin on 10.01.16.
 */
@Repository
public interface WarehouseOperativeRepository extends CrudRepository<WarehouseOperative, Long> {
    WarehouseOperative findByFirstName(String firstName);
}
