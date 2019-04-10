package br.com.springMVC.DAO;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.springMVC.builders.ProdutoBuilder;
import br.com.springMVC.conf.DataSourceConfigurationTest;
import br.com.springMVC.conf.JPAConfiguration;
import br.com.springMVC.model.Produto;
import br.com.springMVC.model.enums.TipoPreco;

//Classe do JUnit que habilita q o JUnit do Spring fará o controle dos testes
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class,ProdutoDAO.class, DataSourceConfigurationTest.class})//Classes para funcionar as funções que usamos na classe.. DAO, JPA etc...
@ActiveProfiles("test") //Profile ATIVO de test, onde será diferenciado na hora de injeção, para qual banco irá ser efetuado o test
public class ProdutoDAOTest {
	
	//JUnit com Spring faz a injeção de dependencia
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Test
	@Transactional //Criará a transação dentro desse método, para podermos gravar no banco para os testes...
	public void deveSomarTodosOsPrecosPorTipoLivro() {
	    
	    //Criando alguns produtos de teste do tipo Impresso
	    List<Produto> livrosImpressos = ProdutoBuilder
	    		.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN)
	    		.more(3).buildAll();
	    
	    //Criando alguns produtos de teste do tipo Ebook
	    List<Produto> livrosEbook = ProdutoBuilder
	    		.newProduto(TipoPreco.EBOOK, BigDecimal.TEN)
	    		.more(3).buildAll();
	    
	    //Gravando no banco os Produtos
	    livrosImpressos.stream().forEach(produtoDAO::gravar);//Chamar método da classe atraves de '::' 
	    livrosEbook.stream().forEach(produtoDAO::gravar);
	    
	    BigDecimal valor = produtoDAO.somaPrecosPorTipo(TipoPreco.EBOOK);
	    
	    //setScale(2) duas casas decimais
	    Assert.assertEquals(new BigDecimal(40).setScale(2), valor);//Valor que esperamos 40 e o valor que foi executado q deverá ser 40
	}
	
	
}
