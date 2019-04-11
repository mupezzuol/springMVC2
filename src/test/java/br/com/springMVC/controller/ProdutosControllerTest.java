package br.com.springMVC.controller;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.springMVC.conf.AppWebConfiguration;
import br.com.springMVC.conf.DataSourceConfigurationTest;
import br.com.springMVC.conf.JPAConfiguration;
import br.com.springMVC.conf.security.SecurityConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //Puxa as configurações de contexto, onde tem configs de página, etc....
@ContextConfiguration(classes = {JPAConfiguration.class, AppWebConfiguration.class, 
		DataSourceConfigurationTest.class, SecurityConfiguration.class})
@ActiveProfiles("test")
public class ProdutosControllerTest {
	
	@Autowired
    private WebApplicationContext wac;//Spring injeta nossas configs de webContext da aplicação
	
	@Autowired
    private Filter springSecurityFilterChain;//Filtro que será injetado pelo Spring
	
	private MockMvc mockMvc;
	
	
	//Ele roda antes do teste, com a tag 'Before'
	//ANTES de executar os testes nós inicializamos o webAppContext, que é nossa config já configurada na aplicação
	//O spring injeta para nós com o '@Autowired'
	@Before
	public void setup(){
	    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
	    		.addFilter(springSecurityFilterChain)//Spring injeta nossos Filters que configuramos na nossa aplicação...
	    		.build();//Mandamos fazer o build do nosso webContext injetado pelo Spring
	}
	
	
	//Fazemos um 'get' na URL X, e esperamos um retorno para a página X
	//Ele retorna caso nossa requisição esteja dessa forma
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception{
	    mockMvc.perform(MockMvcRequestBuilders.get("/"))//Requisição via GET para URL '/'
	            .andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))//Atributo que nós enviamos para a página abaixo
	            .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"));//Deve ser direcionado para essa página
	}
	
	
	
	//Testando permissões, onde na URL abaixo, somente users com role 'ADMIN' podem acessar, ou seja,
	//Ele faz uma requisição de um usuário que NÃO tem permissao, portanto deverá retornar 403
	//Se retornar 403 é pq ele realmente não tem permissao
	@Test
	public void somenteAdminDeveAcessarProdutosForm() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/formProdutos")
	            .with(SecurityMockMvcRequestPostProcessors
	                .user("aux")//Usuário aux, ou seja, não deve acessar, deve retornar um 403
	                .password("123456")
	                .roles("AUX")))//Ele tem acesso as permissões de AUX, portanto não deve acessar a URL acima
	            .andExpect(MockMvcResultMatchers.status().is(403));//Esperamos o Status de 403 (permissão negada)
	}
	
	
}
