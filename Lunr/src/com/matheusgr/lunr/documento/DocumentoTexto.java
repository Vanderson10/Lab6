package com.matheusgr.lunr.documento;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import biblitex.TransformaTexto;

/**
 * Documento de texto simples. Não precisa de tratamento complexos nem tem
 * metadados próprios.
 */
class DocumentoTexto extends DocumentoAbstract{
	
	private Map<String, String> metadados;

	/**
	 * Construtor padrão do documento de texto.
	 * @param id ID do documento.
	 * @param txt Texto do documento.
	 */
	public DocumentoTexto(String id, String txt) {
		super(id, txt, limpo(txt));
	}
	
	private static String limpo(String txt) {
		return (new TransformaTexto()).transforma(TransformaTexto.Algoritmos.clean, txt).strip();
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
		DocumentoTexto other = (DocumentoTexto) obj;
		return Objects.equals(super.getId(), other.getId());
	}
	
	@Override
	public String toString() {
		return "===" + super.getId()+ System.lineSeparator() + super.getLimpo();
	}

	@Override
	public Map<String, String> getMetadados() {
		if (this.metadados != null) {
			return this.metadados;
		}
		this.metadados = new HashMap<String, String>();
		this.metadados.put("LINHAS", "" + super.getOriginal().chars().filter((value) -> '\n' == value).count());
		this.metadados.put("TAMANHO", "" + super.getLimpo().length());
		this.metadados.put("METADATADATE", "" + System.currentTimeMillis());
		this.metadados.put("TIPO", "" + "txt");
		return this.metadados;
	}

}