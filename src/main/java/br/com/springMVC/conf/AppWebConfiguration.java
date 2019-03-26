package br.com.springMVC.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;
import br.com.springMVC.conf.rest.JsonViewResolver;

@SuppressWarnings("deprecation")
@EnableWebMvc
@ComponentScan(basePackages = {"br.com.springMVC.conf", "br.com.springMVC.conf.rest", "br.com.springMVC.conf.security",
	    "br.com.springMVC.controller", "br.com.springMVC.DAO", "br.com.springMVC.DAO.auth", "br.com.springMVC.infra",
	    "br.com.springMVC.model", "br.com.springMVC.model.auth", "br.com.springMVC.model.enums", "br.com.springMVC.validation"})
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter{
	
	@Override //Deixa o tomcat se virar para pegar os arquivos dentro da aplicação
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	    configurer.enable();
	}
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver(); 
		resolver.setPrefix("/WEB-INF/views/");//Tudo que vem primeiro, o PREFIXO
		resolver.setSuffix(".jsp");//Tudo que vem por último, o nosso SUFIXO
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
	    DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
	    formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
	    formatterRegistrar.registerFormatters(conversionService);
	    return conversionService;
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
    }
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean //Spring injeta o método pra gnt.. Guava (cache da google)
	public CacheManager cacheManager() {
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
				.maximumSize(100) //Até 100 elementos
				.expireAfterAccess(5, TimeUnit.MINUTES);//Expira em 5 minutos.
		GuavaCacheManager manager = new GuavaCacheManager();//Quem está cuidando do cahce agora é o GUAVA não o Spring
		manager.setCacheBuilder(builder);//Mando para meu cache essas configs
		
		return manager;
	}
	
	//Método que gerenciamos/negociamos qual será o retorno da view, JSON, HTML etc...
	@Bean
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager){
		List<ViewResolver> viewResolvers = new ArrayList<>();
	    viewResolvers.add(internalResourceViewResolver());//Resolver que nós já temos como padrão
	    viewResolvers.add(new JsonViewResolver());//Resolver para JSON que criamos
		
		
		//Configurar negociação que ele pode fazer
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);//Esse cara vai decidir qual View será retornado, ele negocia.
		
		return resolver;
	}
	

}
