package com.my.account;

import com.my.item.Item;
import com.my.item.repository.ItemRepository;
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
import java.math.BigDecimal;
import java.util.Collections;

public class UserServiceFacade implements UserDetailsService {

	@Log
	Logger logger;

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private WarehouseRepository warehouseRepository;
	@Autowired
	private WarehouseOperativeRepository warehouseOperativeRepository;
	@Autowired
	private ItemRepository itemRepository;

	@PostConstruct	
	protected void initialize() {
		Account account = new Account("operator", "operator", "ROLE_OPERATIVE");
		Account account2 = new Account("operator2", "operator2", "ROLE_OPERATIVE");
		Account regular = new Account("regular","regular", "ROLE_USER", "imie2", "nazwisko2","ul 1","miasto",11222);
		Account bez = new Account("bez","bez", "ROLE_OPERATIVE");
		regular.setRegular(true);
		accountRepository.create(new Account("customer", "demo", "ROLE_USER", "imie4", "nazwisko4","ul 1","miasto",11222));
		accountRepository.create(new Account("admin", "admin", "ROLE_ADMIN"));
		accountRepository.create(regular);
		accountRepository.create(account);
		accountRepository.create(account2);
		accountRepository.create(bez);
		account = accountRepository.findByEmail("operator");

		Warehouse warehouse = new Warehouse();
		warehouse.setName("magazyn1");
		warehouseRepository.save(warehouse);

		Warehouse warehouse2 = new Warehouse();
		warehouse2.setName("magazyn2");
		warehouseRepository.save(warehouse2);

		warehouse = warehouseRepository.findByName("magazyn1");

		WarehouseOperative warehouseOperative = new WarehouseOperative();
		warehouseOperative.setFirstName("Pan");
		warehouseOperative.setLastName("Magazynier");
		warehouseOperative.setAccount(account);
		warehouseOperative.setWarehouse(warehouse);
		warehouseOperativeRepository.save(warehouseOperative);

		WarehouseOperative warehouseOperative2 = new WarehouseOperative();
		warehouseOperative2.setFirstName("Pan2");
		warehouseOperative2.setLastName("Magazynier2");
		warehouseOperative2.setAccount(account2);
		warehouseOperative2.setWarehouse(warehouse2);
		warehouseOperativeRepository.save(warehouseOperative2);


		WarehouseOperative bezM = new WarehouseOperative();
		bezM.setFirstName("Nie");
		bezM.setLastName("Mam");
		bezM.setAccount(bez);
		warehouseOperativeRepository.save(bezM);

		Item item = new Item();
		item.setName("testowy");
		item.setAmount(10);
		item.setWarehouseAmount(10);
		item.setPrice(new BigDecimal(120));
		item.setWarehouse(warehouse);
		item.setVat(5);
		itemRepository.save(item);

		Item item2 = new Item();
		item2.setName("testowy2");
		item2.setAmount(30);
		item2.setWarehouseAmount(30);
		item2.setPrice(new BigDecimal(50));
		item2.setWarehouse(warehouse2);
		item2.setVat(23);
		itemRepository.save(item2);

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
