package com.springMVC.model;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

import com.springMVC.model.enums.TipoPreco;

@Embeddable //Indica que isso fará parte do nosso PRODUTO
public class Preco {
	
	private BigDecimal valor;
    private TipoPreco tipo;
    
    
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public TipoPreco getTipo() {
		return tipo;
	}
	public void setTipo(TipoPreco tipo) {
		this.tipo = tipo;
	}
}
