package it.prova.gestioneimpiegatojdbc.model;

import java.util.Date;

public class Impiegato {

	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataNascita;
	private Date dataAssunzione;
	private Compagnia compagnia;

	public Impiegato() {

	}

	public Impiegato(String nome, String cognome, String codiceFiscale, Date dataNascita, Date dataAssunzione) {
		setNome(nome);
		setCognome(cognome);
		setCodiceFiscale(codiceFiscale);
		setDataNascita(dataNascita);
		setDataAssunzione(dataAssunzione);
	}

	public Impiegato(String nome, String cognome, String codiceFiscale, Date dataNascita, Date dataAssunzione,
			Compagnia compagnia) {
		setNome(nome);
		setCognome(cognome);
		setCodiceFiscale(codiceFiscale);
		setDataNascita(dataNascita);
		setDataAssunzione(dataAssunzione);
		setCompagnia(compagnia);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Compagnia getCompagnia() {
		return compagnia;
	}

	public void setCompagnia(Compagnia compagnia) {
		this.compagnia = compagnia;
	}

	@Override
	public String toString() {
		return "Impiegato [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", dataNascita=" + dataNascita + ", dataAssunzione=" + dataAssunzione + ", compagnia=" + compagnia
				+ "]";
	}

}