package br.com.flagme.bi.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email implements Entidade {

	private String email;

	public Email() {
		super();
	}
	
	/**
	 * 
	 * @param email
	 * @throws IllegalArgumentException
	 */
	public Email(String email) throws IllegalArgumentException {
		super();
		if(email == null || email.equals("null") || email.trim().length() == 0)
			return;

		this.email = email.toLowerCase();
		this.validar();
	}

	private void validar() {
		if (this.email == null || this.email.trim().length() == 0) {
			String erro = ("campo_vazio");
		    throw new IllegalArgumentException(erro);
		}

		Pattern regex = Pattern.compile("[a-z0-9\\._-]+@{1}[a-z0-9]{1,}\\.[a-z]{1,4}\\.?[a-z]+?", Pattern.CASE_INSENSITIVE);
		Matcher matcher = regex.matcher(this.email);
		Boolean valido = matcher.matches();
		if (!valido){
	       String erro = ("valor_invalido");
	       throw new IllegalArgumentException(erro);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return this.email == null ? "" : this.email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Email other = (Email) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
}
