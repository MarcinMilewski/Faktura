package com.my.account;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceFacadeTest {

	@InjectMocks
	private UserServiceFacade userServiceFacade = new UserServiceFacade();

	@Mock
	private AccountRepository accountRepositoryMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldInitializeWithThreeDemoUsers() {
		// act
		userServiceFacade.initialize();
		// assert
		verify(accountRepositoryMock, times(3)).create(any(Account.class));
	}

	@Test
	public void shouldThrowExceptionWhenUserNotFound() {
		// arrange
		thrown.expect(UsernameNotFoundException.class);
		thrown.expectMessage("customer not found");

		when(accountRepositoryMock.findByEmail("customer@example.com")).thenReturn(null);
		// act
		userServiceFacade.loadUserByUsername("customer@example.com");
	}

	@Test
	public void shouldReturnUserDetails() {
		// arrange
		Account demoUser = new Account("customer@example.com", "demo", "ROLE_USER");
		when(accountRepositoryMock.findByEmail("customer@example.com")).thenReturn(demoUser);

		// act
		UserDetails userDetails = userServiceFacade.loadUserByUsername("customer@example.com");

		// assert
		assertThat(demoUser.getEmail()).isEqualTo(userDetails.getUsername());
		assertThat(demoUser.getPassword()).isEqualTo(userDetails.getPassword());
        assertThat(hasAuthority(userDetails, demoUser.getRole()));
	}

	private boolean hasAuthority(UserDetails userDetails, String role) {
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		for(GrantedAuthority authority : authorities) {
			if(authority.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
}
