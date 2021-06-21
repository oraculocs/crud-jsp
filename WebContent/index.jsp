<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sistema de Cadastro de Usuários e Produtos</title>
<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>

	<div class="login-page">
		<center>
			<h1 style="color: #FFFFFF;">CRUD de Usuários e Produtos</h1>
		</center>

		<center>
			<h2 style="color: #FFFFFF;">Java Web: JSP + Servlet + JDBC</h2>
		</center>
		<center>
			<span><b style="color: #003300;">USUÁRIO: admin</b> <br /> <b
				style="color: #003300;">SENHA: admin</b></span>
		</center>
		<br />
		<div class="form">

			<form action="LoginServlet" method="post" class="login-form">

				<b>Login:</b> <input type="text" id="login" name="login"
					placeholder="Username"> <br /> <b>Senha: </b><input
					type="password" id="senha" name="senha" placeholder="Password">
				<br />

				<button type="submit" value="Logar">Logar</button>

			</form>
		</div>
		<center>
			<h3 style="color: #2F4F4F;">
				Sistema Desenvolvido por <a style="text-decoration-style: solid; color: white"
					href="https://oraculocs.github.io/#home" target="_blank">Carlos
					Augusto</a>
			</h3>
			<h3 style="color: #2F4F4F;">LinkedIn: <a style="text-decoration-style: solid; color: white"
					href="https://www.linkedin.com/in/carlos-augusto-47a1764a/" target="_blank">@Carlos</h3>
		</center>
	</div>


</body>
</html>