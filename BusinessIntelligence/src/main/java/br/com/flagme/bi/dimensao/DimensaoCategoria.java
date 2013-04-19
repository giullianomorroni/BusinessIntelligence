package br.com.flagme.bi.dimensao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DimensaoCategoria implements Dimensao {

	private String categoria;
	private String subCategoria;
	
	public DimensaoCategoria(String categoria, String subCategoria) {
		this.categoria = categoria;
		this.subCategoria = subCategoria;
	}

	@Override
	public Long consultaIdComInsercao(Connection conexao) {
		Long id = null;
		try {
			id = this.consultaId(conexao);
			if (id == null)
				id = this.inserirRegistro(conexao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public Long consultaId(Connection conexao) {
		Long id = null;
		if (this.categoria == null)
			return id;
		try {
			String sql = "SELECT ID FROM BUSINESS_INTELLIGENCE.TB_DM_CATEGORIA WHERE 1=1 ";
			if(categoria != null)
				sql += " AND CATEGORIA LIKE '"+categoria+"'";
			if(subCategoria != null)
				sql += " AND SUBCATEGORIA LIKE '"+subCategoria+"'";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Long inserirRegistro(Connection conexao) throws SQLException {
		String sql = "INSERT INTO BUSINESS_INTELLIGENCE.TB_DM_CATEGORIA (ID, CATEGORIA, SUBCATEGORIA) VALUES (?,?,?)";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setLong(1, proximoId(conexao));
		ps.setString(2, categoria);
		ps.setString(3, subCategoria);
		ps.execute();
		return this.consultaId(conexao);
	}
	
	@Override
	public Long proximoId(Connection conexao) {
		Long id = null;
		try {
			String sql = "SELECT MAX(ID)+1 FROM BUSINESS_INTELLIGENCE.TB_DM_CATEGORIA";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (id == null || id == 0) ? 1 : id;
	}
	
	public String getCategoria() {
		return categoria;
	}

	public String getSubCategoria() {
		return subCategoria;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DimensaoCategoria [categoria=" + categoria + ", subCategoria="
				+ subCategoria + "]";
	}

}
