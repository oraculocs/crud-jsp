package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCategoria;
import beans.Produto;
import connection.SingleConnection;

public class DaoProduto {
	
	private Connection con;
	
	public DaoProduto() {
		con = SingleConnection.getConnection();
	}
	
	public void salvarProduto(Produto produto) {
		try {
			String sql = "insert into produto(nome, quantidade, valor, categoria_id) values (?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getValor());
			stmt.setLong(4, produto.getCategoria_id());
			stmt.execute();
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public List<Produto> listar() throws Exception{
		List<Produto> lista = new ArrayList<Produto>();
		
		String sql = "select * from produto";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		
		while(rs.next()) {
			Produto p = new Produto();
			p.setId(rs.getLong("id"));
			p.setNome(rs.getString("nome"));
			p.setQuantidade(rs.getInt("quantidade"));
			p.setValor(rs.getDouble("valor"));
			p.setCategoria_id(rs.getLong("categoria_id"));
			
			lista.add(p);
		}
		
		
		return lista;
	}
	
	public List<BeanCategoria> listaCategorias() throws Exception{
		List<BeanCategoria> retorno = new ArrayList<BeanCategoria>();
		String sql = "select * from categoria";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			BeanCategoria categoria = new BeanCategoria();
			
			categoria.setId(rs.getLong("id"));
			categoria.setNome(rs.getString("nome"));
			retorno.add(categoria);
		}
		 return retorno;
		
	}
	
	public void delete(String id) {
		try {
			String sql = "delete from produto where id = '" + id + "'";
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

	public Produto consultar(String id) throws Exception {
		
		
		String sql = "select * from produto where id = '" + id + "'";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			Produto p = new Produto();
			p.setId(rs.getLong("id"));
			p.setNome(rs.getString("nome"));
			p.setQuantidade(rs.getInt("quantidade"));
			p.setValor(rs.getDouble("valor"));
			p.setCategoria_id(rs.getLong("categoria_id"));
			

			return p;
		}
		return null;
	}
	
	
	public void atualizar(Produto produto) {
		try {
			String sql = "update produto set nome = ?, valor = ?, quantidade = ?, categoria_id = ? where id = " + produto.getId();

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setDouble(2, produto.getValor());
			stmt.setInt(3, produto.getQuantidade());
			stmt.setLong(4, produto.getCategoria_id());
			
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

}
