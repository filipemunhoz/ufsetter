package com.br.uf.ufsetter;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {
	
	@Mock
	List<String> list;
	
	@Test
	public void mockingArray() {
		
		list.add("SP");
		Mockito.verify(list).add("SP");
		assertEquals(0, list.size());
		
		Mockito.when(list.size()).thenReturn(100);
		assertEquals(100, list.size());
	}
}
