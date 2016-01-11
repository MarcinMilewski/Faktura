package com.my.account;

import com.my.logger.Log;
import com.my.warehouse.Warehouse;
import com.my.warehouse.WarehouseRepository;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserServiceFacade implements UserDetailsService {

	@Log
	Logger logger;

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private WarehouseRepository warehouseRepository;
	@Autowired
	private WarehouseOperativeRepository warehouseOperativeRepository;
	
	@PostConstruct	
	protected void initialize() {
		Account account = new Account("operator", "operator", "ROLE_OPERATIVE");
		Account regular = new Account("regular","regular", "ROLE_USER");
		regular.setRegular(true);
		accountRepository.create(new Account("customer", "demo", "ROLE_USER"));
		accountRepository.create(new Account("admin", "admin", "ROLE_ADMIN"));
		accountRepository.create(regular);
		accountRepository.create(account);
		account = accountRepository.findByEmail("operator");

		Warehouse warehouse = new Warehouse();
		warehouse.setName("magazyn1");
		warehouseRepository.save(warehouse);
		warehouse = warehouseRepository.findByName("magazyn1");

		WarehouseOperative warehouseOperative = new WarehouseOperative();
		warehouseOperative.setFirstName("Pan");
		warehouseOperative.setLastName("Magazynier");
		warehouseOperative.setAccount(account);
		warehouseOperative.setWarehouse(warehouse);
		warehouseOperativeRepository.save(warehouseOperative);

	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("customer not found");
		}
		return createUser(account);
	}
	
	public void signIn(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));		
	}
	
	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

	public Account getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = null;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			userDetails = (UserDetails) auth.getPrincipal();
			if (userDetails.getUsername() != null) {
				Account logged = accountRepository.findByEmail(userDetails.getUsername());
				return logged;
			}
		}
		return null;
	}
}
