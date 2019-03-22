<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%><!-- chama URL -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Produtos</title>
<c:url value="/resources/css" var="cssPath" /> <!-- Resolve o problema do Link relativo -->
<link rel="stylesheet" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" href="${cssPath}/bootstrap-theme.min.css">
</head>
<body>

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
		
		
	<h2>Cadastro de Produtos</h2>
	<!-- INICIO FORM SPRING -->
	<form:form action="${s:mvcUrl('PC#gravar').build()}" method="post" modelAttribute="produto" enctype="multipart/form-data">
		<div class="form-group">
			<label>Título</label> 
			<form:input path="titulo" cssClass="form-control"/>
			<form:errors path="titulo"/>
		</div>
		<div class="form-group">
			<label>Descrição</label>
			<form:textarea path="descricao" rows="10" cols="20" cssClass="form-control"/>
			<form:errors path="descricao"/>
		</div>
		<div class="form-group">
			<label>Páginas</label> 
			<form:input path="paginas" cssClass="form-control"/>
			<form:errors path="paginas"/>
		</div>
		<div class="form-group">
		    <label>Data de Lançamento</label>
		    <form:input path="dataLancamento" cssClass="form-control"/>
		    <form:errors path="dataLancamento" />
		</div>
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label>${tipoPreco}</label> 
				<form:input path="precos[${status.index}].valor" cssClass="form-control"/>
				<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}"/>
			</div>
		</c:forEach>
		<div class="form-group">
		    <label>Sumário</label>
		    <input name="sumario" type="file" class="form-control"/>
		</div>
		<button type="submit" class="btn btn-primary">Cadastrar</button>
	</form:form>
	<!-- FIM FORM SPRING -->
	
	</div>
	
</body>
</html>