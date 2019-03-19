package com.springMVC.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springMVC.model.Produto;

@Repository
@Transactional
public class ProdutoDAO {
	
	@PersistenceContext
	private EntityManager manager; 
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> listar() {
		return manager.createQuery("select p from Produto p", Produto.class).getResultList();
	}
	
	public Produto find(int id) {
		return manager.createQuery("select distinct(p) from Produto p "
				+ "join fetch p.precos precos "
				+ "where p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
}
