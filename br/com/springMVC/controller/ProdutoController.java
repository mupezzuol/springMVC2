package com.springMVC.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springMVC.DAO.ProdutoDAO;
import com.springMVC.infra.FileSaver;
import com.springMVC.model.Produto;
import com.springMVC.model.enums.TipoPreco;
import com.springMVC.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos") //Endereço DEFAULT antes de todos abaixo
public class ProdutoController  {
	
	@Autowired
	private ProdutoDAO produtoDAO; //Injeção do DAO.. Faz o NEW....
	
	@Autowired
    private FileSaver fileSaver; //Classe para gravar caminho do arquivo
	
	//Na inicialização do Binder, vamos adc um validador dele, que vá até a classe X que implemeta o Validator
	@InitBinder
	public void initBinder(WebDataBinder binder){
	    binder.addValidators(new ProdutoValidation());
	}
	
	//Os parametros recebido pelo metodo precisa ser o mesmo nome dos inputs do formulado da propriedade 'name'
	//Esse processo se chama BINDING (funciona com parametro normal e até mesmo um objeto)
	//@Valid mostra que ele estará de acordo para validação
	//result é após validação ele recolhe o resultado do Bind do @Valid...
	@RequestMapping(value="/gravar", method=RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		
		//Se o result, que recolheu os erros, tiver tido erro, ele retorna para a página de cadastro
		if(result.hasErrors()){
	        return homeProduto(produto);
	    }
		
		String path = fileSaver.write("arquivos-sumario", sumario);//Salvo o arquivo dentro da pasta criada do projeto. .(webapp)
	    produto.setSumarioPath(path);//Seto o endereço String 'path' para salvar no banco o endereço
		
		produtoDAO.gravar(produto);
		redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!");//Redireciona o atributo para a próxima requisicao, mantendo ele no outro método, sem perde-lo
		return new ModelAndView("redirect:/produtos");//Chamo direto outro método dentro da Controller com o REDIRECT, para não causar o "BUG do F5"
	}
	
	//Não coloquei o endereço pois é o endereço HOME 'produtos'
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView mv = new ModelAndView("produto/lista");
		mv.addObject("listProdutos", produtos);
		return mv;
	}
	
	
	@RequestMapping(value="/CadProdutos", method=RequestMethod.GET)
	public ModelAndView homeProduto(Produto produto) {
		ModelAndView mv = new ModelAndView("produto/cadastroProduto");//Do MODEL para a VIEW
		mv.addObject("tipos", TipoPreco.values());
		return mv;
	}
	
	@RequestMapping("/detalhe/{id}")//Passo o ID com uma URL Amigavel
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView mv = new ModelAndView("/produto/detalhe");
	    Produto produto = produtoDAO.find(id);
	    mv.addObject("produto", produto);
	    return mv;
	}
	
	
	
	
	
	

}
