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
		Account regular = new Account("regular","regular", "ROLE_USER", "imie2", "nazwisko2");
		Account bez = new Account("bez","bez", "ROLE_OPERATIVE");
		regular.setRegular(true);
		accountRepository.create(new Account("customer", "demo", "ROLE_USER", "imie4", "nazwisko4"));
		accountRepository.create(new Account("admin", "admin", "ROLE_ADMIN"));
		accountRepository.create(regular);
		accountRepository.create(account);
		accountRepository.create(bez);
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
		itemRepository.save(item);


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
