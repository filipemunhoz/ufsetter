package com.br.uf.ufsetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.br.data.helper.UfHelper;
import com.br.data.service.DataServiceImpl;

public class PerformanceTest {
	
	final String lista = "7069SP0001SP";
	static DataServiceImpl d;
	
	@BeforeAll
	public static void init() {
		d = new DataServiceImpl();
	}
	
	@Test
	public void ufTest() {
		String uf = UfHelper.getUf(lista, "7069");		
		assertEquals("SP", uf);
	}

	@Test
	public void emptyTest() {
		String uf = UfHelper.getUf(lista, "9010");
		assertEquals("", uf);
	}
	
	@Test
	public void geraStringTest() {
		String s = d.geraString();
		assertNotNull(s);
		assertThat(s).isNotEmpty();
		assertThat(s).hasSize(54006);
	}
	
	@Test
	void performanceTest() {
		
		assertTimeout(Duration.ofMillis(200), () -> {
			d.geraString();
		});		
		
		assertTimeout(Duration.ofMillis(1), () -> {
			UfHelper.getUf(lista, "7069");
		});
	}
	

}
