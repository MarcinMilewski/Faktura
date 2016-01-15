package com.my.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.order.OrderSummary;
import com.my.warehouse.operative.WarehouseOperative;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQueries({
		@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
})
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;

	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;

	@Column(name = "ZIP")
	private Integer zip;
	
	@JsonIgnore
	private String password;

	private String role = "ROLE_USER";

	private Boolean regular;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "customer")
	private List<OrderSummary> orderSummaries;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private WarehouseOperative warehouseOperative;

    protected Account() {

	}

	public Account(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		regular=false;
	}
	
	public Account(String email, String password, String role, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		regular=false;
	}

	public Account(String email, String password, String role, String firstName, String lastName, String street,
				   String city, Integer zip) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.zip = zip;
		regular=false;
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

	public List<OrderSummary> getOrderSummaries() {
		return orderSummaries;
	}

	public void setOrderSummaries(List<OrderSummary> orderSummaries) {
		this.orderSummaries = orderSummaries;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WarehouseOperative getWarehouseOperative() {
		return warehouseOperative;
	}

	public void setWarehouseOperative(WarehouseOperative warehouseOperative) {
		this.warehouseOperative = warehouseOperative;
	}

	public Boolean getRegular() {
		return regular;
	}

	public void setRegular(Boolean regular) {
		this.regular = regular;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}
}
