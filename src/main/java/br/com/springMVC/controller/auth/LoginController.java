package br.com.springMVC.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	//URL padrão do Spring Security para ir até a página de login
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginForm() {
		return "login/loginForm";
	}
	
}
