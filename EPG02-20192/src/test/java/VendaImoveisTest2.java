
import static org.junit.Assert.*;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class VendaImoveisTest2 {
	
	// Estruturas de dados a serem usadas nos testes
	VendaImoveis v1;
	VendaImoveis v2;
	HashSet<String> imoveis1;
	HashSet<String> imoveis2;
	
	@Before
	public void init () {
		// Criando Dados de Teste
		v1 = new VendaImoveis("./src/main/resources/Vizinhanca2.csv");
		v2 = new VendaImoveis("./src/main/resources/Vazio.csv");
		imoveis1 = new HashSet<String>();
		imoveis1.add("I1"); imoveis1.add("I2"); imoveis1.add("I3");
		imoveis2 = new HashSet<String>();
	}
	
	@Test
	public void test1() {
		String imovel = v1.localizaImovel("RESTAURANTE",imoveis1);
		assertEquals("I1", imovel);
	}
	
	@Test
	public void test2() {
		String imovel = v1.localizaImovel("SHOPPING",imoveis1);
		assertEquals("I3", imovel);
	}
	
	@Test
	public void test3() {
		String imovel = v1.localizaImovel("ESCOLA",imoveis1);
		assertEquals("I1", imovel);
	}
	
	@Test
	public void test4() {
		String imovel = v1.localizaImovel("HOSPITAL",imoveis1);
		assertNull(imovel);
	}
	
	@Test
	public void test5() {
		String imovel = v1.localizaImovel("Q4",imoveis1);
		assertEquals("I2", imovel);
	}
	
}
