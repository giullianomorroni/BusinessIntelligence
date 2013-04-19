package br.com.flagme.bi.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Telefone implements Entidade {

	private String telefone;

	private String ddd;
	private String numero;

	/**
	 * Construtor
	 */
	public Telefone() {
		super();
	}

	/**
	 * Construtor
	 * @param pais
	 * @param ddd
	 * @param numero
	 */
	public Telefone(String pais, String ddd, String numero) {
		super();
		if (numero == null || numero.trim().length() == 0 || numero.equals("null"))
			return;

		this.numero = numero.replace(" ", "").replace("(", "").replace(")", "").replace("-", ""); //Remove espaços em branco

		if(ddd != null)
			this.ddd = ddd.replace(" ", "").replace("(", "").replace(")", "").replace("-", ""); //Remove espaços em branco

		if (this.numero != null && this.ddd != null){
			this.telefone = this.ddd + " " + this.numero;
		} else if (this.numero != null && this.numero.length() > 0){
			this.telefone = this.numero;
		}
	}

	/**
	 * Construtor
	 * @param ddd
	 * @param numero
	 */
	public Telefone(String ddd, String numero) {
		this("BR", ddd, numero);
	}

	/**
	 * Construtor
	 * @param numero
	 */
	public Telefone(String numero) {
		if (numero != null) {
			this.numero = numero.replace(" ", "").replace("(", "").replace(")", "").replace("-", "");
			if (this.numero.length() >= 10){
				this.ddd = this.numero.substring(0,2);
				this.numero = this.numero.substring(2);
				this.telefone = this.ddd + " " + this.numero;
			} else {
				this.telefone = this.numero;
			}
		}
	}

	public void formatarValores() {
		if (this.telefone != null && this.telefone.length() > 2) {
			this.numero = telefone.replace(" ", "");
			this.ddd = numero.substring(0, 2);
			this.numero = numero.substring(2);
		}
	}

	/**
	 * Verifica o telefone é válido através de uma expressão Regex
	 * @throws IllegalArgumentException
	 */ 
	public void validar() throws IllegalArgumentException {
		if (this.telefone == null) {
			String erro = ("valor_invalido");
			throw new IllegalArgumentException(erro);
		}

		Pattern regex = null;
		if (this.ddd != null) {
			regex = Pattern.compile("[1-9]{2}(9?[6-9]|[2-9])[0-9]{3}[0-9]{4}");
		} else {
			regex = Pattern.compile("(9?[6-9]|[2-9])[0-9]{3}[0-9]{4}");
		}

		Matcher matcher = regex.matcher(this.telefone.replaceAll(" ", ""));
		Boolean valido = matcher.matches();
		if(!valido) {
			String erro = ("valor_invalido");
			throw new IllegalArgumentException(erro);
		}
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * Formata o telefone de acordo com a necessidade
	 * do pagamento digital (11-999-9999)
	 * 
	 * @return
	 */
	public String formatoPD(){
		if (telefone != null) {
			numero = telefone.replace(" ", "");
			if (numero.length() >= 11) {
				String ddd = numero.substring(0, 2);
				String numero_p1 = numero.substring(2,7);
				String numero_p2 = numero.substring(7);
				numero = ddd+"-"+numero_p1+"-"+numero_p2;
			} else if (numero.length() >= 10) {
				String ddd = numero.substring(0, 2);
				String numero_p1 = numero.substring(2,6);
				String numero_p2 = numero.substring(6);
				numero = ddd+"-"+numero_p1+"-"+numero_p2;
			}
		}
		return numero;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		if (this.telefone != null)
			return this.telefone;
		else
			return new String();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((telefone == null) ? 0 : telefone.hashCode());
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
		Telefone other = (Telefone) obj;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

}
