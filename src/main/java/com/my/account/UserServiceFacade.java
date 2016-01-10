package com.my.account;

import com.my.logger.Log;
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
import java.util.Collections;

public class UserServiceFacade implements UserDetailsService {

	@Log
	Logger logger;

	@Autowired
	private AccountRepository accountRepository;
	
	@PostConstruct	
	protected void initialize() {
		accountRepository.create(new Account("customer", "demo", "ROLE_USER"));
		accountRepository.create(new Account("admin", "admin", "ROLE_ADMIN"));
		accountRepository.create(new Account("operator", "operator", "ROLE_OPERATIVE"));
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
