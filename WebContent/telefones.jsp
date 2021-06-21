<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Página de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

	<a href="acessoliberado.jsp"><img src="resources/img/inicio.jpg"
		title="Início" width="60px" height="60px"></a>
	<a href="index.jsp"><img src="resources/img/sair.jpg" title="Sair"
		width="60px" height="60px"></a>
	<h1 align="center" style="color: #4682B4">Cadastro de Telefones:</h1>
	
	<h3 align="center" style="color: #000000;">${msgTelefone}</h3>
	<h3 align="center" style="color: #000000;">${msgRemovido}</h3>
	<h3 align="center" style="color: orange;">${msgNumero}</h3>
	
	

	<form action="salvarTelefones" method="post" id="formUser">

		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Usuário:</td>
						<td><input type="text" readonly="readonly" id="user" name="user"
							class="field-long" value="${userEscolhido.id}"></td>

						<td>Nome:</td>
						<td><input type="text" readonly="readonly" id="nome"
							name="nome" value="${userEscolhido.nome}"></td>

					</tr>



					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero">
						<td>Tipo:</td>
						<!-- <td><input type="text" id="tipo" name="tipo"> -->
						<td><select id="tipo" name="tipo" style="width: 172px">
								<option>Casa</option>
								<option>Contato</option>
								<option>Celular</option>
								<option>Recado</option>
								<option>Outros</option>

						</select></td>

					</tr>


					<tr>
						<td></td>
						<td><input type="submit" value="Salvar">
						<input type="submit" value="Voltar" onclick="document.getElementById('formUser').action = 'salvarTelefones?acao=voltar'">
						</td>

						
					</tr>

					<!-- <tr> -->
					<!-- <input type="submit"style="border-radius: 20px; color: black; background-color: silver; value="Salvar" > -->
					<!-- <td></td> -->
					<!-- <td><button type="submit" style="border-radius: 50px; color: black; width: 50%; background-color: silver; color: #FFFFFF; font-family: Verdana, Geneva, Arial, helvetica, sans-serif;"><b>Salvar</b></button></td> -->

					<!-- </tr> -->
				</table>

			</li>
		</ul>

	</form>


	<br />
	<h1 align="center"  style="color: #4682B4">Lista de Telefones Cadastrados</h1>
	<div class="container">
		<table class="responsive-table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Número</th>
					<th>Tipo</th>
					<th>Excluir</th>
				</tr>
			</thead>

			<c:forEach items="${telefones}" var="fone">
				<tr>
					<td><c:out value="${fone.id}"></c:out></td>
					<td><c:out value="${fone.numero}"></c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>


					<td><a href="salvarTelefones?user=${fone.usuario}&acao=deleteFone&foneId=${fone.id}" onclick="return confirm('Confirmar a exclusão?')"><img
							src="resources/img/excluir.png" alt="Excluir" title="Excluir"
							width="20px" height="20px"></a></td>

				</tr>

			</c:forEach>


		</table>
	</div>

</body>
</html>