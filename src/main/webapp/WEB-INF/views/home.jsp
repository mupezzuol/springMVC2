<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home com SpringMVC</title>
</head>
<body>
	<h1>HOME - Spring MVC</h1>
	</br>
	
	<h1>PRODUTOS</h1>
	<a href='<c:url value="/produtos"/>'>Produtos</a> <!-- Link com Spring usamos a taglib -->
	
	
	
	<h1>Anotações</h1>
	<h4>A pasta WEB-INF -> Faz parte da especificação de Servlets JSP</h4>
	
</body>
</html>