package br.com.bi.dimensao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.bi.modelo.Data;

public class DimensaoData implements Dimensao {

	private Integer ano;
	private Integer mes;
	private Integer dia;
	private Integer hora;
	private Integer minuto;

	public DimensaoData(Data data) {
		if(data == null)
			return;

		final Calendar valor = data.getValor();
		this.ano = valor.get(Calendar.YEAR);
		this.mes = valor.get(Calendar.MONTH)+1;
		this.dia = valor.get(Calendar.DAY_OF_MONTH);
		this.hora = valor.get(Calendar.HOUR_OF_DAY);
		this.minuto = valor.get(Calendar.MINUTE);

		if (minuto <= 15)
			minuto = 15;
		else if (minuto <= 30)
			minuto = 30;
		else if (minuto <= 45)
			minuto = 45;
		else
			minuto = 60;
	}

	@Override
	public Long consultaIdComInsercao(Connection conexao) {
		Long id = null;
		
		if (this.dia == null && this.hora == null)
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
			String sql = "SELECT ID FROM BUSINESS_INTELLIGENCE.TB_DM_DATA WHERE ANO = ? AND MES = ? AND DIA = ? AND HORA = ? AND MINUTO = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, ano);
			ps.setInt(2, mes);
			ps.setInt(3, dia);
			ps.setInt(4, hora);
			ps.setInt(5, minuto);			
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
		String sql = "INSERT INTO BUSINESS_INTELLIGENCE.TB_DM_DATA (ID, ANO, MES, DIA, HORA, MINUTO) VALUES (?,?,?,?,?,?)";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setLong(1, proximoId(conexao));
		ps.setInt(2, ano);
		ps.setInt(3, mes);
		ps.setInt(4, dia);
		ps.setInt(5, hora);
		ps.setInt(6, minuto);
		ps.execute();
		return this.consultaId(conexao);
	}

	@Override
	public Long proximoId(Connection conexao) {
		Long id = null;
		try {
			String sql = "SELECT MAX(ID)+1 FROM BUSINESS_INTELLIGENCE.TB_DM_DATA";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (id == null || id == 0) ? 1 : id;
	}
	
	public Integer getAno() {
		return ano;
	}

	public Integer getMes() {
		return mes;
	}

	public Integer getDia() {
		return dia;
	}

	public Integer getHora() {
		return hora;
	}

	public Integer getMinuto() {
		return minuto;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DimensaoData [ano=" + ano + ", mes=" + mes + ", dia=" + dia
				+ ", hora=" + hora + ", minuto=" + minuto + "]";
	}

}
