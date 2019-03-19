package com.springMVC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springMVC.DAO.ProdutoDAO;
import com.springMVC.model.Produto;

@Controller
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@RequestMapping("/")
	@Cacheable(value="produtosHome")//Preciso colocar nome do pacote do ache, para o Map(key,value) encontrar atraves do nome
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("home");
		List<Produto> listProduto = produtoDAO.listar();
		mv.addObject("produtos",listProduto); //Retorna para HOME a lista de produtos do banco
		return mv;
	}
	
}
