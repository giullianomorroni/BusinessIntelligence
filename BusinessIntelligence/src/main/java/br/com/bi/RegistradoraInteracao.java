package br.com.bi;

import java.util.ArrayList;
import java.util.List;

import br.com.bi.fato.FatoLocalizacao;
import br.com.bi.modelo.Cliente;
import br.com.bi.modelo.Entidade;
import br.com.bi.modelo.Estabelecimento;
import br.com.bi.modelo.Flag;

/**
 * Esta classe receberá todos os eventos e a partir de uma lógica interna 
 * irá decidir para quais registradores os dados serão enviados.
 * 
 * @author giulliano
  */
public class RegistradoraInteracao implements Runnable {

	private final List<Entidade> entidades = new ArrayList<Entidade>();

	private Flag flag;
	private Estabelecimento estabelecimento;
	private Cliente cliente;

	private Double longitude;
	private Double latitude;

	public RegistradoraInteracao(Entidade ...entidade) {
		for (Entidade e : entidade)
			this.entidades.add(e);

		final Thread thread = new Thread(this, "BI");
		thread.start();
	}

	public RegistradoraInteracao(Double latitude, Double longitude, Entidade ...entidade) {
		for (Entidade e : entidade)
			this.entidades.add(e);

		this.longitude = longitude;
		this.latitude = latitude;

		final Thread thread = new Thread(this, "BI");
		thread.start();
	}

	@Override
	public void run() {
		for (Entidade entidade : entidades) {
			if (entidade instanceof Flag)
				this.flag = (Flag) entidade;
			else if (entidade instanceof Estabelecimento)
				this.estabelecimento = (Estabelecimento) entidade;
			else if (entidade instanceof Cliente)
				this.cliente = (Cliente) entidade;
		}

		try {
			this.registarFlag();
			this.registarVisita();
			this.registarLocalizacaoAtual();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private void registarFlag(){
		if (flag != null)
			new FatoLocalizacao(flag).registrar();
	}

	private void registarVisita(){
		if (estabelecimento != null && cliente != null)
			new FatoLocalizacao(estabelecimento, cliente).registrar();
	}

	private void registarLocalizacaoAtual(){
		if (cliente != null && latitude != null && longitude != null)
			new FatoLocalizacao(cliente, latitude, longitude).registrar();
	}

}
