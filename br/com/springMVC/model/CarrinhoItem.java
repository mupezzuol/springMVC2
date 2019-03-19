package com.springMVC.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.springMVC.model.enums.TipoPreco;

public class CarrinhoItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private TipoPreco tipoPreco;
	
	public CarrinhoItem(Produto produto, TipoPreco tipoPreco) {
		super();
		this.produto = produto;
		this.tipoPreco = tipoPreco;
	}
	
	// Verifico o preco de acordo com o tipo
	public BigDecimal getPreco() {
		return produto.precoPara(tipoPreco);
	}
	
	//Retorno a quantidade total atraves de multiply, onde nós multiplacamos o valor pela quantidade daquele item daquele tipo
	public BigDecimal getTotal(int quantidade) {
		return this.getPreco().multiply(new BigDecimal(quantidade));
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public TipoPreco getTipoPreco() {
		return tipoPreco;
	}
	public void setTipoPreco(TipoPreco tipoPreco) {
		this.tipoPreco = tipoPreco;
	}
	
	//Implementamos hashCode e equals para não deixar adc itens no carrinho que sejam iguais
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((tipoPreco == null) ? 0 : tipoPreco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrinhoItem other = (CarrinhoItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (tipoPreco != other.tipoPreco)
			return false;
		return true;
	}

}
