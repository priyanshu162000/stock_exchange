package com.example.stock_exchange.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name="stocks")
@Table(name = "stocks")


public class Stock extends AuditModel {

	private static final long serialVersionUID = 1L;
	
	private enum TYPES{
		Equality, Option, Future, Index
	}

	@Id  
   	@GeneratedValue(strategy=GenerationType.AUTO)  
   	private long id;  
	
    @NotNull
    private String symbol;
    
    @NotNull
    private String industry;
    
    @NotNull
    private String exchangeName;
    
    @NotNull
    private long segmentId;
    
    @NotNull
    private TYPES type;
    
    @NotNull
    private Float currentPrice;
    
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expiryDate;
    
    @NotNull
    private int lotSize;
    
    public Stock() {
    	
    }
    
    public Stock(String symbol, String industry, String exchangeName, long segmentId,TYPES type,Float currentPrice,Date expiryDate, int lotSize) {
		this.symbol = symbol;
		this.industry = industry;
		this.exchangeName = exchangeName;
		this.segmentId = segmentId;
		this.type = type;
		this.currentPrice = currentPrice;
		this.expiryDate = expiryDate;
		this.lotSize = lotSize;
	}
    
    public long getId() {
		return id;
	}
    
 	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public long getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(long segmentName) {
		this.segmentId = segmentName;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchnageName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public TYPES getType() {
		return type;
	}

	public void setType(TYPES type) {
		this.type = type;
	}

	public Float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getLotSize() {
		return lotSize;
	}

	public void setLotSize(int lotSize) {
		this.lotSize = lotSize;
	}  
}
