package com.example.stock_exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stock_exchange.model.Segment;

public interface SegmentRepository extends JpaRepository<Segment, Long>{
	
}
