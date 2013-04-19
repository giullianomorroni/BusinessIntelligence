package br.com.flagme.bi.dimensao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DimensaoEstabelecimento implements Dimensao {

	private Long idEstabelecimento;
	private String nomeFantasia;

	public DimensaoEstabelecimento(Long idEstabelecimento, String nomeFantasia) {
		super();
		this.idEstabelecimento = idEstabelecimento;
		this.nomeFantasia = nomeFantasia;
	}

	@Override
	public Long consultaIdComInsercao(Connection conexao) {
		Long id = null;
		if (this.nomeFantasia == null)
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
			String sql = "SELECT ID FROM BUSINESS_INTELLIGENCE.TB_DM_ESTABELECIMENTO WHERE NOME_FANTASIA = ? AND ID_ESTABELECIMENTO = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, nomeFantasia);
			ps.setLong(2, idEstabelecimento);
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
		String sql = "INSERT INTO BUSINESS_INTELLIGENCE.TB_DM_ESTABELECIMENTO (ID, NOME_FANTASIA, ID_ESTABELECIMENTO) VALUES (?,?,?)";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setLong(1, proximoId(conexao));
		ps.setString(2, nomeFantasia);
		ps.setLong(3, idEstabelecimento);
		ps.execute();
		return this.consultaId(conexao);
	}

	@Override
	public Long proximoId(Connection conexao) {
		Long id = null;
		try {
			String sql = "SELECT MAX(ID)+1 FROM BUSINESS_INTELLIGENCE.TB_DM_ESTABELECIMENTO";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (id == null || id == 0) ? 1 : id;
	}
	
	public Long getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

}
