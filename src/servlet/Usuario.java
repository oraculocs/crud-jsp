package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

/**
 * Servlet implementation class Usuario
 */
@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoUsuario dao = new DaoUsuario();

	public Usuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");

			String user = request.getParameter("user");

			if (acao != null && acao.equalsIgnoreCase("delete") && user != null) {
				dao.delete(user);
				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("usuarios", dao.listar());
				tela.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("editar")) {
				BeanCursoJsp jsp = dao.consultar(user);

				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("user", jsp);
				tela.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("usuarios", dao.listar());
				tela.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("download")) {
				BeanCursoJsp usuario = dao.consultar(user);
				if (usuario != null) {

					String contentType = "";
					byte[] fileBytes = null;

					String tipo = request.getParameter("tipo");

					if (tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						// Converte a base64 da imagem do banco para byte[]
						fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());
					} else if (tipo.equalsIgnoreCase("curriculo")) {
						contentType = usuario.getContentTypeCurriculo();
						// Converte a base64 da imagem do banco para byte[]
						fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
					}

					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					// Coloca os bytes em um objeto de entrada para processar
					InputStream is = new ByteArrayInputStream(fileBytes);

					/* Início da resposta para o navegador */
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}

					os.flush();
					os.close();
				}
			}else {
				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("usuarios", dao.listar());
				tela.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean podeInserir = true;

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("usuarios", dao.listar());
				tela.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			// Recupera os parâmetros que vieram da Tela ou Requisição
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			//String telefone = request.getParameter("telefone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");
			
			//String on = request.getParameter("ativo");

			BeanCursoJsp usuario = new BeanCursoJsp();

			// Coloca dentro do objeto usuario
			usuario.setId(!id.isEmpty() ? Long.valueOf(id) : null);
			// usuario.setId(Long.valueOf(id));
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			//usuario.setTelefone(telefone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);
			usuario.setSexo(sexo);
			usuario.setPerfil(perfil);
			
			if(request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {
				usuario.setAtivo(true);
			}else {
				usuario.setAtivo(false);
			}

			try {

				// File Upload de imagens e PDF//
				if (ServletFileUpload.isMultipartContent(request)) {

					Part imagemFoto = request.getPart("foto");
					
						if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
							
							byte[] bytesImagem = converteStreamParaByte(imagemFoto.getInputStream());

							String fotoBase64 = new Base64()
									.encodeBase64String(bytesImagem);

							usuario.setFotoBase64(fotoBase64);
							usuario.setContentType(imagemFoto.getContentType());
							
							/*Inicio miniatura imagem */
							
							//Decodificar a imagem
							byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
							
							/*Transformar em um buffereimage para trabalhar com imagem*/
							BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
							
							//Pegar o tipo da imagem
							int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB: bufferedImage.getType();
							
							//Cria imagem em miniatura
							BufferedImage resizedImage = new BufferedImage(100, 100, type);
							Graphics2D g = resizedImage.createGraphics();
							g.drawImage(bufferedImage, 0, 0, 100, 100, null);
							g.dispose();
							
							/*Escrever imagem novamente */
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ImageIO.write(resizedImage, "png", baos);
							
							
							String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
							/*Fim miniatura imagem */
							usuario.setFotoBase64Miniatura(miniaturaBase64);
							
						
					}else {
						usuario.setAtualizarImage(false);;
					}

					/* Processa PDF */
					Part curriculoPdf = request.getPart("curriculo");

						if (curriculoPdf != null &&curriculoPdf.getInputStream().available() > 0) {
							String curriculoBase64 = new Base64()
									.encodeBase64String(converteStreamParaByte(curriculoPdf.getInputStream()));

							usuario.setCurriculoBase64(curriculoBase64);
							usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
					}else {
						usuario.setAtualizarPdf(false);;
					}

//					List<FileItem> fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
//					
//					for (FileItem fileItem : fileItems) {
//						if(fileItem.getFieldName().equals("foto")) {
//							
//							String fotoBase64 = new Base64().encodeBase64String(fileItem.get());
//							String contentType = fileItem.getContentType();
//							usuario.setFotoBase64(fotoBase64);
//							usuario.setContentType(contentType);
//						}
//					}
				}
				/* FIM File upload de imagens e PDF */

				
				//Validações no BackEnd
				if (login == null || login.isEmpty()) {
					podeInserir = false;
					request.setAttribute("msgLoginObrigatorio", "Login deve ser informado!");
				} else if (senha == null || senha.isEmpty()) {
					podeInserir = false;
					request.setAttribute("msgSenhaObrigatorio", "Senha deve ser informado!");
				} else if (nome == null || nome.isEmpty()) {
					podeInserir = false;
					request.setAttribute("msgNomeObrigatorio", "Nome deve ser informado!");
//				} else if (telefone == null || telefone.isEmpty()) {
//					podeInserir = false;
//					request.setAttribute("msgTelObrigatorio", "Telefone deve ser informado!");
				} else if (id == null || id.isEmpty() && !dao.validarLogin(login)) {
					request.setAttribute("msgNok", "Usuário já cadastrado no Sistema!");
					podeInserir = false;
				} else if (id == null || id.isEmpty() && !dao.validarSenha(senha)) {
					request.setAttribute("msgSenha", "Senha já existe no Sistema, favor digite outra senha...");
					podeInserir = false;
				}

				else if (id == null || id.isEmpty() && dao.validarLogin(login) && podeInserir) {
					// E chama o método salvar passando o objeto como parâmetro
					dao.salvar(usuario);
					request.setAttribute("msgOk", "Usuário cadastrado no Sistema com sucesso!");
				} else if (id != null || !id.isEmpty()) {

					// VERIFICAR ESSE IF condicional incorreta
					if (!dao.validarLoginUpdate(login, id)) {
						request.setAttribute("msgErroAtualizar", "Login já existe para outro usuário!");
						podeInserir = false;
					} else if (id != null && !id.isEmpty() && podeInserir) {
						dao.atualizar(usuario);
						request.setAttribute("msgAtualizar", "Usuário alterado com sucesso!");
					}

				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {

				if (!podeInserir) {
					request.setAttribute("user", usuario);
				}

				RequestDispatcher tela = request.getRequestDispatcher("/cadastroUsuario.jsp");
				// Carrega a Lista de usuários e coloca dentro da variável usuarios
				request.setAttribute("usuarios", dao.listar());
				tela.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* Converte a entrada de fluxo de dados da imagem para byte[] */
	private byte[] converteStreamParaByte(InputStream imagem) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}

		return baos.toByteArray();
	}
}
