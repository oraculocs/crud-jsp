package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Produto;
import beans.Telefones;
import connection.SingleConnection;

public class DaoTelefones {

	private Connection con;

	public DaoTelefones() {
		con = SingleConnection.getConnection();
	}

	public void salvarTelefones(Telefones telefone) {
		try {
			String sql = "insert into telefone(numero, tipo, usuario) values (?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, telefone.getNumero());
			stmt.setString(2, telefone.getTipo());
			stmt.setLong(3, telefone.getUsuario());
			stmt.execute();
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public List<Telefones> listar(Long user) throws Exception {
		List<Telefones> lista = new ArrayList<Telefones>();

		String sql = "select * from telefone where usuario = " + user;
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Telefones tel = new Telefones();
			tel.setId(rs.getLong("id"));
			tel.setNumero(rs.getString("numero"));
			tel.setTipo(rs.getString("tipo"));
			tel.setUsuario(rs.getLong("usuario"));

			lista.add(tel);
		}

		return lista;
	}

	public void delete(String id) {
		try {
			String sql = "delete from telefone where id = '" + id + "'";
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

}
