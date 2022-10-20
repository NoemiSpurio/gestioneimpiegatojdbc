package it.prova.gestioneimpiegatojdbc.model;

import java.util.Date;
import java.util.List;

public class Compagnia {

	private Long id;
	private String ragionesociale;
	private Integer fatturatoAnnuo;
	private Date dataFondazione;
	private List<Impiegato> impiegati;

	public Compagnia() {

	}

	public Compagnia(String ragioneSociale, int fatturatoAnnuo, Date dataFondazione) {
		setRagionesociale(ragioneSociale);
		setFatturatoAnnuo(fatturatoAnnuo);
		setDataFondazione(dataFondazione);
	}
	
	public Compagnia(Long id, String ragioneSociale, int fatturatoAnnuo, Date dataFondazione) {
		setId(id);
		setRagionesociale(ragioneSociale);
		setFatturatoAnnuo(fatturatoAnnuo);
		setDataFondazione(dataFondazione);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagionesociale() {
		return ragionesociale;
	}

	public void setRagionesociale(String ragionesociale) {
		this.ragionesociale = ragionesociale;
	}

	public Integer getFatturatoAnnuo() {
		return fatturatoAnnuo;
	}

	public void setFatturatoAnnuo(Integer fatturatoAnnuo) {
		this.fatturatoAnnuo = fatturatoAnnuo;
	}

	public Date getDataFondazione() {
		return dataFondazione;
	}

	public void setDataFondazione(Date dataFondazione) {
		this.dataFondazione = dataFondazione;
	}

	public List<Impiegato> getImpiegati() {
		return impiegati;
	}

	public void setImpiegati(List<Impiegato> impiegati) {
		this.impiegati = impiegati;
	}

	@Override
	public String toString() {
		return "Compagnia [id=" + id + ", ragionesociale=" + ragionesociale + ", fatturatoAnnuo=" + fatturatoAnnuo
				+ ", dataFondazione=" + dataFondazione + "]";
	}

}
