package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {

	private Connection con;

	public DaoUsuario() {
		// Conexão estabelecida para o Sistema
		con = SingleConnection.getConnection();
	}

	// Método para Gravar um Usuário na página JSP e gravar no Banco
	public void salvar(BeanCursoJsp usuario) {
		try {
			String sql = "insert into usuario(login, senha, nome, telefone, cep, rua, bairro, cidade, estado, ibge, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura, ativo, sexo, perfil) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getNome());
			stmt.setString(4, usuario.getTelefone());
			stmt.setString(5, usuario.getCep());
			stmt.setString(6, usuario.getRua());
			stmt.setString(7, usuario.getBairro());
			stmt.setString(8, usuario.getCidade());
			stmt.setString(9, usuario.getEstado());
			stmt.setString(10, usuario.getIbge());
			stmt.setString(11, usuario.getFotoBase64());
			stmt.setString(12, usuario.getContentType());
			stmt.setString(13, usuario.getCurriculoBase64());
			stmt.setString(14, usuario.getContentTypeCurriculo());
			stmt.setString(15, usuario.getFotoBase64Miniatura());
			stmt.setBoolean(16, usuario.isAtivo());
			stmt.setString(17, usuario.getSexo());
			stmt.setString(18, usuario.getPerfil());

			stmt.execute();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/*
	 * Método Responsável por Listar todos os usuários do sistema na Tela
	 */
	 
	public List<BeanCursoJsp> listar() throws Exception {


		String sql = "select * from usuario where login <> 'admin'";
		return consultarUsuarios(sql);
	}

	private List<BeanCursoJsp> consultarUsuarios(String sql) throws SQLException {
		List<BeanCursoJsp> lista = new ArrayList<BeanCursoJsp>();
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			BeanCursoJsp bean = new BeanCursoJsp();
			bean.setId(rs.getLong("id"));
			bean.setLogin(rs.getString("login"));
			bean.setSenha(rs.getString("senha"));
			bean.setNome(rs.getString("nome"));
			bean.setTelefone(rs.getString("telefone"));
			bean.setCep(rs.getString("cep"));
			bean.setRua(rs.getString("rua"));
			bean.setBairro(rs.getString("bairro"));
			bean.setCidade(rs.getString("cidade"));
			bean.setEstado(rs.getString("estado"));
			bean.setIbge(rs.getString("ibge"));
			bean.setContentType(rs.getString("contenttype"));
			// bean.setFotoBase64(rs.getString("fotobase64"));
			bean.setFotoBase64Miniatura(rs.getString("fotobase64miniatura"));
			bean.setCurriculoBase64(rs.getString("curriculobase64"));
			bean.setContentTypeCurriculo(rs.getString("contenttypecurriculo"));
			bean.setAtivo(rs.getBoolean("ativo"));
			bean.setSexo(rs.getString("sexo"));
			bean.setPerfil(rs.getString("perfil"));

			lista.add(bean);
		}
		
		return lista;
	}
	
	public List<BeanCursoJsp> listar(String descricaoconsulta) throws SQLException{
		String sql = "select * from usuario where login <> 'admin' and nome ilike '%"+ descricaoconsulta +"%'";
		return consultarUsuarios(sql);
	}

	public void delete(String id) {
		try {
			String sql = "delete from usuario where id = '" + id + "' and login <> 'admin'";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public BeanCursoJsp consultar(String id) throws Exception {

		String sql = "select * from usuario where id = '" + id + "' and login <> 'admin'";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			BeanCursoJsp beanCurso = new BeanCursoJsp();
			beanCurso.setId(rs.getLong("id"));
			beanCurso.setLogin(rs.getString("login"));
			beanCurso.setSenha(rs.getString("senha"));
			beanCurso.setNome(rs.getString("nome"));
			beanCurso.setTelefone(rs.getString("telefone"));
			beanCurso.setCep(rs.getString("cep"));
			beanCurso.setRua(rs.getString("rua"));
			beanCurso.setBairro(rs.getString("bairro"));
			beanCurso.setCidade(rs.getString("cidade"));
			beanCurso.setEstado(rs.getString("estado"));
			beanCurso.setIbge(rs.getString("ibge"));
			beanCurso.setFotoBase64(rs.getString("fotobase64"));
			beanCurso.setFotoBase64Miniatura(rs.getString("fotobase64miniatura"));
			beanCurso.setContentType(rs.getString("contenttype"));
			beanCurso.setCurriculoBase64(rs.getString("curriculobase64"));
			beanCurso.setContentTypeCurriculo(rs.getString("contenttypecurriculo"));
			beanCurso.setAtivo(rs.getBoolean("ativo"));
			beanCurso.setSexo(rs.getString("sexo"));
			beanCurso.setPerfil(rs.getString("perfil"));

			return beanCurso;
		}
		return null;
	}

	// Validação de Login caso haja no Sistema
	public boolean validarLogin(String login) throws Exception {

		String sql = "select count(1) as qtd from usuario where login = '" + login + "'";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {

			return rs.getInt("qtd") <= 0; // Se a consulta for menor ou igual a 0 return true e deixa cadastrar esse
											// login
		}
		return false;
	}

	public boolean validarSenha(String senha) throws Exception {

		String sql = "select count(1) as qtd from usuario where senha = '" + senha + "'";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {

			return rs.getInt("qtd") <= 0; // Se a consulta for menor ou igual a 0 return true e deixa cadastrar esse
											// login
		}
		return false;
	}

	// Validação para Tentativa de Atualização de Login já existente.
	public boolean validarLoginUpdate(String login, String id) throws Exception {

		String sql = "select count(1) as qtde from usuario where login = '" + login + "' and id <> " + id;
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {

			return rs.getInt("qtde") <= 0; // Se a consulta for menor ou igual a 0 return true e deixa cadastrar esse
											// login
		}
		return false;
	}

	public void atualizar(BeanCursoJsp usuario) {
		try {
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" update usuario set login = ?, senha = ?, nome = ?, telefone = ");
			sql.append(" ?, cep = ?, rua = ?, bairro = ?, cidade = ? ");
			sql.append(", estado = ?, ibge = ?, ativo = ?, sexo = ?, perfil = ? ");
					
			if(usuario.isAtualizarImage()) {
				sql.append(", fotobase64 = ?, contenttype = ? ");
			}
			
			if(usuario.isAtualizarPdf()) {
				sql.append(", curriculobase64 = ?, contenttypecurriculo = ? ");
			}
			
			
			if(usuario.isAtualizarImage()) {
				sql.append(", fotoBase64Miniatura = ? ");
			}
			
			sql.append(" where id = " + usuario.getId());

			PreparedStatement stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getNome());
			stmt.setString(4, usuario.getTelefone());
			stmt.setString(5, usuario.getCep());
			stmt.setString(6, usuario.getRua());
			stmt.setString(7, usuario.getBairro());
			stmt.setString(8, usuario.getCidade());
			stmt.setString(9, usuario.getEstado());
			stmt.setString(10, usuario.getIbge());
			stmt.setBoolean(11, usuario.isAtivo());
			stmt.setString(12, usuario.getSexo());
			stmt.setString(13, usuario.getPerfil());
			
			if(usuario.isAtualizarImage()) {
				stmt.setString(14, usuario.getFotoBase64());
				stmt.setString(15, usuario.getContentType());
			}
			
			if(usuario.isAtualizarPdf()) {
				if(usuario.isAtualizarPdf() && !usuario.isAtualizarImage()) {
					stmt.setString(14, usuario.getCurriculoBase64());
					stmt.setString(15, usuario.getContentTypeCurriculo());
				}else {
					stmt.setString(16, usuario.getCurriculoBase64());
					stmt.setString(17, usuario.getContentTypeCurriculo());
				}
				
			}else {
				if(usuario.isAtualizarImage()) {
					stmt.setString(16, usuario.getFotoBase64Miniatura());
				}
			}
			
			if(usuario.isAtualizarImage() && usuario.isAtualizarPdf()) {
				stmt.setString(18, usuario.getFotoBase64Miniatura());
			}
			
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
	
	//Opção de Atualizar
//	public void atualizar(BeanUsuario beanUsuario) {
//		try {
//			StringBuilder sql = new StringBuilder();
//			sql.append("UPDATE usuario SET login = '"  + beanUsuario.getLogin()  + "' " );
//			sql.append(					 ",senha = '"  + beanUsuario.getSenha()  + "' " );
//			sql.append(                  ",nome = '"   + beanUsuario.getNome()   + "' " );
//			sql.append(                  ",cep = '"    + beanUsuario.getCep()    + "' " );
//			sql.append(                  ",rua = '"    + beanUsuario.getRua()    + "' " );
//			sql.append(                  ",bairro = '" + beanUsuario.getBairro() + "' " );
//			sql.append(                  ",cidade = '" + beanUsuario.getCidade() + "' " );
//			sql.append(                  ",estado = '" + beanUsuario.getEstado() + "' " );
//			sql.append(                  ",ibge = '"   + beanUsuario.getIbge()   + "' " );
//			sql.append( 				 ",ativo = '"  + beanUsuario.isAtivo()   + "' " );
//			
//            if (beanUsuario.isAtualizarImagem()) {
//            	sql.append(              ",fotoBase64 = '"          + beanUsuario.getFotoBase64()          + "' " );
//            	sql.append(              ",contentType = '"         + beanUsuario.getContentType()         + "' " );
//            	sql.append(              ",fotoBase64Miniatura = '" + beanUsuario.getFotoBase64Miniatura() + "' " );
//			}
//            
//            if (beanUsuario.isAtualizarPdf()) {
//            	sql.append(              ",pdfBase64 = '"      + beanUsuario.getPdfBase64()      + "' " );
//            	sql.append(              ",pdfContentType = '" + beanUsuario.getPdfContentType() + "' "  );
//			}
//		                                    
//            sql.append("WHERE id = "+ beanUsuario.getId());
//			PreparedStatement statement = connection.prepareStatement(sql.toString());
//			statement.executeUpdate();
//			connection.commit();
//		} catch (Exception e) {
//			try {
//				connection.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		}
//	}

}
