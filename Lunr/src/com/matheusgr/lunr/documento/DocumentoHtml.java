package com.matheusgr.lunr.documento;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import biblitex.TransformaTexto;

/**
 * DocumentoHTML representa e extrai dados de um HTML.
 * 
 * Os termos extraídos são obtidos dos textos dos nós do HTML. São ignorados
 * nome de tags ou de propriedades. Por não ser um texto bem formulado, limpa-se
 * da melhor forma possível o HTML.
 * 
 * Os metadados são obtidos de características do documento, mas de detalhes
 * descritos na tag HEAD.
 */
class DocumentoHtml extends DocumentoAbstract {

	private static final String HEAD_METADADO = "HEAD";

	/**
	 * Construtor padrão. Realiza o processamento de extração do HTML.
	 * 
	 * @param id ID do documento a ser criado.
	 * @param original HTML do documento a ser criado.
	 */
	public DocumentoHtml(String id, String original) {
		super(id, original, limpo(original));
	}
	
	private static String limpo(String original) {
		var transformaTexto = new TransformaTexto();
		String txt = transformaTexto.transforma(TransformaTexto.Algoritmos.html, original);
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
		DocumentoHtml other = (DocumentoHtml) obj;
		return Objects.equals(super.getId(), other.getId());
	}

	@Override
	public String toString() {
		return "===" + super.getId() + System.lineSeparator() + this.getMetadados().get(HEAD_METADADO)
				+ System.lineSeparator() + "===" + super.getLimpo();
	}

	@Override
	public Map<String, String> getMetadados() {
		Map<String, String> metadados = super.getMetadado();
		if (metadados != null) {
			return metadados;
		}
		metadados = extractHead(super.getOriginal());
		metadados.put("LINHAS", "" + super.getOriginal().chars().filter((value) -> '\n' == value).count());
		metadados.put("TAMANHO", "" + super.getLimpo().length());
		metadados.put("METADATADATE", "" + System.currentTimeMillis());
		metadados.put("TIPO", "" + "html");
		return metadados;
	}

	/*
	 * Os metadados do HTML incluem, especificamente:
	 * - Quantidade de tags, estimada a partir da quantidade de símbolos <
	 * - Todo o corpo da tag <head>
	 */
	private Map<String, String> extractHead(String original2) {
		Map<String, String> metadados2 = new HashMap<>();
		metadados2.put("BRUTE_TAGS", "" + super.getOriginal().chars().filter((value) -> '<' == value).count());
		String meta = "";
		int headStart = original2.toLowerCase().indexOf("<head>");
		if (headStart != -1) {
			int headEnd = original2.toLowerCase().indexOf("</head>");
			if (headEnd != -1) {
				meta = original2.substring(headStart, headEnd);
			}
		}
		metadados2.put(HEAD_METADADO, meta);
		return metadados2;
	}
}