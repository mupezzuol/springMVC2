package br.com.springMVC.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.springMVC.DAO.ProdutoDAO;
import br.com.springMVC.DAO.auth.UsuarioDAO;
import br.com.springMVC.model.Produto;
import br.com.springMVC.model.auth.Role;
import br.com.springMVC.model.auth.Usuario;

@Controller
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@RequestMapping("/")
	@Cacheable(value="produtosHome")//Preciso colocar nome do pacote do ache, para o Map(key,value) encontrar atraves do nome
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("home");
		List<Produto> listProduto = produtoDAO.listar();
		mv.addObject("produtos",listProduto); //Retorna para HOME a lista de produtos do banco
		return mv;
	}
	
	
	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-maluca-oajksfbvad6584i57j54f9684nvi658efnoewfmnvowefnoeijn")
	public String urlMagicaMaluca() {
	    //User Admin
		Usuario userAdmin = new Usuario(); 
	    userAdmin.setNome("Administrador");
	    userAdmin.setEmail("admin");
	    userAdmin.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
	    userAdmin.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
	    
	    //User Aux
	    Usuario userAux = new Usuario(); 
	    userAux.setNome("Auxiliar");
	    userAux.setEmail("aux");
	    userAux.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
	    userAux.setRoles(Arrays.asList(new Role("ROLE_AUX")));
	    
	    usuarioDAO.gravar(userAdmin);//Cadastra usuário ADMIN
	    usuarioDAO.gravar(userAux);//Cadastra usuário AUX

	    return "URL Mágica Executada!!!";
	}
	
}
