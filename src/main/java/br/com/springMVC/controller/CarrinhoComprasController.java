package br.com.springMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.springMVC.DAO.ProdutoDAO;
import br.com.springMVC.model.CarrinhoCompras;
import br.com.springMVC.model.CarrinhoItem;
import br.com.springMVC.model.Produto;
import br.com.springMVC.model.enums.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private CarrinhoCompras carrinhoCompras;
	
	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipoPreco) {
		ModelAndView mv = new ModelAndView("redirect:/carrinho");
		CarrinhoItem carrinhoItem = criaItem(produtoId, tipoPreco);
		
		carrinhoCompras.add(carrinhoItem);
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView itens() {
		return new ModelAndView("/carrinho/itens");
	}
	
	
	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = produtoDAO.find(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		return carrinhoItem;
	}
	
	@RequestMapping("/remover")
	public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco) {
		carrinhoCompras.remover(produtoId, tipoPreco);
		return new ModelAndView("redirect:/carrinho");
	}
	
}
