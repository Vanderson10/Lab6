package com.matheusgr.lunr.documento;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import biblitex.TransformaTexto;

/**
 * Documento base java. As palavras-chave da linguagem ainda são preservadas
 * (como public, private, etc), mas elimina-se documentação e comentários.
 */
class DocumentoJava extends DocumentoAbstract {

	private Map<String, String> metadados;

	/**
	 * Cria o DocumentoJava, extraindo o texot base.
	 * 
	 * @param id       ID do documento a ser criado.
	 * @param original Código java original.
	 */
	public DocumentoJava(String id, String original) {
		super(id, original, limpo(original));
	}
	
	private static String limpo(String original) {
		var transformaTexto = new TransformaTexto();
		String txt = transformaTexto.transforma(TransformaTexto.Algoritmos.java, original);
		return transformaTexto.transforma(TransformaTexto.Algoritmos.clean, txt).strip();
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoJava other = (DocumentoJava) obj;
		return Objects.equals(super.getId(), other.getId());
	}

	@Override
	public String toString() {
		return "===" + super.getId() + System.lineSeparator() + super.getLimpo();
	}

	@Override
	public Map<String, String> getMetadados() {
		if (this.metadados != null) {
			return this.metadados;
		}
		this.metadados = extractData(super.getOriginal());
		this.metadados.put("LINHAS", "" + super.getOriginal().chars().filter((value) -> '\n' == value).count());
		this.metadados.put("TAMANHO", "" + super.getOriginal().length());
		this.metadados.put("METADATADATE", "" + System.currentTimeMillis());
		this.metadados.put("TIPO", "" + "java");
		return this.metadados;
	}

	/*
	 * Metadados particulares de java: número de imports e autor da classe.
	 */
	private Map<String, String> extractData(String original2) {
		Map<String, String> metadados2 = new HashMap<>();
		metadados2.put("IMPORTS", "" + ((super.getOriginal().length() - super.getOriginal().replaceAll("import ", "").length()) / 7));
		metadados2.put("AUTHOR", "" + (super.getOriginal().indexOf("@author") != -1 ? "TRUE" : ""));
		return metadados2;
	}

}