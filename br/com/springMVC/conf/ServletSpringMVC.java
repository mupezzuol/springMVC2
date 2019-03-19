package com.springMVC.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	//Método que procura a classe de configuração dos Controllers do Spring
	//Nesse caso retornamos a classe onde constam essas configurações
	//Colocamos classes de configurações da JPA também
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppWebConfiguration.class, JPAConfiguration.class};
	}
	
	//Método de configuração do mapeando que eu quero que o Spring gerencie
	//Nesse caso ele vai gerianciar o que tiver '/' em diante, ou seja TUDO.
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
    @Override
    protected Filter[] getServletFilters() {
    	CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        return new Filter[] {encodingFilter};
    }
    
    //Config de concatenação do endereço do arquivo, se precisa de '/' entre outros, nesse caso aspas vazia quer dizer
    // que queremos que o arquivo fique do jeito que ele vem por padrão...
    @Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(""));
    }

}
