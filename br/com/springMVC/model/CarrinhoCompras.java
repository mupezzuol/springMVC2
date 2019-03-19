package com.springMVC.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.springMVC.model.enums.TipoPreco;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION,//Para cada usuário que acessar, será um sessão diferente. Preciso sempre configurar a Controller que chama essa classe
		proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Usamos MAP de CarrinhoItem e Integer
	//LinkedHashMap é usado junto com hashCode e Equals, para varrer exatamente os itens corretos
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
	
	
	//Adiciona no carrinho, porém qnd ele vai pegar a QUANTIDADE ele vá até um método que:
	//Verifica se aquele ITEM já existe no MAP, se ele existir é coloco o valor ZERO e retorna ZERO no método, dessa forma não muda o inteiro
	//Se não tem o ITEM, ele simplesmente retorna o ITEM normalmente, que será 1. Ficando sempre o ITEM + 1.
	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);
	}
	
	
	//Atenção nesse método de validação de itens no carrinho
	public Integer getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		
		return itens.get(item);
	}
	
	//Publico, será usado no getQuantidade do Spring da página na hora de pegar via JSTL
	//Usando Lampda.. Reduce usado como se fosse um contador
	public int getQuantidade() {
		return itens.values().stream().reduce(0, 
				(proximo, acumulador) -> proximo + acumulador);
	}
	
	//Uso para pegar os itens do carrinho etc...
	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}
	
	//Total de acordo com CADA item
	public BigDecimal getTotal(CarrinhoItem item) {
		return item.getTotal(getQuantidade(item));
	}
	
	//Total do carrinho todo
	//itens.keySet() pega os valores de cada item que temos etc...
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (CarrinhoItem item : itens.keySet()) {
			total = total.add(getTotal(item));
		}
		return total;
	}


	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);//Crio produto com esse ID recebido
		itens.remove(new CarrinhoItem(produto, tipoPreco));//Removo através da KEY, que é do ID recolhido. Removo da lista
	}
	
	

}
