package br.com.flagme.bi.modelo;

public class Cliente implements Entidade {

	private Long id;
	private String idContaTwitter;
	private String idContaFaceBook;
	private String urlFotoPerfil;

	private String nome;
	private String sobreNome;

	private String senha;
	private String codigoValidacao;

	private Cpf cpf;
	private RegistroGeral rg;
	private Telefone telefone;
	private Email email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdContaTwitter() {
		return idContaTwitter;
	}

	public void setIdContaTwitter(String idContaTwitter) {
		this.idContaTwitter = idContaTwitter;
	}

	public String getIdContaFaceBook() {
		return idContaFaceBook;
	}

	public void setIdContaFaceBook(String idContaFaceBook) {
		this.idContaFaceBook = idContaFaceBook;
	}

	public String getUrlFotoPerfil() {
		return urlFotoPerfil;
	}

	public void setUrlFotoPerfil(String urlFotoPerfil) {
		this.urlFotoPerfil = urlFotoPerfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCodigoValidacao() {
		return codigoValidacao;
	}

	public void setCodigoValidacao(String codigoValidacao) {
		this.codigoValidacao = codigoValidacao;
	}

	public Cpf getCpf() {
		return cpf;
	}

	public void setCpf(Cpf cpf) {
		this.cpf = cpf;
	}

	public RegistroGeral getRg() {
		return rg;
	}

	public void setRg(RegistroGeral rg) {
		this.rg = rg;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

}