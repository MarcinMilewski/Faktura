package com.my.account;

import com.my.repository.AbstractRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by marcin on 06.01.16.
 */
@NoRepositoryBean
public interface AccountRepository extends AbstractRepository<Account> {
    Account findByEmail(String email);
}
