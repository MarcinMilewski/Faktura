package com.my.account;

import com.my.repository.AbstractRepositoryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Repository
@Transactional(readOnly = true)
public class AccountRepositoryImpl extends AbstractRepositoryImpl<Account> implements AccountRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private PasswordEncoder passwordEncoder;

	public AccountRepositoryImpl() {
		super(Account.class);
	}

	@Transactional
	@Override
	public void create(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		entityManager.persist(account);
	}

	@Override
	public Account findByEmail(String email) {
		try {
			return entityManager.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}



	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
