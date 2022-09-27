package com.example.stock_exchange.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "exchanges")
public class Exchange extends AuditModel {
	
    @Id
    @NotNull
    @Column(name = "name")
    private String name;
    
    @NotNull
    @Column(name = "symbol")
    private String symbol;
    
    @NotNull
    @Column(name = "country")
    private String country;
    
    public Exchange() {

	}
    
    public Exchange(String name, String symbol, String country) {
		this.name = name;
		this.symbol = symbol;
		this.country = country;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
