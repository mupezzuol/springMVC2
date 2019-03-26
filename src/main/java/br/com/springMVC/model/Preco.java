package br.com.springMVC.model;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

import br.com.springMVC.model.enums.TipoPreco;

@Embeddable
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
