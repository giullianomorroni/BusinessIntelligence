package br.com.flagme.bi.modelo;


public class Cep implements Entidade {

	private String valor;

	public Cep() {
		super();
	}
	
	public Cep(String valor) {
		super();
		if (valor == null){
			valor = "";
		}

		this.valor = valor.replace("-", "").replace(" ", "");
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPrimeiraParte() {
		if (valor != null && valor.length() > 5)
			return valor.substring(0,5);
		return valor;
	}

	public String getSegundaParte() {
		if (valor != null && valor.length() > 5)
			return valor.substring(5);
		return valor;
	}

	@Override
	public String toString() {
		return this.valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cep other = (Cep) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	
}
