package br.com.springMVC.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.springMVC.DAO.auth.UsuarioDAO;

@SuppressWarnings("deprecation")
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UsuarioDAO usuarioDAO;
	
	
	//A ordem dos acessos é relevante, começa sempre NEGANDO e depois LIBERANDO qnd se trata do mesmo prefixo de URL
	// Como no caso de '/produtos'
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	    .antMatchers("/resources/**").permitAll()//Todos terão acesso, para que nos request não ocorram esse de direcionar pasa CSS etc..
	    .antMatchers("/carrinho/**").permitAll()
	    .antMatchers("/pagamento/**").permitAll()
	    .antMatchers("/produtos/formProdutos").hasRole("ADMIN")//Somente acessos com perfil de ADMIN acessará essa URL
	    .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")//Método via POST e GET só acesso para usuários com perfil ADMIN
	    .antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")//ADMIN -> ROLE_ALGUMACOISA -> é o default...
	    .antMatchers(HttpMethod.GET, "/produtos/").hasRole("ADMIN")
	    .antMatchers("/produtos/**").permitAll()//Após bloquear alguns URL, todas as outras serão permitidas acesso
	    .antMatchers("/").permitAll()//Home da aplicação será permitida
	    .anyRequest().authenticated()//Qualquer requisição DEVERÁ ser autenticada
	    .and().formLogin().loginPage("/login").permitAll()//Após autenticação vá para tela de LOGIN via controller(url) caso seja recusado o acesso, página de login personalizada.
	    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));//Hablito o logout, atraves da URL '/logout' default no spring security
	}
	
	
	//Configuramos o USUÁRIO aqui, onde terá nossas permissões
	//Ele é um UserDetails que o UsuárioDAO implementa
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDAO)//
        .passwordEncoder(new BCryptPasswordEncoder());//Encriptação
    }
	
}
