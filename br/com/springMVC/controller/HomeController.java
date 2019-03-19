package com.springMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String index() {
		System.out.println("Entrando na Controller Home.");
		return "home";//Não preciso do '.jsp' pois já configuramos como Sufixo na classe de configuração do Spring
	}
	
	
}
