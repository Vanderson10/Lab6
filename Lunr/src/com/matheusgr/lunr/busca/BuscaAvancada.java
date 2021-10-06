package com.matheusgr.lunr.busca;

import java.util.HashMap;
import java.util.Map;

import com.matheusgr.lunr.documento.Documento;
import com.matheusgr.lunr.documento.DocumentoService;

public class BuscaAvancada implements Busca{
	private Map<String, String> metadados;
	
	public BuscaAvancada(Map<String, String> metadados) {
		(new ValidadorBusca()).valida(metadados);
		this.metadados = metadados;
	}

	@Override
	public Map<Documento, Integer> busca(DocumentoService ds) {
		Map<Documento, Integer> respostaDocumento = new HashMap<>();
		for (Documento d : ds.busca(this.metadados)) {
			respostaDocumento.put(d, this.metadados.size());
		}
		return respostaDocumento;
	}

	@Override
	public String[][] descreveConsulta() {
		String[][] resultado = new String[this.metadados.size()][];
		int i = 0;
		for (String m : this.metadados.keySet()) {
			String valor = this.metadados.get(m);
			resultado[i] = new String[] {"TERMO " + (i + 1), m+" - "+valor};
			i+=1;
		}
		return resultado;
	}

}
