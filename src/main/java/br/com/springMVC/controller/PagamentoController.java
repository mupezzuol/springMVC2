package br.com.springMVC.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springMVC.model.CarrinhoCompras;
import br.com.springMVC.model.DadosPagamento;
import br.com.springMVC.model.auth.Usuario;


@RequestMapping("/pagamento")
@Controller
public class PagamentoController {
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	//Spring injeta o MailSender que configuramos no appWebCofniguration, um sender de envio etc..
	@Autowired
	private MailSender sender;
	
	
	//@AuthenticationPrincipal o Spring injeta o usuário logado pra gnt
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model) {
		
		return () -> {
			try {
				String uri = "https://book-payment.herokuapp.com/payment";
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				System.out.println(response);
				
				enviaEmailCompraProduto(usuario);//Enviar e-mail
				
				model.addFlashAttribute("sucesso",response);
				return new ModelAndView("redirect:/produtos");
			} catch (HttpClientErrorException e) {
				model.addFlashAttribute("falha","Valor maior que o permitido!");
				return new ModelAndView("redirect:/produtos");
			}
		};
	}


	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		
	    email.setSubject("Compra finalizada com sucesso!");
	    //email.setTo(usuario.getEmail());
	    email.setTo("murillopezzuol@hotmail.com");
	    email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
	    email.setFrom("murillo@pezzuol.com.br");

	    sender.send(email);
	}
	
}
