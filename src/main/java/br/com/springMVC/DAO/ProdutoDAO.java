package br.com.springMVC.DAO;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.springMVC.model.Produto;
import br.com.springMVC.model.enums.TipoPreco;

@Repository
@Transactional
public class ProdutoDAO {
	
	@PersistenceContext
	private EntityManager manager; 
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}
	
	
	//Usamos FETCH para que traga a coleção de preços, pois por default o hibernate trata coleções como LAZY
	//ou seja, só vai até o banco quando for utilizada...
	//Podemos usar o OpenEntityManagerInViewFilter, porém ele faz muito mais query no banco, isso pode ser ruim dependendo da performace
	public List<Produto> listar() {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos", Produto.class).getResultList();
	}
	
	public Produto find(int id) {
		return manager.createQuery("select distinct(p) from Produto p "
				+ "join fetch p.precos precos "
				+ "where p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco){
		TypedQuery<BigDecimal> query = manager.createQuery("select sum(preco.valor) from Produto p "
	    		+ "join p.precos preco where preco.tipo = :tipoPreco", BigDecimal.class);
	    query.setParameter("tipoPreco", tipoPreco);
	    
	    return query.getSingleResult();
	}
	
}
