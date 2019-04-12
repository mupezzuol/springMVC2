package br.com.springMVC.controller.auth;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


//Essa tag faz com q seja monitorado todos os outros controllers da aplicação
//Ou seja, qnd acontecer uma Exception em qualquer Controller, irá ser tratado por essa classe/metodo
@ControllerAdvice
public class ExceptionHandlerController {
	
	//Todas as classe são tratadas por essa Exceptio, caso queria q seja diferente, é só implemtar esse método
	//na classe cuja Exception será diferente
    @ExceptionHandler(Exception.class)
    public ModelAndView trataDetalheNaoEcontrado(Exception exception){
    	ModelAndView mv = new ModelAndView("exception/error");//Página de erro
    	
    	System.out.println("Erro genérico acontecendo:");
        exception.printStackTrace();//Printa erro no console

        mv.addObject("exception", exception);//Atributo para mostrar na página o erro e analisar de lá
        return mv;
    }
    
    //Para testar, jogue essa Exception
    //if(true) throw new RuntimeException("Excessão Genérica Acontecendo!!!!");
    
    
    
    
    //FORMA DE MOSTRAR OS ERROS NAS PÁGINAS:
//    Mensagem: ${exception.message}
//    <c:forEach items="${exception.stackTrace}" var="stk">
//        ${stk}
//    </c:forEach>   
    
    
}
