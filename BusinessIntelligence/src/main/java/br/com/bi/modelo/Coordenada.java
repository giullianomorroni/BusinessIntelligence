package br.com.bi.modelo;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Coordenada implements Entidade {

	private Double latitude;
	private Double longitude;

	/**
	 * Formata a coordenada com 5 casas após a vírgula, garantindo uma
	 * assertividade maior. 
	 * 
	 * (taxa de erro para 4 casas é de 135 metros)
	 * (taxa de erro para 5 casas é de 13 metros)
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public Coordenada(Double latitude, Double longitude) throws IllegalArgumentException {
		super();
		this.latitude = latitude;
		this.longitude = longitude;

		if (this.latitude == null || this.longitude == null)
			return;

		NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(2);
		format.setMaximumIntegerDigits(2);
		format.setRoundingMode(RoundingMode.HALF_UP);
		this.latitude = Double.valueOf(format.format(this.latitude));
		this.longitude = Double.valueOf(format.format(this.longitude));
	}

	/**
	 * Verifica se ambos os campos foram verificados como não nulos
	 * @return
	 */
	public Boolean validar() {
		return !(this.latitude == null || this.longitude == null); 
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
