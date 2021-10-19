package com.br.data.helper;

public class UfHelper {
	
	public static String getUf(final String lista, final String agencia) {
		
		int indexOf = lista.indexOf(agencia);
		return indexOf != -1 ? lista.substring(indexOf + 4, indexOf + 6) : "";
	}
}
