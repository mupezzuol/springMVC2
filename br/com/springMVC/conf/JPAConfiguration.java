package com.springMVC.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement //Deixo habilitado para ser utilizado o Spring Transaction
public class JPAConfiguration {
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(vendorAdapter);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("postgres");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springMVC1");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        factoryBean.setDataSource(dataSource);//Na criação do factory passamos o objeto dataSource

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); //Dialeto HIBERNATE
        props.setProperty("hibernate.show_sql", "true"); //Mostrar as QUERYS
        props.setProperty("hibernate.hbm2ddl.auto", "update"); //Toda modificação nos modelos ele alterar no banco

        factoryBean.setJpaProperties(props);//Seto as propriedados do factory atraves da classe Properties

        factoryBean.setPackagesToScan("com.springMVC.model");//Pacote que será escaniado os repositorios

        return factoryBean;
    }
	
	//Método para gerenciar nossa transação atraves do nosso EntityManager
	@Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }
	
}
