package com.example.stock_exchange.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.stock_exchange.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
	List<Stock> findByExchangeName(String exchangeName);
	List<Stock> findBySegmentId(Long segmentId);
	List<Stock> findByType(String type);
	@Query("SELECT s FROM  stocks s JOIN  com.example.stock_exchange.model.Segment sm ON s.segmentId = sm.id JOIN com.example.stock_exchange.model.Exchange e ON e.name=sm.exchangeName where e.country= :countryName")
	List<Stock> findByCountryName(String countryName);
}
