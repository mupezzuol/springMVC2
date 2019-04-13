package br.com.springMVC.DAO.auth;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.springMVC.model.auth.Usuario;

@Repository
public class UsuarioDAO implements UserDetailsService {
	
	@PersistenceContext
	private EntityManager manager;
	
	//Método 'loadUserByUsername' vem da INTERFACE
	//Usuário retornar um UserDetails pois Usuário implementa UserDetails
	public Usuario loadUserByUsername(String email){
	    List<Usuario> usuarios = manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
	            .setParameter("email", email)
	            .getResultList();//Retorna uma lista
	    
	    //Se for vaziu joga uma Exception
	    if(usuarios.isEmpty()){
	        throw new UsernameNotFoundException("O usuário "+ email +" não foi encontrado");
	    }

	    return usuarios.get(0);//Pega primeiro valor da lista.
	}

	public void gravar(Usuario usuario) {
		manager.persist(usuario);
	}

	
}
