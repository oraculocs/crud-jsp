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
	
	//Método que retorna verdadeiro ou false para validar o login e a senha para o usuário se conectar
	public boolean validarLogin(String login, String senha) throws Exception{
		
		//Consultando no Banco se existe o usuário e senha que foram passados como parâmetros
		String sql = "select * from usuario where login = '" + login+"' and senha = '" + senha + "'";
		//Instrução pro BD
		PreparedStatement stmt = con.prepareStatement(sql);
		//Objeto que vai carregar o resultado do SQL
		ResultSet rs = stmt.executeQuery();
		//Verifica se existe resultado
		if(rs.next()) {
			return true; //encontrou o usuário
		}
		return false; //não validou usuário
	}

}
