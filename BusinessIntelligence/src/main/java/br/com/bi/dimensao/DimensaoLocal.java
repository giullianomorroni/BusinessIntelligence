package br.com.bi.dimensao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.bi.modelo.Endereco;

public class DimensaoLocal implements Dimensao {

	private String pais;
	private String estado;
	private String cidade;
	private String bairro;

	public DimensaoLocal(Endereco endereco) {
		if (endereco == null)
			return;
		
		this.bairro = endereco.getBairro();
		this.cidade = endereco.getCidade();
		this.estado = endereco.getEstado();
		this.pais = endereco.getPais();
	}

	@Override
	public Long consultaIdComInsercao(Connection conexao) {
		Long id = null;
		if (this.estado == null && this.cidade == null)
			return id;
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
		try {
			String sql = "SELECT ID FROM BUSINESS_INTELLIGENCE.TB_DM_LOCAL WHERE 1 = 1 ";
			if (pais != null)
				sql += " AND PAIS = '"+pais+"'";
			if (cidade != null)
				sql += " AND CIDADE = '"+cidade+"'";
			if (estado != null)
				sql += " AND ESTADO = '"+estado+"'";
			if (bairro != null)
				sql += " AND BAIRRO = '"+bairro+"'";
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
		String sql = "INSERT INTO BUSINESS_INTELLIGENCE.TB_DM_LOCAL (ID, PAIS, ESTADO, CIDADE, BAIRRO) VALUES (?,?,?,?,?)";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setLong(1, proximoId(conexao));
		ps.setString(2, pais);
		ps.setString(3, estado);
		ps.setString(4, cidade);
		ps.setString(5, bairro);
		ps.execute();
		return this.consultaId(conexao);
	}
	
	@Override
	public Long proximoId(Connection conexao) {
		Long id = null;
		try {
			String sql = "SELECT MAX(ID)+1 FROM BUSINESS_INTELLIGENCE.TB_DM_LOCAL";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (id == null || id == 0) ? 1 : id;
	}
	
	public String getPais() {
		return pais;
	}

	public String getEstado() {
		return estado;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	@Override
	public String toString() {
		return "DimensaoLocal [pais=" + pais + ", estado=" + estado
				+ ", cidade=" + cidade + ", bairro=" + bairro + "]";
	}
	
}
