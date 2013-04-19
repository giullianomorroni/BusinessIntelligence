package br.com.bi.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Data implements Entidade {

	private Calendar valor;

	public Data() {
		super();
		valor = Calendar.getInstance();
	}

	public Data(Long millis) {
		super();
		valor = Calendar.getInstance();
		valor.setTimeInMillis(millis);
	}

	public Data(Integer dia, Integer mes, Integer ano) {
		super();
		this.valor = Calendar.getInstance();
		this.valor.set(Calendar.MONTH, mes-1);
		this.valor.set(Calendar.DAY_OF_MONTH, dia);
		this.valor.set(Calendar.YEAR, ano);
	}

	public Data(String dia, String mes, String ano) {
		if ((dia != null && dia.trim().length() > 0) &&
			(mes != null && mes.trim().length() > 0) &&
			(ano != null && ano.trim().length() > 0)) {
			valor = Calendar.getInstance();
			valor.set(Calendar.YEAR, new Integer(ano));
			valor.set(Calendar.MONTH, new Integer(mes)-1);
			valor.set(Calendar.DAY_OF_MONTH, new Integer(dia));
		}
	}

	public Data(Calendar valor) {
		super();
		if (valor == null)
			return;
		this.valor = valor;
	}

	/**
	 * Calcula o tempo ocorrido entre a data (a instância em questão) 
	 * e hoje (Calendar.getInstance()); <br/>
	 * 
	 * O retorno varia de acordo com o tempo ocorrido. Em um {@link Map} com um único resultado, exemplos: <br/>
	 * 
	 * <1, minuto>  <br/>
	 * <2, hora>	<br/>
	 * <5, dia>		<br/>
	 * <1, mês>		<br/>
	 * <1, ano>		<br/>
	 * 
	 * @return Map<Integer, String>
	 */
	public Map<Integer, String> tempoOcorrido() {
		Map<Integer, String> resultado = new HashMap<Integer, String>();

		Calendar hoje = Calendar.getInstance();

		Integer anoAtual = hoje.get(Calendar.YEAR);
		Integer ano = valor.get(Calendar.YEAR);

		if (!anoAtual.equals(ano)){
			resultado.put(1, "ano");
			return resultado;
		}

		Integer mesAtual = hoje.get(Calendar.MONTH);
		Integer mes = valor.get(Calendar.MONTH);
		if ((anoAtual.equals(ano)) && !mesAtual.equals(mes)){
			resultado.put(1, "mês");
			return resultado;
		}

		Integer diaAtual = hoje.get(Calendar.DAY_OF_MONTH);
		Integer dia = valor.get(Calendar.DAY_OF_MONTH);
		if ((mesAtual.equals(mes)) && !diaAtual.equals(dia)){
			Integer dias = diaAtual - dia;
			resultado.put(dias, "dia");
			return resultado;
		}

		Integer horaAtual = hoje.get(Calendar.HOUR_OF_DAY);
		Integer minutoAtual = hoje.get(Calendar.MINUTE);
		Integer hora = valor.get(Calendar.HOUR_OF_DAY);
		Integer minuto = valor.get(Calendar.MINUTE);

		if ((diaAtual.equals(dia)) && horaAtual.equals(hora)) {
			Integer minutos = minutoAtual - minuto;
			resultado.put(minutos, "minuto");
			return resultado;
		}
		
		if ((diaAtual.equals(dia)) && !horaAtual.equals(hora)) {
			Integer horas = horaAtual - hora;
			if (horas < 0)
				horas = horas+horas*2;
			resultado.put(horas, "hora");
			return resultado;
		}
		resultado.put(0, "minuto");
		return resultado;
	}
	
	/**
	 * Calcula a diferença de dias de uma data para outra.
	 * O primeiro parâmetro deve ser a menor das duas. 
	 * Caso os valores estejam invertidos retorna -1
	 * 
	 * @param c1 data menor
	 * @param c2 data maior
	 * @return
	 */
	public Integer diferencaDeDias(Calendar c1, Calendar c2) {
		Integer resultado = new Integer(0);

		if (c1.after(c2))
			return -1;

		Integer ano1 = c1.get(Calendar.YEAR);
		Integer ano2 = c2.get(Calendar.YEAR);

		//365 dias de diferenca
		while (!ano1.equals(ano2)){
			resultado += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
			c1.add(Calendar.YEAR, 1);
			ano1 = c1.get(Calendar.YEAR);
		}

		//dias faltantes para o fim do mês
		Integer mes1 = c1.get(Calendar.MONTH);
		Integer mes2 = c2.get(Calendar.MONTH);
		while ((ano1.equals(ano2)) && !mes1.equals(mes2)){
			Integer totalDiasMes = c1.getActualMaximum(Calendar.DAY_OF_MONTH);

			if (mes1.equals(mes2)) {
				Integer diaAtualMes = c1.get(Calendar.DAY_OF_MONTH);
				resultado += (totalDiasMes - diaAtualMes);
			} else {
				resultado += (totalDiasMes);
			}

			c1.add(Calendar.MONTH, 1);
			mes1 = c1.get(Calendar.MONTH);
		}

		Integer dia1 = c1.get(Calendar.DAY_OF_MONTH);
		Integer dia2 = c2.get(Calendar.DAY_OF_MONTH);
		if ((mes1.equals(mes2)) && !dia1.equals(dia2)){
			Integer dias = dia2 - dia1;
			resultado += dias;
		}
		return resultado;
	}
	
	public String formatoBrasileiro(){
		if(valor == null)
			return "";

		try{
			String valorFormatado;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //para salvar no joker precisa seguir o formado 00/00/0000
			valorFormatado = dateFormat.format(valor.getTime());
			return valorFormatado; 
		} catch (NullPointerException | NumberFormatException e) {
			return "";
		}
	}
	
	public String horario(){
		if(valor == null)
			return "";

		try{
			String valorFormatado;
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			valorFormatado = dateFormat.format(valor.getTime());
			return valorFormatado; 
		} catch (NullPointerException | NumberFormatException e) {
			return "";
		}
	}

	public String formatoBrasileiroComHorario(){
		if(valor == null || valor.equals(""))
			return null;

		try{
			String valorFormatado;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			valorFormatado = dateFormat.format(valor.getTime());
			return valorFormatado; 
		} catch (NullPointerException | NumberFormatException e) {
			return "";
		}
	}
	
	public String formatoBrasileiroComHorarioCompleto(){
		if(valor == null || valor.equals(""))
			return null;

		try{
			String valorFormatado;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
			valorFormatado = dateFormat.format(valor.getTime());
			return valorFormatado; 
		} catch (NullPointerException | NumberFormatException e) {
			return "";
		}
	}
	
	public String formatoAmericano(){
		if(valor == null)
			return "";

		try{
			String valorFormatado;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
			valorFormatado = dateFormat.format(valor.getTime());
			return valorFormatado; 
		} catch (NullPointerException | NumberFormatException e) {
			
			return "";
		}
	}
	
	public String formatoAmericanoComHorario(){
		if(valor == null)
			return "";

		try{
			String valorFormatado;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			valorFormatado = dateFormat.format(valor.getTime());
			return valorFormatado; 
		} catch (NullPointerException | NumberFormatException e) {
			
			return "";
		}
	}

	public Data(String data) {
		this(data, null);
	}

	/**
	 * A Data é obrigatória
	 * 
	 * @param data
	 * @param hora
	 */
	public Data(String data, String hora) {
		if(data == null || data.equals("null") || data.equals(""))
			return;

		this.valor = Calendar.getInstance();
		if(data.contains("-")){ //padrão americano
			String[] valoresData = data.split("-");
			this.valor.set(Calendar.YEAR, new Integer(valoresData[0]));
			this.valor.set(Calendar.MONTH, new Integer(valoresData[1])-1); //Mês começa com 0.
			this.valor.set(Calendar.DAY_OF_MONTH, new Integer(valoresData[2]));
		} else if(data.contains("/")){ //padrão brasileiro
			String[] valoresData = data.split("/");
			this.valor.set(Calendar.DAY_OF_MONTH, new Integer(valoresData[0]));
			this.valor.set(Calendar.MONTH, new Integer(valoresData[1])-1); //Mês começa com 0.
			this.valor.set(Calendar.YEAR, new Integer(valoresData[2]));
		}

		if(hora != null) {
			String[] valoresHora = new String[]{"0","0"};
			if (hora.contains("h")){
				valoresHora = hora.split("h");
			} else if (hora.contains(":")){
				valoresHora = hora.split(":");
			}

			this.valor.set(Calendar.HOUR_OF_DAY, new Integer(valoresHora[0]));
			this.valor.set(Calendar.MINUTE, new Integer(valoresHora[1]));
		}

	}

	/**
	 * Calcula o ano deste (this) com o ano atual. 
	 * @return
	 */
	public Integer calcularIdade(){
		if (this.valor == null)
			return 0;

		int ano = this.valor.get(Calendar.YEAR);
		int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

		return anoAtual - ano;
	}

	/**
	 * Retorna o primerio dia da semana atual.
	 * 
	 * @return
	 * @throws Falha
	 */
	public Calendar comecoDaSemana() {
		Calendar comecoSemana = Calendar.getInstance();
		comecoSemana.setTimeInMillis(this.valor.getTimeInMillis());

		int diaSemana = comecoSemana.get(Calendar.DAY_OF_WEEK);
		while (diaSemana > 1) {
			comecoSemana.add(Calendar.DAY_OF_WEEK, -1);
			diaSemana = comecoSemana.get(Calendar.DAY_OF_WEEK);
		}
		return comecoSemana;
	}

	/**
	 * Retorna o último dia da semana atual.
	 * 
	 * @return
	 * @throws Falha
	 */
	public Calendar finalDaSemana() {
		Calendar finalSemana = Calendar.getInstance();
		finalSemana.setTimeInMillis(this.valor.getTimeInMillis());

		int diaSemana = finalSemana.get(Calendar.DAY_OF_WEEK);
		while (diaSemana < 7) {
			finalSemana.add(Calendar.DAY_OF_WEEK, 1);
			diaSemana = finalSemana.get(Calendar.DAY_OF_WEEK);
		}
		return finalSemana;
	}

	/**
	 * Retorna a data atual menos a quantidade de dias informados.
	 * 
	 * 1 dia = 86400000 milisegundos
	 * 
	 * @param diasInformado
	 * @return
	 */
	public Calendar subtrairDias(Integer diasInformado) {
		Calendar data = this.getValor();
		Long hojeMiliSegundos = data.getTimeInMillis();

		Long miliSegundosPorDia = 86400000L;
		miliSegundosPorDia = miliSegundosPorDia * diasInformado;

		Long resultaMiliSegundos = hojeMiliSegundos - miliSegundosPorDia;
		data.setTimeInMillis(resultaMiliSegundos);
		return data;
	}

	public Calendar subtrairMinutos(Integer minutosInformado) {
		Calendar data = this.getValor();
		Long hojeMiliSegundos = data.getTimeInMillis();

		Long miliSegundosPorMinuto = 60000l;
		miliSegundosPorMinuto = miliSegundosPorMinuto * minutosInformado;

		Long resultaMiliSegundos = hojeMiliSegundos - miliSegundosPorMinuto;
		data.setTimeInMillis(resultaMiliSegundos);
		return data;
	}
	
	public Calendar getValor() {
		return valor;
	}

	public void setValor(Calendar valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return this.formatoBrasileiro();
	}

}
