package br.com.flagme.bi.modelo;

import java.util.InputMismatchException;

public class Cpf implements Entidade {

	private String valor;

	public Cpf() {
		super();
	}

	/**
	 * 
	 * @param valor
	 * @throws IllegalArgumentException
	 */
	public Cpf(String valor) {
		super();
		if (valor == null || valor.trim().length() == 0)
			return;

		this.valor = valor.replace(".", "").replace("-", "");
		Boolean valido = new ValidaCpf().validar(this.valor);
		if(!valido)
			throw new IllegalArgumentException("CPF inválido");
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValorFormatado(){
		if (this.valor == null)
			return "";

		if (this.valor.length() < 11)
			return "";

		String formatado = this.valor.substring(0,3) + ".";
		formatado += this.valor.substring(3,6) + ".";
		formatado += this.valor.substring(6,9) + "-";
		formatado += this.valor.substring(9);

		return formatado;
	}

	public String getSomenteNumero(){
		if (this.valor == null)
			return "";
		return valor.replace(".", "").replace("-", "");
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
		Cpf other = (Cpf) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
}

class ValidaCpf {

	public Boolean validar(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
	    if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
	        CPF.equals("22222222222") || CPF.equals("33333333333") ||
	        CPF.equals("44444444444") || CPF.equals("55555555555") ||
	        CPF.equals("66666666666") || CPF.equals("77777777777") ||
	        CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11)){
	    	return(false);
	    }

	    char dig10, dig11;
	    int sm, i, r, num, peso;

	    try {
		// Calculo do 1o. Digito Verificador
	    	sm = 0;
			peso = 10;
			for (i=0; i<9; i++) {              
				num = (int)(CPF.charAt(i) - 48); 
			    sm = sm + (num * peso);
			    peso = peso - 1;
			}
	
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)){
				dig10 = '0';
			} else {
				dig10 = (char)(r + 48); // converte no respectivo caractere numerico
			}
			 
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for(i=0; i<10; i++) {
				num = (int)(CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			 
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)){
				dig11 = '0';
			} else {
				dig11 = (char)(r + 48);
			}
	
			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
				return(true);
			} else {
				return(false);
			}
	    } catch (InputMismatchException erro) {
	    	return(false);
	    }
	}
	
}
