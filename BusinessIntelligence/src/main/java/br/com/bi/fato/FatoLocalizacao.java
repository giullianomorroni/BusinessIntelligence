package br.com.bi.fato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.bi.Registrar;
import br.com.bi.dimensao.DimensaoCategoria;
import br.com.bi.dimensao.DimensaoData;
import br.com.bi.dimensao.DimensaoEstabelecimento;
import br.com.bi.dimensao.DimensaoLocal;
import br.com.bi.dimensao.DimensaoPosicao;
import br.com.bi.fabrica.FabricaConexao;
import br.com.bi.modelo.Cliente;
import br.com.bi.modelo.Data;
import br.com.bi.modelo.Endereco;
import br.com.bi.modelo.Estabelecimento;
import br.com.bi.modelo.Flag;

public class FatoLocalizacao implements Registrar, Fato {

	private String nomeCliente;
	private String emailCliente;
	private Long idCliente;
	private Boolean flag;

	private DimensaoData dimensaoData;
	private DimensaoLocal dimensaoLocal;
	private DimensaoCategoria dimensaoCategoria;
	private DimensaoEstabelecimento dimensaoEstabelecimento; 
	private DimensaoPosicao dimensaoPosicao;

	private Long idDimensaoCategoria;
	private Long idDimensaoData;
	private Long idDimensaoLocal;
	//FIXME Ainda não tenho ofertas
	private Long idDimensaoOferta;
	private Long idDimensaoEstabelecimento;
	private Long idDimensaoPosicao;

	/**
	 * Construtor
	 * @param flag
	 */
	public FatoLocalizacao(Flag flag) {
		Estabelecimento estabelecimento = flag.getEstabelecimento();
		Endereco endereco = estabelecimento.getEndereco();
		this.dimensaoData = new DimensaoData(new Data(flag.getDataFlag()));
		this.dimensaoLocal = new DimensaoLocal(endereco);
		this.dimensaoPosicao = new DimensaoPosicao(endereco.getCoordenada().getLatitude(), endereco.getCoordenada().getLongitude());
		this.dimensaoEstabelecimento = new DimensaoEstabelecimento(estabelecimento.getId(), estabelecimento.getNomeFantasia());

		this.nomeCliente = flag.getCliente().getNome();
		this.emailCliente = flag.getCliente().getEmail().getEmail();
		this.idCliente = flag.getCliente().getId();
		this.flag = true;
	}

	public FatoLocalizacao(Estabelecimento estabelecimento, Cliente cliente) {
		Endereco endereco = estabelecimento.getEndereco();
		this.dimensaoData = new DimensaoData(new Data());
		this.dimensaoLocal = new DimensaoLocal(endereco);
		this.dimensaoPosicao = new DimensaoPosicao(endereco.getCoordenada().getLatitude(), endereco.getCoordenada().getLongitude());
		this.dimensaoEstabelecimento = new DimensaoEstabelecimento(estabelecimento.getId(), estabelecimento.getNomeFantasia());

		this.nomeCliente = cliente.getNome();
		this.emailCliente = cliente.getEmail().getEmail();
		this.idCliente = cliente.getId();
		this.flag = false;
	}

	public FatoLocalizacao(Cliente cliente, Double latitude, Double longitude) {
		this.dimensaoData = new DimensaoData(new Data());
		this.dimensaoPosicao = new DimensaoPosicao(latitude, longitude);

		this.nomeCliente = cliente.getNome();
		this.emailCliente = cliente.getEmail().getEmail();
		this.idCliente = cliente.getId();
		this.flag = false;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.flagme.bi.Registrar#registrar()
	 */
	@Override
	public void registrar() {
		try {
			final Connection conexao = FabricaConexao.conectar();
			this.idDimensaoCategoria = dimensaoCategoria.consultaIdComInsercao(conexao);
			this.idDimensaoLocal = dimensaoLocal.consultaIdComInsercao(conexao);
			this.idDimensaoData = dimensaoData.consultaIdComInsercao(conexao);
			this.idDimensaoEstabelecimento = dimensaoEstabelecimento.consultaIdComInsercao(conexao);
			this.idDimensaoPosicao = dimensaoPosicao.consultaIdComInsercao(conexao);

			String sql = 
					"INSERT INTO BUSINESS_INTELLIGENCE.TB_FATO_LOCALIZACAO (" +
					" ID, " +
					" ID_DM_LOCAL, " +
					" ID_DM_DATA, " +
					" ID_DM_CATEGORIA, " +
					" ID_DM_OFERTA, " +
					" ID_DM_ESTABELECIMENTO, " +
					" ID_DM_POSICAO, " +
					" EMAIL, " +
					" NOME_COMPLETO, " +
					" ID_CLIENTE, " +
					" FLAG " +
					" )VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, proximoId(conexao));
			ps.setObject(2, idDimensaoLocal);
			ps.setObject(3, idDimensaoData);
			ps.setObject(4, idDimensaoCategoria);
			ps.setObject(5, idDimensaoOferta);
			ps.setObject(6, idDimensaoEstabelecimento);
			ps.setObject(7, idDimensaoPosicao);
			ps.setString(8, emailCliente);
			ps.setString(9, nomeCliente);
			ps.setObject(10, idCliente);
			ps.setBoolean(11, flag);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Long proximoId(Connection conexao) {
		Long id = null;
		try {
			String sql = "SELECT MAX(ID)+1 FROM BUSINESS_INTELLIGENCE.TB_FATO_LOCALIZACAO";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (id == null || id == 0) ? 1 : id;
	}

	@Override
	public String toString() {
		return "FatoLocalizacao [nomeCliente=" + nomeCliente
				+ ", emailCliente=" + emailCliente + ", idCliente=" + idCliente
				+ ", flag=" + flag + ", dimensaoData=" + dimensaoData
				+ ", dimensaoLocal=" + dimensaoLocal + ", dimensaoCategoria=" + dimensaoCategoria
				+ ", idDimensaoCategoria=" + idDimensaoCategoria
				+ ", idDimensaoData=" + idDimensaoData + ", idDimensaoLocal="
				+ idDimensaoLocal+"]";
	}

}
