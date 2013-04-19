package br.com.flagme.bi.modelo;

import java.util.Calendar;

public class Flag implements Entidade {

	private Long id;
	private Cliente cliente;
	private Estabelecimento estabelecimento;
	private Calendar dataFlag;
	private Boolean possuiFoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Calendar getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(Calendar dataFlag) {
		this.dataFlag = dataFlag;
	}

	public Boolean getPossuiFoto() {
		return possuiFoto;
	}

	public void setPossuiFoto(Boolean possuiFoto) {
		this.possuiFoto = possuiFoto;
	}

}
