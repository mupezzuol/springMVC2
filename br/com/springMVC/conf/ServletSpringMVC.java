package com.springMVC.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.springMVC.conf.security.SecurityConfiguration;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {//Assim que o projeto subir, a primeira coisa a fazer é chama o security
		return new Class[] {SecurityConfiguration.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses() { //Qnd for acessado pegará essas configs
		return new Class[] {AppWebConfiguration.class, JPAConfiguration.class};
	}
	
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
    
    @Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(""));
    }

}
