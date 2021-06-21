<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page" />
<jsp:useBean id="novoproduto" class="beans.Produto" type="beans.Produto"
	scope="page" />

<html>
<body>

	<h1 align=center width=50% heigth=20%
		style="text-align: center; background-color: #728DCF; color: #FFFFFF; font-family: Verdana, Geneva, Arial, helvetica, sans-serif;">
		Bem Vindo ao Sistema - JSP</h1>

		<table align=center style="padding-top: 2%;">
			<tr>
				<th><h3 style="color: #191970;">Cadastro de Usuário</h3></th>
				<th><h3 style="color: #191970;">Cadastro de Produtos</h3></th>
			</tr>
			<tr>
			<td><a href="salvarUsuario?acao=listartodos"><img
					src="resources/img/usu.jpg" width="200px" height="200px"
					title="Cadastro de Usuários"></a></td>

			<td><a href="gravarProduto?acao=listartodos"><img
					src="resources/img/produto.jpg" width="200px" height="200px"
					title="Cadastro de Produtos"></a></td>
			
			</tr>
		</table>


</body>
</html>