package com.br.data.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.data.response.DataResponse;
import com.br.data.service.DataServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Setter;

@RestController
@AllArgsConstructor
public class DataController {
	
	@Autowired
	@Setter
	private DataServiceImpl dataServiceImpl;
	
	@GetMapping(path="/data/{bankId}/{branchId}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public String getData(@PathVariable String bankId,
								@PathVariable String branchId) {
		
		//DataResponse response = dataServiceImpl.getData(bankId, branchId);
		DataResponse response = dataServiceImpl.getDataJax(bankId, branchId);
		
		return response.getUf();
	}

	@GetMapping(path = "healthcheck", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, String> healthCheck() { 
		return Collections.singletonMap("status", "UP"); 
	}
}
