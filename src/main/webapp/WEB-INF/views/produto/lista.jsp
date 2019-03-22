<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais - Casa do Código</title>
<c:url value="/resources/css" var="cssPath" />
<link rel="stylesheet" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" href="${cssPath}/bootstrap-theme.min.css">
</head>
<body>
	${sucesso}
	${falha}
	
	
	<div class="container"> 
		<!-- INICIO NAV BAR-->
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">SpringMVC2</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Alterna navegação">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarNav">
		    <ul class="navbar-nav">
		      <li class="nav-item">
		        <a class="nav-link" href="${s:mvcUrl('PC#listar').build()}">Lista de Produtos</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="${s:mvcUrl('PC#formProduto').build()}">Cadastro de Produtos</a>
		      </li>
		    </ul>
		  </div>
		</nav>
		<!-- FIM NAV BAR-->
		
		<h2>Lista de Produtos</h2>
		<!-- INICIO TABLE -->
		<table class="table table-bordered table-striped table-hover">
			<tr>
				<th>Título</th>
				<th>Descrição</th>
				<th>Páginas</th>
			</tr>
	
			<c:forEach items="${listProdutos}" var="produto">
				<tr>
					<td>
						<a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build()}">${produto.titulo}</a>
					</td>
					<td>${produto.descricao}</td>
					<td>${produto.paginas}</td>
				</tr>
			</c:forEach>
		</table>
		<!-- FIM TABLE -->
	</div>
	
</body>
</html>