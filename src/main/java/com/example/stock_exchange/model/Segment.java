package com.example.stock_exchange.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.time.LocalTime;

@Entity
@Table(name = "segments")
public class Segment extends AuditModel {

	private static final long serialVersionUID = 1L;
	
	
		@Id  
	   	@GeneratedValue(strategy=GenerationType.AUTO)  
	   	private long id;  
		
		@NotNull
		private String exchangeName;
		
	    @NotNull
	    private LocalTime startTime;
	    
	    @NotNull
	    private LocalTime endTime;
	    
	    @NotNull
	    private String workingDays;
	    
	    public Segment() {

		}
	    
	    public Segment(LocalTime startTime, LocalTime endTime, String workingDays, String exchangeName) {
			this.startTime =  startTime;
			this.endTime = endTime;
			this.workingDays = workingDays;
			this.exchangeName = exchangeName;
		}
	    
	    public long getId() {
			return id;
		}
	    
	    public String getExchangeName() {
			return exchangeName;
		}

		public void setExchangeName(String exchangeName) {
			this.exchangeName = exchangeName;
		}

		public LocalTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalTime startTime) {
			this.startTime = startTime;
		}

		public LocalTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalTime endTime) {
			this.endTime = endTime;
		}

		public String getWorkingDays() {
			return workingDays;
		}

		public void setWorkingDays(String workingDays) {
			this.workingDays = workingDays;
		}
		
}
