<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<c:url value="/resources/css" var="cssPath" /> <!-- Resolve o problema do Link relativo -->
<link rel="stylesheet" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" href="${cssPath}/bootstrap-theme.min.css">
</head>
<body>
	
	<div class="container">
        <h1>Login - Spring Security</h1>
        
        <!-- INICIO FORM LOGIN SPRING -->
        <!-- Precisa ser esse padrão, chamar '/login', 'username', 'password' etc.. -->
        <form:form servletRelativeAction="/login" method="post">
            <div class="form-group">
                <label>Nome</label>
                <input type="text" name="username" class="form-control" />
            </div>
            <div class="form-group">
                <label>Senha</label>
                <input type="password" name="password" class="form-control" />
            </div>
            <button type="submit" class="btn btn-primary">Logar</button>
        </form:form>
        <!-- FIM FORM LOGIN SPRING -->
        
    </div>
	
</body>
</html>