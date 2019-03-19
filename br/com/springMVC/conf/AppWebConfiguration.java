package com.springMVC.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.springMVC.DAO.ProdutoDAO;
import com.springMVC.controller.HomeController;
import com.springMVC.infra.FileSaver;
import com.springMVC.model.CarrinhoCompras;

//Para a IDE tirar o erro.. Usando 'deprecation' é qnd usamos uma classe antiga que foi substituido etc...
@SuppressWarnings("deprecation")
@EnableWebMvc
@ComponentScan(basePackageClasses= {HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class})
public class AppWebConfiguration extends WebMvcConfigurerAdapter{
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	    configurer.enable();
	}
	
	//O retorno seja gerenciado pelo Spring, dessa forma temos que colocar como Beans
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver(); 
		resolver.setPrefix("/WEB-INF/views/");//Tudo que vem primeiro, o PREFIXO
		resolver.setSuffix(".jsp");//Tudo que vem por último, o nosso SUFIXO
		
		
		//Atributos Gerais
		//resolver.setExposeContextBeansAsAttributes(true); //TODOS OS ATRIBUTOS FICARÃO DISPONIVEIS NAS PÁGINAS
		
		//Habilitando atributo das classe individualmente.. Nome da classe com inicial minuscula
		resolver.setExposedContextBeanNames("carrinhoCompras");
		
		return resolver;
	}
	
	@Bean
	public MessageSource messageSource(){
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("/WEB-INF/messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    messageSource.setCacheSeconds(1);
	    return messageSource;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService(){
	    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
	    
	    //Registro o formato que eu vou RECEBER para ele transformar no padrão de formato de saída de Date para o banco
	    DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
	    formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
	    formatterRegistrar.registerFormatters(conversionService);

	    return conversionService;
	}
	
	@Bean //Método para configurar envio de arquivos, multiplos arquivos etc...
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
    }
	
	@Bean //Bean para o Spring encontrar nossa config
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	
	
	
	
	
	
	//Classe que contém todas as configurações referente aos Controllers do Spring
	//Ele é especifico para parte de web
	
	//ANOTAÇÕES:
	/*
	@EnableWebMvc -> Habilita o Web MVC do spring 
	
	@ComponentScan-> Indica onde estão o pacote base dos Controllers
	-	(basePackages= {array de string dos enderecos dos pacotes onde estão as controller}) 
	-	basePackageClasses= {SuaClasse.class} << Spring já é esperto, então se mudar o pacote não tem problema, pois ele pega a classe
	*/
}
