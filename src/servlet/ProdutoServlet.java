package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.Produto;
import dao.DaoProduto;


@WebServlet("/gravarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoProduto daoProduto = new DaoProduto();
       
  
    public ProdutoServlet() {
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listartodos";

			String prod = request.getParameter("prod");

			RequestDispatcher tela = request.getRequestDispatcher("/cadastroProduto.jsp");
			
			
			if (acao.equalsIgnoreCase("delete")) {
				daoProduto.delete(prod);
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("produtos", daoProduto.listar());
			} else if (acao.equalsIgnoreCase("editar")) {
				Produto produto = daoProduto.consultar(prod);
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("prod", produto);
			} else if (acao.equalsIgnoreCase("listartodos")) {
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("produtos", daoProduto.listar());
			}
			
			request.setAttribute("categorias", daoProduto.listaCategorias());
			tela.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean podeInserir = true;
		
		String acao = request.getParameter("acao");
		
		
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher tela = request.getRequestDispatcher("/cadastroProduto.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("categorias", daoProduto.listaCategorias());
				tela.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
				String id = request.getParameter("id");
				String nome = request.getParameter("nome");
				String quantidade = request.getParameter("quantidade");
				String valor = request.getParameter("valor");
				String categoria = request.getParameter("categoria_id");
				
				Produto produto = new Produto();
				produto.setId(!id.isEmpty() ? Long.valueOf(id) : 0);
				produto.setNome(nome);
				produto.setCategoria_id(Long.parseLong(categoria));
				
				if(quantidade != null && !quantidade.isEmpty()) {
					produto.setQuantidade(Integer.valueOf(quantidade));
				}
				if(valor != null && !valor.isEmpty()) {
					String valorProduto = valor.replaceAll("\\.", "");
					valorProduto = valorProduto.replaceAll("\\,", ".");
					produto.setValor(Double.parseDouble(valorProduto));
				}
				
				if (nome == null || nome.isEmpty()) {
					podeInserir = false;
					request.setAttribute("msgNomeObrigatorio", "Nome deve ser informado!");
				} else if(quantidade == null || quantidade.isEmpty()) {
					podeInserir = false;
					request.setAttribute("msgQtdObrigatorio", "Quantidade deve ser informado!");
				}else if(valor == null || valor.isEmpty()) {
					podeInserir = false;
					request.setAttribute("msgValorObrigatorio", "Valor deve ser informado!");
				}else if(id == null || id.isEmpty()) {
					daoProduto.salvarProduto(produto);
				}else {
					daoProduto.atualizar(produto);
				}
				
				try {
					RequestDispatcher tela = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("produtos", daoProduto.listar());
					request.setAttribute("categorias", daoProduto.listaCategorias());
					tela.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}

}
