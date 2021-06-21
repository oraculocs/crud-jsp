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
			
			/*Criando uma instância da Classe que contém os atributos que serão resgatados pelo Servlet
			através da requisição enviado pelo usuário que digitou os dados na página */
			BeanCursoJsp jsp = new BeanCursoJsp();

			//Resgatando os parâmetros e colocando dentro de uma variável local
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			//Condição para o usuário conseguir se logar ou não (Verificação)
			//Se o login e a senha forem diferentes de nulo ou vazios essa condição será atendida e entrará nela
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				//Essa verificação ele vai no método que valida o login e retorna para o Servlet se o usuário pode acessar a aplicação ou não
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
