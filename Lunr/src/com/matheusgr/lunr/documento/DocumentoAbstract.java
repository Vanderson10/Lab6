package com.matheusgr.lunr.documento;

import java.util.Arrays;
import java.util.Map;

import biblitex.TransformaTexto;

public abstract class DocumentoAbstract implements Documento{
	
	private String id;
	private String original;
	private String limpo;
	private String[] split;
	
	public DocumentoAbstract(String id, String original, String limpo) {
		this.id = id;
		this.original = original;
		this.limpo = limpo;
		
	}

	@Override
	public double metricaTextoUtil() {
		long extractedSize = (new TransformaTexto()).transforma(TransformaTexto.Algoritmos.cleanSpaces, this.limpo).length();
		return (1.0 * extractedSize) / this.original.length();
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String[] getTexto() {
		if (this.split == null) {
			this.split = (new TransformaTexto()).transforma(TransformaTexto.Algoritmos.cleanLines, this.limpo).split(" ");
			Arrays.sort(this.split);
		}
		return this.split;
	}

	@Override
	public abstract Map<String, String> getMetadados();
	
	@Override
	public String toString() {
		return "===" + this.id + System.lineSeparator() + this.limpo;
	}
	
	public String getOriginal() {
		return this.original;
	}
	
	public String getLimpo() {
		return this.limpo;
	}

}
