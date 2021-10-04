package ar.com.ada.api.mutantes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.mutantes.entities.DNASample;
import ar.com.ada.api.mutantes.services.MutantService;

@SpringBootTest
class MutantesApplicationTests {

	@Autowired
	MutantService mutantService;

	@Test
	void contextLoads() {
	}

	@Test
	void validarMatrix(){
		String[] dnaBueno = {
			"ATGCGA",
			"CAGTGC",
			"TTATTT",
			"AGACGG",
			"GCGTCA",
			"TCACTG"};
		
		DNASample sampleBueno = new DNASample(dnaBueno);

		assertTrue(sampleBueno.isValid());

		String[] dnaMalo ={
			"ATGCGX",
			"CAGTGC",
			"TTATTTG",
			"AGACGG",
			"GCGTCA",
			"TCACTG"};

			DNASample sampleMalo = new DNASample(dnaMalo);

			assertFalse(sampleMalo.isValid());

			String[] dnaMinuscula = {
				"ATGCGA",
				"CAGTGC",
				"TTATTT",
				"AGACgG",
				"GCGTCA",
				"TCACTG"};
			
				DNASample sampleMinuscula = new DNASample(dnaMinuscula);

				assertFalse(sampleMinuscula.isValid());
	
		}

		

		



		


}
