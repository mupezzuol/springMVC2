package br.com.springMVC.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//Essa classe será usada nas classes de Teste, para chamar o Spring e ele INJETAR o dataSource de profile de 'test'
public class DataSourceConfigurationTest {
	
	@Bean
    @Profile("test")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/springMVC2_test");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("postgres");
        return dataSource;
    }
	
}
