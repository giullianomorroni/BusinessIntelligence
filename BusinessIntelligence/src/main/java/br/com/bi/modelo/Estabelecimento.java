package br.com.bi.modelo;

public class Estabelecimento implements Entidade {

	private Long id;
	private String nomeFantasia;
	private Endereco endereco;
	private Integer classificacao;
	private String razaoSocial;
	private Endereco correspondencia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Integer getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Integer classificacao) {
		this.classificacao = classificacao;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Endereco getCorrespondencia() {
		return correspondencia;
	}

	public void setCorrespondencia(Endereco correspondencia) {
		this.correspondencia = correspondencia;
	}

}
