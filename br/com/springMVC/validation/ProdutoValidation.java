package com.springMVC.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springMVC.model.Produto;

public class ProdutoValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}
	
	//Target é o alvo, qual classe será validado, para iso fazemos o cast.
	@Override
	public void validate(Object target, Errors errors) {
		 ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");//Validando campo vazio do Produto
	     ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
	     ValidationUtils.rejectIfEmpty(errors, "dataLancamento", "field.required");

	     Produto produto = (Produto) target;//Cast da classe alvo
	     if(produto.getPaginas() <= 0){
	    	 errors.rejectValue("paginas", "field.required");//Se for 0 ou menor que zero ele retorna um ERROR. Int ñ pode ser null
	     }
	}

}
