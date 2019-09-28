import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class VendaImoveisTest {
	
	// Estruturas de dados a serem usadas nos testes
	VendaImoveis v1;
	VendaImoveis v2;
	HashSet<String> imoveis1;
	HashSet<String> imoveis2;
	
	@Before
	public void init () {
		// Criando Dados de Teste
		v1 = new VendaImoveis("./src/main/resources/Vizinhanca.csv");
		v2 = new VendaImoveis("./src/main/resources/Vazio.csv");
		imoveis1 = new HashSet<String>();
		imoveis1.add("I1"); imoveis1.add("I2"); imoveis1.add("I3"); imoveis1.add("I4"); imoveis1.add("I5");
		imoveis2 = new HashSet<String>();
	}
	
	@Test
	public void test1() {
		String imovel = v1.localizaImovel("ESCOLA",imoveis1);
		assertEquals("I5",imovel);

	}
	
	@Test 
	public void test2 () {
		String imovel = v1.localizaImovel("PRACA",imoveis1);
		assertTrue(imovel.equals("I2")||imovel.equals("I1"));
		
	}
	
	@Test
	public void test3 () {
		String imovel = v1.localizaImovel("SHOPPING",imoveis1);
		assertEquals("I5",imovel);
	}

	
	@Test 
	public void test4 () {
		String imovel = v1.localizaImovel("HOSPITAL",imoveis1);
		assertEquals("I1",imovel);
	}
	
	@Test 
	public void test5 () {
		String imovel = v1.localizaImovel(null,imoveis1);
		assertNull(imovel);
	}
	
	@Test 
	public void test6 () {
		String imovel = v1.localizaImovel("invalido",imoveis1);
		assertNull(imovel);
	}
	
	@Test 
	public void test7 () {
		String imovel = v1.localizaImovel("Q4",imoveis1);
		assertEquals("I3",imovel);
	}

	@Test 
	public void test8 () {
		String imovel = v1.localizaImovel("Q18",imoveis1);
		assertEquals("I2",imovel);
	}

	@Test 
	public void test9 () {
		String imovel = v1.localizaImovel("Q7",imoveis1);
		assertEquals("I4",imovel);
	}
	
	@Test
	public void test10 () {
		String imovel = v2.localizaImovel("Qualquer", imoveis1);
		assertNull(imovel);
	}
	
	@Test
	public void test11 () {
		String imovel = v2.localizaImovel("Qualquer", imoveis2);
		assertNull(imovel);
	}
	
	@Test
	public void test12 () {
		String imovel = v1.localizaImovel("ESCOLA", imoveis2);
		assertNull(imovel);
	}
	
	public void test13 () {
		String imovel = v1.localizaImovel("ESCOLA", null);
		assertNull(imovel);
	}
}
