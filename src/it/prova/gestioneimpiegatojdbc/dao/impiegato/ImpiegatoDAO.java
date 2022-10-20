package it.prova.gestioneimpiegatojdbc.dao.impiegato;

import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.dao.IBaseDAO;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato> {

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception;

	public int countByDataFondazioneCompagniaGreaterThan(Date dataInput) throws Exception;

	public List<Impiegato> findAllErroriAssunzione() throws Exception;

}
