package com.matheusgr.lunr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class SimilaridadeTest extends BaseTest{
	
	@Test 
	void testSimilaridadeProfessor() {
		assertEquals(0.2, (this.similaridadeController.similaridade(TEXTO1_ID, TEXTO2_ID)));
		
	}
	
	@Test 
	void testSimilaridadeCodigo() {
		System.out.println(this.similaridadeController.similaridade(TEXTO3_ID, TEXTO4_ID));
		
	}

}
