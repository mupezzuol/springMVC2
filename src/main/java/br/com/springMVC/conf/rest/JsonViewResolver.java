package br.com.springMVC.conf.rest;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();//View JSON
		jsonView.setPrettyPrint(true);//Habilito para o JSON sair formato no navegador
		return jsonView;
	}

}
