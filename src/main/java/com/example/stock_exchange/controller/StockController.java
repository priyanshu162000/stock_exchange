package com.example.stock_exchange.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock_exchange.model.Exchange;
import com.example.stock_exchange.model.Segment;
import com.example.stock_exchange.model.Stock;
import com.example.stock_exchange.repository.ExchangeRepository;
import com.example.stock_exchange.repository.SegmentRepository;
import com.example.stock_exchange.repository.StockRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stocks")
public class StockController {
	
	@Autowired
	StockRepository stockRepository;
	@Autowired
	ExchangeRepository exchangeRepository;
	@Autowired
	SegmentRepository segmentRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Stock>> getAllStocks() {
		try {
			List<Stock> stocks = new ArrayList<Stock>();
			stockRepository.findAll().forEach(stocks::add);
			if (stocks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(stocks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Stock> getStockById(@PathVariable("id") long id) {
		Optional<Stock> stockData = stockRepository.findById(id);

		if (stockData.isPresent()) {
			return new ResponseEntity<>(stockData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = {"/filter", "/search"}, method = RequestMethod.GET)
	public ResponseEntity<List<Stock>> getSerchedStocks(@RequestParam(required = false) String exchangeName, @RequestParam(required = false) Long segmentId, @RequestParam(required = false) String stockType, @RequestParam(required = false) String countryName) {
		try {
			List<Stock> stocks = new ArrayList<Stock>();
			
			if(exchangeName != null){
				stockRepository.findByExchangeName(exchangeName).forEach(stocks::add);
			} else if(segmentId != null){
				stockRepository.findBySegmentId(segmentId).forEach(stocks::add);
			} else if(stockType != null){
				stockRepository.findByType(stockType).forEach(stocks::add);
			} else if(countryName != null) {
				stockRepository.findByCountryName(countryName).forEach(stocks::add);
			}
			if (stocks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(stocks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
		Optional<Exchange> exchangeData = exchangeRepository.findByName(stock.getExchangeName());
		Optional<Segment> segmentData = segmentRepository.findById(stock.getSegmentId());
		if(exchangeData.isPresent() && segmentData.isPresent()) {
			try {
				Stock _stock = stockRepository.save(new Stock(stock.getSymbol(), stock.getIndustry(), stock.getExchangeName(), stock.getSegmentId(), stock.getType(), stock.getCurrentPrice(), stock.getExpiryDate(), stock.getLotSize()));
				return new ResponseEntity<>(_stock, HttpStatus.CREATED);
				
			} catch (Exception e) {
				System.out.println(e);
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Stock> updateStock(@PathVariable("id") long id, @RequestBody Stock stock) {
		Optional<Stock> stockData = stockRepository.findById(id);

		if (stockData.isPresent()) {
			Stock _stock = stockData.get();
			_stock.setSymbol(stock.getSymbol());
			_stock.setIndustry(stock.getIndustry());
			_stock.setExchnageName(stock.getExchangeName());
			_stock.setSegmentId(stock.getSegmentId());
			_stock.setType(stock.getType());
			_stock.setCurrentPrice(stock.getCurrentPrice());
			_stock.setExpiryDate(stock.getExpiryDate());
			_stock.setLotSize(stock.getLotSize());
			return new ResponseEntity<>(stockRepository.save(_stock), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			stockRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/expired-status/{id}")
	public ResponseEntity<?> getSegmenExpiredStatustById(@PathVariable("id") long id) {
		Optional<Stock> stockData = stockRepository.findById(id);
		if (stockData.isPresent()) {
			Date expiryDate = stockData.get().getExpiryDate();
			Date currentDate = new Date(System.currentTimeMillis());
			
			if(currentDate.compareTo(expiryDate) == 0 || currentDate.compareTo(expiryDate) > 0)
				return new ResponseEntity<>("Expired", HttpStatus.OK);
			
			return new ResponseEntity<>("Not Expired", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
}
