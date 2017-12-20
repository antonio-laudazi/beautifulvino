package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello.Provincia;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetProvince extends Risposta {

	List<Provincia> province;

	/**
	 * @return the province
	 */
	public List<Provincia> getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(List<Provincia> province) {
		this.province = province;
	}
}
