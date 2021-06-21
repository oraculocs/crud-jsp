package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoLogin;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Objeto de acesso ao Banco de Dados
	private DaoLogin dao = new DaoLogin();

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			/*Criando uma inst�ncia da Classe que cont�m os atributos que ser�o resgatados pelo Servlet
			atrav�s da requisi��o enviado pelo usu�rio que digitou os dados na p�gina */
			BeanCursoJsp jsp = new BeanCursoJsp();

			//Resgatando os par�metros e colocando dentro de uma vari�vel local
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			//Condi��o para o usu�rio conseguir se logar ou n�o (Verifica��o)
			//Se o login e a senha forem diferentes de nulo ou vazios essa condi��o ser� atendida e entrar� nela
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				//Essa verifica��o ele vai no m�todo que valida o login e retorna para o Servlet se o usu�rio pode acessar a aplica��o ou n�o
				if (dao.validarLogin(login, senha)) {// Acertou o Login e Senha
					RequestDispatcher dispatcher = request.getRequestDispatcher("acessoliberado.jsp");
					dispatcher.forward(request, response);
				} else {// Errou Login e Senha
					RequestDispatcher dispatcher = request.getRequestDispatcher("acessonegado.jsp");
					dispatcher.forward(request, response);
				}
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
