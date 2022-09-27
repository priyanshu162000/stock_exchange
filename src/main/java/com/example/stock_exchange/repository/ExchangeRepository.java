package com.example.stock_exchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock_exchange.model.Exchange;

public interface ExchangeRepository extends JpaRepository<Exchange, Long>{
	
	Optional<Exchange> findByName(String name);
	
	@Transactional
	void deleteByName(String name);
	
}
