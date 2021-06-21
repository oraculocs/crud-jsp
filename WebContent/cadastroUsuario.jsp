<%@page import="beans.BeanCursoJsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- Adicionando JQuery para consumir o WebService do Via CEP -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous">
	
</script>

<title>Página de Cadastro de Usuários</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

<!-- Validações JavaScript  -->
<script type="text/javascript">
	function validarCampos() {
		if (document.getElementById("login").value == '') {
			alert('Informe o Login');
			return false;
		} else if (document.getElementById("senha").value == '') {
			alert('Informe a Senha');
			return false;
		} else if (document.getElementById("nome").value == '') {
			alert('Informe o Nome');
			return false;
		} else if (document.getElementById("telefone").value == '') {
			alert('Informe o Telefone');
			return false;
		}

		return true;
	}

	function consultaCep() {
		var cep = $("#cep").val();

		//Consulta o webservice viacep.com.br/
		$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
				function(dados) {

					if (!("erro" in dados)) {
						//Atualiza os campos com os valores da consulta.
						$("#rua").val(dados.logradouro);
						$("#bairro").val(dados.bairro);
						$("#cidade").val(dados.localidade);
						$("#estado").val(dados.uf);
						$("#ibge").val(dados.ibge);
					} //end if.
					else {
						$("#cep").val('');
						$("#rua").val('');
						$("#bairro").val('');
						$("#cidade").val('');
						$("#estado").val('');
						$("#ibge").val('');
						//CEP pesquisado não foi encontrado.
						alert("CEP não encontrado.");
					}
				});
	}
</script>

</head>
<body>
	
	<!-- Ícones do Topo da Página -->
	<a href="acessoliberado.jsp"><img src="resources/img/inicio.jpg" title="Início" width="60px" height="60px"></a>
	<a href="index.jsp"><img src="resources/img/sair.jpg" title="Sair" width="60px" height="60px"></a>
	<h1 align="center" style="color: #4682B4">Cadastro de Novo Usuário:</h1>

	<!-- Área de mensagens -->
	<h3 align="center" style="color: red;">${msgNok}</h3>
	<h3 align="center" style="color: green;">${msgOk}</h3>
	<h3 align="center" style="color: #EB8305;">${msgErroAtualizar}</h3>
	<h3 align="center" style="color: #1A1AF7;">${msgSenha}</h3>
	<h3 align="center" style="color: #000000;">${msgAtualizar}</h3>
	<h3 align="center" style="color: #000000;">${msgLoginObrigatorio}</h3>
	<h3 align="center" style="color: #000000;">${msgSenhaObrigatorio}</h3>
	<h3 align="center" style="color: #000000;">${msgNomeObrigatorio}</h3>
	<h3 align="center" style="color: #000000;">${msgTelObrigatorio}</h3>


	<form action="salvarUsuario" method="post" id="formUser" onsubmit="return validarCampos()? true : false" enctype="multipart/form-data" style="width: 70%">
		

		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><b>Código:</b></td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}" class="field-long"></td>

						<td><b>CEP:</b></td>
						<td><input type="text" id="cep" name="cep" onblur="consultaCep()" value="${user.cep}" placeholder="Digite o CEP" maxlength="9" class="field-longao"></td>
					</tr>

					<tr>
						<td><b>Login:</b></td>
						<td><input type="text" id="login" name="login" value="${user.login}" class="field-long" placeholder="Digite seu Login" maxlength="10"></td>

						<td><b>Rua:</b></td>
						<td><input type="text" id="rua" name="rua" value="${user.rua}" placeholder="Nome da Rua" maxlength="50" class="field-longao"></td>
					</tr>

					<tr>
						<td><b>Senha:</b></td>
						<td><input type="password" id="senha" name="senha" value="${user.senha}" placeholder="Digite sua Senha" class="field-long" maxlength="10"></td>

						<td><b>Bairro:</b></td>
						<td><input type="text" id="bairro" name="bairro" value="${user.bairro}" placeholder="Nome do Bairro" maxlength="50" class="field-longao"></td>
					</tr>

					<tr>
						<td><b>Nome:</b></td>
						<td><input type="text" id="nome" name="nome" value="${user.nome}" class="field-long" placeholder="Digite seu Nome" maxlength="50"></td>

						<td><b>Cidade:</b></td>
						<td><input type="text" id="cidade" name="cidade" value="${user.cidade}" placeholder="Nome da Cidade" maxlength="50" class="field-longao"></td>
					</tr>

					<tr>

						<td><b>IBGE:</b></td>
						<td><input type="text" id="ibge" name="ibge" value="${user.ibge}" placeholder="Nº do IBGE" class="field-long" maxlength="20"></td>

						<td><b>Estado:</b></td>
						<td><input type="text" id="estado" name="estado" value="${user.estado}" placeholder="Nome do Estado" class="field-longao"></td>
					</tr>


					<tr>
						<td><b>Perfil:</b></td>
						<td><select id="perfil" name="perfil" style="width: 200px">


								<option value="nao_informado">--SELECIONE--</option>


								<option value="administrador"
									<%if (request.getAttribute("user") != null) {
				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("administrador")) {
					out.print(" ");
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>Administrador</option>


								<option value="secretario"
									<%if (request.getAttribute("user") != null) {
				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("secretario")) {
					out.print(" ");
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>Secretário</option>


								<option value="gerente"
									<%if (request.getAttribute("user") != null) {
				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("gerente")) {
					out.print(" ");
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>Gerente</option>


								<option value="funcionario"
									<%if (request.getAttribute("user") != null) {
				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("funcionario")) {
					out.print(" ");
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>Funcionário</option>
						</select></td>

						<td>Ativo:</td>
						<td><input type="checkbox" id="ativo" name="ativo"
							<%if (request.getAttribute("user") != null) {
				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.isAtivo()) {
					out.print(" ");
					out.print("checked=\"checked\"");
					out.print(" ");
				}
			}%>>

						</td>


					</tr>

					<tr>

						<td><b>Sexo:</b></td>
						<td><input type="radio" name="sexo"
							<%if (request.getAttribute("user") != null) {
				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getSexo().equalsIgnoreCase("masculino")) {
					out.print(" ");
					out.print("checked=\"checked\"");
					out.print(" ");
				}
			}%>
							value="Masculino">Masculino</input> <input type="radio"
							name="sexo"
							<%if (request.getAttribute("user") != null) {
				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getSexo().equalsIgnoreCase("feminino")) {
					out.print(" ");
					out.print("checked=\"checked\"");
					out.print(" ");
				}
			}%>
							value="Feminino">Feminino</input></td>
					</tr>




					<tr>
						<td><b>Foto:</b></td>
						<td><input type="file" name="foto">
					</tr>

					<tr>
						<td><b>Currículo:</b></td>
						<td><input type="file" name="curriculo" value="curriculo">
					</tr>



					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'">
						</td>


					</tr>

				

				</table>

			</li>
		</ul>

	</form>


	<form method="post" action="servletPesquisa" style="width: 47%">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><b>Nome:</b></td>
						<td><input type="text" id="descricaoconsulta"
							name="descricaoconsulta" placeholder="Nome do Usuário"
							class="field-pesquisa"></td>
						<td><input type="submit" value="Pesquisar"
							class="field-pesquisar"></td>
					</tr>

				</table>
			</li>
		</ul>
	</form>

	<h1 align="center" style="color: #4682B4">Lista de Usuários
		Cadastrados</h1>
	<!-- <table border="2" align=center width=30% heigth=20% style="border-style: outset; text-align: center; background-color: #728DCF; color: #FFFFFF; font-family: Verdana, Geneva, Arial, helvetica, sans-serif;"> -->
	<div class="container">
		<table class="responsive-table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Imagem</th>
					<th>Currículo</th>
					<th>Login</th>
					<th>Nome</th>
					<th>Sexo</th>
					<th>Perfil</th>
					<th>Telefones</th>
					<th>Excluir</th>
					<th>Editar</th>

				</tr>
			</thead>

			<c:forEach items="${usuarios}" var="user">
				<tr>

					<td><c:out value="${user.id}"></c:out></td>

					<!-- Foto -->
					<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
						<td><a
							href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
								src='<c:out value="${user.fotoBase64Miniatura}" />' width="32px"
								height="32px" alt="ImagemUser" title="Imagem User" /></a></td>
					</c:if>

					<c:if test="${user.fotoBase64Miniatura == null}">
						<td><img alt="Imagem User" src="resources/img/branco.png"
							width="32px" height="32px" onclick="alert('Não posssui foto')"></td>
					</c:if>

					<!-- Currículo -->
					<c:if test="${user.curriculoBase64.isEmpty() == false}">
						<td><a
							href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img
								alt="Curriculo" src="resources/img/pdf.jpg" width="32px"
								height="32px"></a></td>
					</c:if>

					<c:if test="${user.curriculoBase64.isEmpty() == null}">
						<td><img alt="Curriculo" src="resources/img/pdf.jpg"
							width="32px" height="32px" onclick="alert('Não tem Curriculo')"></td>
					</c:if>


					<td><c:out value="${user.login}"></c:out></td>
					<%-- <td><c:out value="${user.senha}"></c:out></td> --%>
					<td><c:out value="${user.nome}"></c:out></td>
					<%-- <td><c:out value="${user.telefone}"></c:out></td> --%>

					<td><c:out value="${user.sexo}"></c:out></td>
					<td><c:out value="${user.perfil}"></c:out></td>

					<td><a href="salvarTelefones?acao=addFone&user=${user.id}"><img
							src="resources/img/tel1.jpg" alt="Telefones" title="Telefones"
							width="20px" height="20px"></a></td>

					<td><a href="salvarUsuario?acao=delete&user=${user.id}"
						onclick="return confirm('Confirmar a exclusão?')"><img
							src="resources/img/excluir.png" alt="Excluir" title="Excluir"
							width="20px" height="20px"></a></td>

					<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img
							src="resources/img/editar1.jpg" alt="Editar" title="Editar"
							width="20px" height="20px"></a></td>


				</tr>

			</c:forEach>

		</table>
		<a href="acessoliberado.jsp"><img src="resources/img/voltar1.png" alt="Voltar" title="Voltar" width="60px" height="60px"/>
	</div>
	
	
</body>
</html>