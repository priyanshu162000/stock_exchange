package com.example.stock_exchange.controller;


import com.example.stock_exchange.model.Segment;
import com.example.stock_exchange.repository.SegmentRepository;
import com.example.stock_exchange.model.Exchange;
import com.example.stock_exchange.repository.ExchangeRepository;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
@RequestMapping("/segments")
public class SegmentController {
	
	private enum weekDays{
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
	}

	
	@Autowired
	SegmentRepository segmentRepository;
	@Autowired
	ExchangeRepository exchangeRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Segment>> getAllSegments() {
		try {
			List<Segment> segments = new ArrayList<Segment>();
			segmentRepository.findAll().forEach(segments::add);
			if (segments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(segments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Segment> getSegmentById(@PathVariable("id") long id) {
		Optional<Segment> segmentData = segmentRepository.findById(id);

		if (segmentData.isPresent()) {
			return new ResponseEntity<>(segmentData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Segment> createSegment(@RequestBody Segment segment) {
		Optional<Exchange> exchangeData = exchangeRepository.findByName(segment.getExchangeName());
		if(exchangeData.isPresent()) {
			try {
				Segment _segment = segmentRepository.save(new Segment(segment.getStartTime(), segment.getEndTime(), segment.getWorkingDays(), segment.getExchangeName()));
				return new ResponseEntity<>(_segment, HttpStatus.CREATED);
				
			} catch (Exception e) {
				System.out.println(e);
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Segment> updateSegment(@PathVariable("id") long id, @RequestBody Segment segment) {
		Optional<Segment> segmentData = segmentRepository.findById(id);

		if (segmentData.isPresent()) {
			Segment _segment = segmentData.get();
			_segment.setStartTime(segment.getStartTime());
			_segment.setEndTime(segment.getEndTime());
			_segment.setWorkingDays(segment.getWorkingDays());
			_segment.setExchangeName(segment.getExchangeName());
			return new ResponseEntity<>(segmentRepository.save(_segment), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			segmentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/active-status/{id}")
	public ResponseEntity<?> getSegmenStatustById(@PathVariable("id") long id) {
		Optional<Segment> segmentData = segmentRepository.findById(id);
		if (segmentData.isPresent()) {
			Format f = new SimpleDateFormat("EEEE");
			String str = f.format(new Date());
			LocalTime startTime = segmentData.get().getStartTime();
			LocalTime endTime = segmentData.get().getEndTime();
			LocalTime currentTime = LocalTime.now();
			String workingDays = segmentData.get().getWorkingDays();
			int i = weekDays.valueOf(str).ordinal();
			
			if((currentTime.compareTo(startTime) > 0 ) && (currentTime.compareTo(endTime) < 0 ) &&  (workingDays.charAt(i) == 'Y' || workingDays.charAt(i) == 'y')) {
				return new ResponseEntity<>("Active", HttpStatus.OK);
			}
			return new ResponseEntity<>("Not Active", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	
}