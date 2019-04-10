package br.com.springMVC.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.springMVC.conf.security.SecurityConfiguration;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {//Assim que o projeto subir, a primeira coisa a fazer é chama o security
		return new Class[] {SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class};
	}

	@Override
    protected Class<?>[] getServletConfigClasses() {//Qnd for acessado pegará essas configs
        return new Class[] {};
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
    
    
    //Configuro para subir minha aplicação em contexto de 'dev'
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(new RequestContextListener());//O Spring fica escutando nosso contexto para escutar nosso profile
        servletContext.setInitParameter("spring.profiles.active", "dev");//Ativamos o profile de 'dev' como default da aplicação
    }

}
