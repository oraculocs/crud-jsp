<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	

<!-- Para formatação de números -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<script src="resources/javascript/jquery.maskMoney.min.js" type="text/javascript"></script>
<title>Página de Cadastro de Produtos</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

<!-- Validações JavaScript  -->
<script type="text/javascript">
function validarCamposProduto() {
	if (document.getElementById("nome").value == '') {
		alert('Informe o Nome do Produto');
		return false;
	} else if (document.getElementById("quantidade").value == '') {
		alert('Informe a Quantidade');
		return false;
	} else if (document.getElementById("valor").value == '') {
		alert('Informe o Valor do Produto');
		return false;
	}
	
	return true;
}

</script>

<script type="text/javascript">
$(function(){
	$('#valor').maskMoney();
})


</script>

</head>
<body>
<a href="acessoliberado.jsp"><img src="resources/img/inicio.jpg" title="Início" width="60px" height="60px"></a>
<a href="index.jsp"><img src="resources/img/sair.jpg" title="Sair" width="60px" height="60px"></a>

	<h1 align="center" style="color: #4682B4">Cadastro de Produtos:</h1>
	
	<h3 align="center" style="color: #000000;">${msgNomeObrigatorio}</h3>
	<h3 align="center" style="color: #000000;">${msgQtdObrigatorio}</h3>
	<h3 align="center" style="color: #000000;">${msgValorObrigatorio}</h3>

	<form action="gravarProduto" method="post" id="formProduto" onsubmit="return validarCamposProduto()? true : false">

		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><b>Código:</b></td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${prod.id}" class="field-produto"></td>
					</tr>

					<tr>
						<td><b>Nome:</b></td>
						<td><input type="text" id="nome" name="nome"
							 value="${prod.nome}" class="field-produto" maxlength="50"></td>
					</tr>

					<tr>
						<td><b>Quantidade:</b></td>
						<td><input type="number" id="quantidade" name="quantidade"
							 value="${prod.quantidade}" class="field-produto" maxlength="5" size="5" min="1" max="999.99"></td>
					</tr>
					
					<tr>
						<td><b>Valor R$:</b></td>
						<td><input type="text" id="valor" name="valor" 
							 value="${prod.valorEmTexto}" data-thousands="." data-decimal="," data-precision="2" class="field-produto" maxlength="7"></td>
					</tr>
					
					<tr>
						<td><b>Categoria</b></td>
						<td><select id="categorias" name="categoria_id" style="width: 200px;">
							<c:forEach items="${categorias}" var="cat">
								<option value="${cat.id}" id="${cat.id}"
									<c:if test="${cat.id == prod.categoria_id}">
										<c:out value="selected=selected" />
									</c:if>
								>
									${cat.nome}
								</option>
							</c:forEach>
						
						</select></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> 
						<input type="submit" value="Cancelar" onclick="document.getElementById('formProduto').action = 'gravarProduto?acao=reset'">
						</td>

					</tr>

				</table>
			</li>
		</ul>

	</form>
	
	<br />
	<h1 align="center" style="color: #4682B4">Lista de Produtos</h1>
	<!-- <table border="2" align=center width=30% heigth=20% style="border-style: outset; text-align: center; background-color: #728DCF; color: #FFFFFF; font-family: Verdana, Geneva, Arial, helvetica, sans-serif;"> -->
	<div class="container">
		<table class="responsive-table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nome</th>
					<th>Quantidade</th>
					<th>Valor</th>
					<th>Categoria</th>
					<th>Excluir</th>
					<th>Editar</th>
				</tr>
			</thead>

			<c:forEach items="${produtos}" var="prod">
				<tr>
					<td><c:out value="${prod.id}"></c:out></td>
					<td><c:out value="${prod.nome}"></c:out></td>
					<td><c:out value="${prod.quantidade}"></c:out></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${prod.valor}" /></td>
					<td><c:out value="${prod.categoria_id}"></c:out></td>

					<td><a href="gravarProduto?acao=delete&prod=${prod.id}" onclick="return confirm('Confirmar a exclusão?')"><img src="resources/img/excluir.png" alt="Excluir" title="Excluir" width="20px" height="20px"></a></td>
					<td><a href="gravarProduto?acao=editar&prod=${prod.id}"><img src="resources/img/editar1.jpg" alt="Editar" title="Editar" width="20px" height="20px"></a></td>
				</tr>

			</c:forEach>


		</table>
		<a href="acessoliberado.jsp"><img src="resources/img/voltar1.png" alt="Voltar" title="Voltar" width="60px" height="60px"/>
	</div>
	
	

</body>
</html>