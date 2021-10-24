package com.br.data.controller;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.data.aop.LogExecutionTime;
import com.br.data.model.DataRequest;
import com.br.data.response.DataResponse;
import com.br.data.service.DataServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Setter;

@RestController
@AllArgsConstructor
public class DataController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Setter
	private DataServiceImpl dataServiceImpl;
	
	@PostMapping(path="/data", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> postData(@RequestBody DataRequest request) {
		
		logger.info("10001 - Calling POST /data");
		DataResponse response = new DataResponse("RR");		
		logger.info("10002 - End of POST /data");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping(path="/data/{bankId}/{branchId}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public String getData(@PathVariable String bankId, @PathVariable String branchId) {
		
		//DataResponse response = dataServiceImpl.getData(bankId, branchId);
		DataResponse response = dataServiceImpl.getDataJax(bankId, branchId);
		
		return response.getUf();
	}

	@LogExecutionTime
	@GetMapping(path = "healthcheck", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, String> healthCheck() { 
		logger.info("10003 - Calling /healthcheck");
		return Collections.singletonMap("status", "UP"); 
	}
}
