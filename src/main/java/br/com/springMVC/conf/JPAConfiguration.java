package br.com.springMVC.conf;

import java.util.Properties;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class JPAConfiguration {
	
	private String validateProfileProps = "test";
	
	//Passamos o dataSource para que o Spring consiga definir qual o Profile que irá injetar no dataSource
	//Se será profile de dev ou test.. dessa forma ele injeta oq ele receber..
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        
        factoryBean.setPackagesToScan(new String[]{"br.com.springMVC.model", "br.com.springMVC.model.enums", "br.com.springMVC.model.auth"});
        factoryBean.setDataSource(dataSource);//Recebe via param a injeção de dataSource que vier.. test/dev
        factoryBean.setJpaProperties(aditionalProperties());//Propriedades adicionais atraves do método q retornar o properties

        return factoryBean;
    }

	@Bean
	@Profile("dev") //Qnd profile for Dev, o Spring injetará esse dataSource
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("postgres");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springMVC2");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        
        validateProfileProps = "dev";//Passou por dataSource de dev
		return dataSource;
	}
	
	private Properties aditionalProperties() {
		Properties props = new Properties();
		
		if (validateProfileProps.equals("dev")) {
			props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	        props.setProperty("hibernate.show_sql", "true");
	        props.setProperty("hibernate.hbm2ddl.auto", "update");
		}else {
			//Properties de para profile de 'test' (zero o banco etc..)
			 props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
			 props.setProperty("hibernate.show_sql", "true");
			 props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		}
		
		return props;
	}
	
	@Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }
	
}
