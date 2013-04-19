package br.com.flagme.bi.dimensao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

public class DimensaoPosicao implements Dimensao {

	private Double latitude;
	private Double longitude;

	public DimensaoPosicao(Double latitude, Double longitude) {
		super();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumIntegerDigits(2);
		this.latitude = new Double(nf.format(latitude).replace(",", "."));
		this.longitude = new Double(nf.format(longitude).replace(",", "."));
	}

	@Override
	public Long consultaIdComInsercao(Connection conexao) {
		Long id = null;
		if (this.latitude == null || this.longitude== null)
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
			String sql = "SELECT ID FROM BUSINESS_INTELLIGENCE.TB_DM_POSICAO WHERE LATITUDE = ? AND LONGITUDE = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setDouble(1, latitude);
			ps.setDouble(2, longitude);			
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
		String sql = "INSERT INTO BUSINESS_INTELLIGENCE.TB_DM_POSICAO (ID, LATITUDE, LONGITUDE) VALUES (?,?,?)";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setLong(1, proximoId(conexao));
		ps.setDouble(2, latitude);
		ps.setDouble(3, longitude);
		ps.execute();
		return this.consultaId(conexao);
	}

	@Override
	public Long proximoId(Connection conexao) {
		Long id = null;
		try {
			String sql = "SELECT MAX(ID)+1 FROM BUSINESS_INTELLIGENCE.TB_DM_POSICAO";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (id == null || id == 0) ? 1 : id;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "DimensaoPosicao [latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

}
