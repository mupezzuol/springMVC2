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
@RequestMapping("/produtos") 
public class ProdutoController  {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
    private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
	    binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping(value="/gravar", method=RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()){
	        return homeProduto(produto);
	    }
		
		String path = fileSaver.write("arquivos-sumario", sumario);
	    produto.setSumarioPath(path);
		
		produtoDAO.gravar(produto);
		redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:/produtos");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView mv = new ModelAndView("produto/lista");
		mv.addObject("listProdutos", produtos);
		return mv;
	}
	
	@RequestMapping(value="/CadProdutos", method=RequestMethod.GET)
	public ModelAndView homeProduto(Produto produto) {
		ModelAndView mv = new ModelAndView("produto/cadastroProduto");
		mv.addObject("tipos", TipoPreco.values());
		return mv;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView mv = new ModelAndView("/produto/detalhe");
	    Produto produto = produtoDAO.find(id);
	    mv.addObject("produto", produto);
	    return mv;
	}

}
