package com.example.stock_exchange.controller;

import com.example.stock_exchange.model.Exchange;
import com.example.stock_exchange.repository.ExchangeRepository;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exchanges")
public class ExchangeController {
	
	@Autowired
	ExchangeRepository exchangeRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Exchange>> getAllExchanges() {
		try {
			List<Exchange> exchanges = new ArrayList<Exchange>();
			exchangeRepository.findAll().forEach(exchanges::add);
			if (exchanges.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(exchanges, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<Exchange> getExchangeById(@PathVariable("name") String name) {
		Optional<Exchange> exchangeData = exchangeRepository.findByName(name);

		if (exchangeData.isPresent()) {
			return new ResponseEntity<>(exchangeData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Exchange> createExchange(@RequestBody Exchange exchange) {
		try {
			Exchange _exchange = exchangeRepository.save(new Exchange(exchange.getName(), exchange.getSymbol(), exchange.getCountry()));
			return new ResponseEntity<>(_exchange, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{name}")
	public ResponseEntity<Exchange> updateExchange(@PathVariable("name") String name, @RequestBody Exchange exchange) {
		Optional<Exchange> exchangeData = exchangeRepository.findByName(name);

		if (exchangeData.isPresent()) {
			Exchange _exchange = exchangeData.get();
//			_exchange.setName(exchange.getName());
			_exchange.setSymbol(exchange.getSymbol());
			_exchange.setCountry(exchange.getCountry());
			return new ResponseEntity<>(exchangeRepository.save(_exchange), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{name}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("name") String name) {
		try {
			exchangeRepository.deleteByName(name);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}