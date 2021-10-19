package com.br.data.service;

import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.data.response.DataResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceImpl {
	
	@Autowired
	private RestTemplate restTemplate;
		
	StringBuilder agencias = new StringBuilder();
	
	public DataResponse getData(final String bankId, final String branchId) {
		
		log.info("Executing API");
		
		final HttpEntity<DataResponse> response = restTemplate.getForEntity("http://localhost:8181/data/" + bankId + "/" + branchId, DataResponse.class);
		return response.getBody();
	}	
	
	public DataResponse getDataJax(final String bankId, final String branchId) {
		
		log.info("JAX-RS Client with Jersey");
		Client client = ClientBuilder.newClient();
		
		WebTarget webTarget = client.target("http://localhost:8181/data/" + bankId + "/" + branchId);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON.toString());
		
		Response response = invocationBuilder.get();
		
		return new DataResponse(response.readEntity(String.class));		
	}

	public <Optional>String geraString() {
		Random rand = new Random();
		List<String> estados = List.of("SP","RJ","MG","AC","RS","MT");
		
		agencias.append("7069SP");
		for(int i=0; i<9000; i++) {
			
			String codigo = String.format("%04d", i);
			String uf = estados.get(rand.nextInt(estados.size()));
			
			agencias.append(codigo + uf);
		}
		return agencias.toString();
	}
	

}