package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.Telefones;
import dao.DaoTelefones;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class TelefonesServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario dao = new DaoUsuario();

	private DaoTelefones daoTelefones = new DaoTelefones();

	public TelefonesServlets() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (user != null) {
				BeanCursoJsp usuario = dao.consultar(user);

				if (acao.equalsIgnoreCase("addFone")) {

					// Colocando usuário na Sessão
					request.getSession().setAttribute("userEscolhido", usuario);
					request.setAttribute("userEscolhido", usuario);
					RequestDispatcher tela = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefones.listar(usuario.getId()));
					request.setAttribute("msg", "Salvo com sucesso!");
					tela.forward(request, response);

				} else if (acao.equalsIgnoreCase("deleteFone")) {
					String foneId = request.getParameter("foneId");
					daoTelefones.delete(foneId);

					BeanCursoJsp jsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

					RequestDispatcher tela = request.getRequestDispatcher("/telefones.jsp");

					request.setAttribute("telefones", daoTelefones.listar(Long.parseLong(user)));
					request.setAttribute("msgRemovido", "Removido com sucesso!");
					tela.forward(request, response);
				}
			}else {
				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", dao.listar());
				tela.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			BeanCursoJsp bean = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");

			String acao = request.getParameter("acao");

			if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {

				if (numero == null || (numero != null && numero.isEmpty())) {
					RequestDispatcher tela = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefones.listar(bean.getId()));
					request.setAttribute("msgNumero", "Informe o número do telefone!");
					tela.forward(request, response);
				} else {

					Telefones telefones = new Telefones();
					telefones.setNumero(numero);
					telefones.setTipo(tipo);
					telefones.setUsuario(bean.getId());

					daoTelefones.salvarTelefones(telefones);

					request.getSession().setAttribute("userEscolhido", bean);
					request.setAttribute("userEscolhido", bean);

					RequestDispatcher tela = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefones.listar(bean.getId()));
					request.setAttribute("msgTelefone", "Salvo com sucesso!");
					tela.forward(request, response);
				}

			} else {
				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("usuarios", dao.listar());
				tela.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
