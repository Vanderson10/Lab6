package com.matheusgr.similaridade;

import com.matheusgr.lunr.documento.DocumentoService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.matheusgr.lunr.documento.Documento;

/**
 * Componente para tratamento da lógica de negócio relativa a similaridade.
 */
public class SimilaridadeService {

	private DocumentoService documentoService;

	/**
	 * Inicialização da lógica de serviço.
	 * 
	 * @param documentoService DocumentoService a ser utilizado pelo
	 *                         SimilaridadeService.
	 */
	public SimilaridadeService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	/**
	 * Calcula e retorna a similaridade.
	 * 
	 * Para o cálculo da similaridade:
	 * <ul>
	 * <li>Pega o documento 1</li>
	 * <li>Pega o documento 2</li>
	 * <li>Pega os termos do documento 1 e coloca em um conjunto (Termos1)</li>
	 * <li>Pega os termos do documento 2 e coloca em um conjunto (Termos2)</li>
	 * <li>Calcula a interseção entre Termos1 e Termos2 (Inters)</li>
	 * <li>Calcula a união entre Termos1 e Termos2 (Uniao)</li>
	 * <li>A similaridade é o tamanho de Inters sobre o tamanho do conjunto
	 * Uniao</li>
	 * </ul>
	 * 
	 * @param docId1 Documento 1.
	 * @param docId2 Documento 2.
	 * @return Valor de similaridade (entre 0 e 1, inclusives) representando a
	 *         semelhança entre os documentos.
	 */
	public double similaridade(String docId1, String docId2) {
		Set<String> termos1 = new HashSet<>();
		Set<String> termos2 = new HashSet<>();
		Set<String> termos11 = new HashSet<>();
		Set<String> termos22 = new HashSet<>();
		// PEGA DOCUMENTO 1
		Optional<Documento> documento1 = this.documentoService.recuperaDocumento(docId1);
		// PEGA DOCUMENTO 2
		Optional<Documento> documento2 = this.documentoService.recuperaDocumento(docId2);
		// COLOCA TERMOS DO DOCUMENTO 1 EM UM CONJUNTO
		if (documento1.isPresent()) {
			termos1 = iterarTermos(documento1.get()); 
			termos11 = iterarTermos(documento1.get());
		}
		// COLOCA TERMOS DO DOCUMENTO 2 EM OUTRO CONJUNTO
		if (documento2.isPresent()) {
			termos2 = iterarTermos(documento2.get());
			termos22 = iterarTermos(documento2.get());
		}
		// A SIMILARIDADE É DETERMINADA PELO...
		// --> (TAMANHO DA INTERSEÇÃO) / (TAMANHO DA UNIÃO DOS CONJUNTOS)
		termos1.retainAll(termos2);
		double intersecao = termos1.size();
		termos11.removeAll(termos22);
		double uniao = termos11.size()+termos22.size();
		return intersecao/uniao;
	}
	
	private Set<String> iterarTermos(Documento documento){
		Set<String> conjunto = new HashSet<>();
		String[] termos = documento.getTexto();
		for (String termo : termos) {
			conjunto.add(termo);
		}
		return conjunto;
	}

}
