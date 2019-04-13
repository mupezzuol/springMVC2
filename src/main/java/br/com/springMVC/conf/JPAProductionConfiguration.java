package br.com.springMVC.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//Profile de Produção, para subir para o HEROKU
@EnableTransactionManagement
@Profile("prod")
public class JPAProductionConfiguration {
	
	//Spring injeta pra gnt a variavel de ambiente, de acordo com o ambiente
	@Autowired
	private Environment environment;//org.springframework.core.env.Environment
	
	
	//Configurado para subir no Heroku
	//DATABASE_URL vem no padrão de URL -> usuario:senha@host:port/path
	@Bean
	public DataSource dataSource() throws URISyntaxException{
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    
	    //DATABASE_URL -> variavel de ambiente, para pegar precisamos do 'environment' ele pega na inicialicação do sistema
	    //Spring injeta nosso ambiente, e pegamos a URL no padrão 'DATABASE_URL'
	    URI dbUrl = new URI(environment.getProperty("DATABASE_URL"));

	    dataSource.setUrl("jdbc:postgresql://"+dbUrl.getHost()+":"+dbUrl.getPort()+dbUrl.getPath());
	    dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);//Pego o usuário com split
	    dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);//Pego a senha com split

	    return dataSource;
	}
	 
	 //Configs para o Heroku
	@Bean
	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		return properties;
	}

}
