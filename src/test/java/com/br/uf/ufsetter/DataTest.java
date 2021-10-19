package com.br.uf.ufsetter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.data.DataApplication;
import com.br.data.response.DataResponse;
import com.br.data.service.DataServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataApplication.class)
public class DataTest {
	
	private static WireMockServer wireMockServer = new WireMockServer(8181);
	
	@Autowired
	private DataServiceImpl dataServiceImpl;
	
    @BeforeAll
    static void setUpAll() {
        wireMockServer.start();
    }
	
    @AfterAll
    static void cleanUpAll() {
        wireMockServer.stop();
    }
	
	@Test
	public void exampleTest() {
		wireMockServer.stubFor(get("/data/341/7069")
	        .willReturn(
	        		aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ \"uf\": \"AC\" }")));
		
		DataResponse r = dataServiceImpl.getData("341", "7069");
	    assertEquals("AC", r.getUf());

		wireMockServer.verify(getRequestedFor(urlPathEqualTo("/data/341/7069")));
	}
}
