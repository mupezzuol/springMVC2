package com.springMVC.conf.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@SuppressWarnings("deprecation")
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	//A ordem dos acessos é relevante, começa sempre NEGANDO e depois LIBERANDO qnd se trata do mesmo prefixo de URL
	// Como no caso de '/produtos'
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	    .antMatchers("/resources/**").permitAll()//Todos terão acesso
	    .antMatchers("/carrinho/**").permitAll()
	    .antMatchers("/pagamento/**").permitAll()
	    .antMatchers("/produtos/formProdutos").hasRole("ADMIN")//Somente acessos com perfil de ADMIN acessará essa URL
	    .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")//Método via POST e GET só acesso para usuários com perfil ADMIN
	    .antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
	    .antMatchers(HttpMethod.GET, "/produtos/").hasRole("ADMIN")
	    .antMatchers("/produtos/**").permitAll()//Após bloquear alguns URL, todas as outras serão permitidas acesso
	    .antMatchers("/").permitAll()//Home da aplicação será permitida
	    .anyRequest().authenticated()//Qualquer requisição DEVERÁ ser autenticada
	    .and().formLogin();//Após autenticação vá para tela de LOGIN caso seja recusado o acesso.
	}
	
}
