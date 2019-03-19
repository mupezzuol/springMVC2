package com.springMVC.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springMVC.model.CarrinhoCompras;
import com.springMVC.model.DadosPagamento;


@RequestMapping("/pagamento")
@Controller
public class PagamentoController {
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired //Precisamos declarar nas configs o Bean do restTemplate
	private RestTemplate restTemplate;
	
	
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(RedirectAttributes model) {
		
		//Callable é uma classe anonima para que o Spring entenda que esse método será requisições ASSINCRONAS
		//Por ser um classe anonima, temos que implemetar a chamada da classe anonima e colocar o código dentro dela
		return () -> {
			try {
				String uri = "https://book-payment.herokuapp.com/payment";
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);//Faz requisicao Rest e retorna
				System.out.println(response);
				model.addFlashAttribute("sucesso",response);//Retorno da resposta da API Rest que está sendo consumida
				return new ModelAndView("redirect:/produtos");//Paga e retorna para listagem dos produtos
			} catch (HttpClientErrorException e) {
				//e.printStackTrace();//Joga o erro na tela
				model.addFlashAttribute("falha","Valor maior que o permitido!");
				return new ModelAndView("redirect:/produtos");
			}
		};
	}
	
}
