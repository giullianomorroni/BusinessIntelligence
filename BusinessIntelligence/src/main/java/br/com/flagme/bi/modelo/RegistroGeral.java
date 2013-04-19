package br.com.flagme.bi.modelo;

public class RegistroGeral implements Entidade {

	private String valor;

	public RegistroGeral() {
		super();
	}
	
	public RegistroGeral(String valor) {
		super();
		if (valor == null)
			return;

		this.valor = valor.replace(".", "").replace("-", "");
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return this.valor == null ? "" : this.valor;
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
		RegistroGeral other = (RegistroGeral) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
