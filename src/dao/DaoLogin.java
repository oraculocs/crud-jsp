package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class DaoLogin {
	
	private Connection con;

	public DaoLogin() {
		con = SingleConnection.getConnection();
	}
	
	//M�todo que retorna verdadeiro ou false para validar o login e a senha para o usu�rio se conectar
	public boolean validarLogin(String login, String senha) throws Exception{
		
		//Consultando no Banco se existe o usu�rio e senha que foram passados como par�metros
		String sql = "select * from usuario where login = '" + login+"' and senha = '" + senha + "'";
		//Instru��o pro BD
		PreparedStatement stmt = con.prepareStatement(sql);
		//Objeto que vai carregar o resultado do SQL
		ResultSet rs = stmt.executeQuery();
		//Verifica se existe resultado
		if(rs.next()) {
			return true; //encontrou o usu�rio
		}
		return false; //n�o validou usu�rio
	}

}
