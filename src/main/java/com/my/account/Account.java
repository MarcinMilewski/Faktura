package com.my.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.order.Order;
import com.my.order.OrderComponent;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;

	private String role = "ROLE_USER";

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private List<Order> orders;

    protected Account() {

	}
	
	public Account(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
