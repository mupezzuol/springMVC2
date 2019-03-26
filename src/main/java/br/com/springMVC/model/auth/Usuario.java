package br.com.springMVC.model.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails{//Precisa implementar UserDetails para retornar no método DAO para o Security

	private static final long serialVersionUID = 1L;
	
    @Id
    private String email;
    
    private String nome;
    
    private String senha;
    
    //UM usuário tem VÁRIAS permissões
    //Fetch -> BUSCA
    //EAGER -> Eager Loading carrega os dados mesmo que você não vá utilizá-los
    //LAZY -> É o default, usando ele será retornado NULO os dados do banco,
    @OneToMany(fetch=FetchType.EAGER)
    private List<Role> roles = new ArrayList<Role>();
    
    
    
	//Métodos sobrescrito da INTERFACE
    //Nosso atributo de permissoes precisa implementar o 'GrantedAuthority'
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;//Nossas permissões que retornam um GrantedAuthority
	}

	@Override
	public String getPassword() {
		return this.senha;//Campo para validar senha
	}

	@Override
	public String getUsername() {
		return this.email;//Campo de usuário
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
    //Getter's and Setter's
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
