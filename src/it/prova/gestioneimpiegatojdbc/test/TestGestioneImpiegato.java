package it.prova.gestioneimpiegatojdbc.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.connection.MyConnection;
import it.prova.gestioneimpiegatojdbc.dao.Constants;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAO;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAO;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAOImpl;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class TestGestioneImpiegato {

	public static void main(String[] args) {

		ImpiegatoDAO impiegatoDAOInstance = null;
		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {

			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
			compagniaDAOInstance = new CompagniaDAOImpl(connection);

			testInsertCompagnia(compagniaDAOInstance);

			testListCompagnia(compagniaDAOInstance);

			testGetCompagnia(compagniaDAOInstance);

			testUpdateCompagnia(compagniaDAOInstance);

			//testDeleteCompagnia(compagniaDAOInstance);

			testFindByExampleCompagnia(compagniaDAOInstance);

			testFindAllByRagioneSocialeContiene(compagniaDAOInstance);
			
			testInsertImpiegato(impiegatoDAOInstance, compagniaDAOInstance);
			
			testFindAllByDataAssunzioneMaggioreDi(compagniaDAOInstance);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInsertCompagnia inizio.............");
		int elementiInseriti = compagniaDAOInstance.insert(
				new Compagnia("ragionesociale1", 10000, new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1950")));
		if (elementiInseriti < 1)
			throw new RuntimeException("testInsertUser FAILED: Inserimento non andato a buon fine.");
		System.out.println(".......testInsertUser fine: PASSED.............");

	}

	private static void testListCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testListCompagnia inizio.............");
		List<Compagnia> elencoPresenti = compagniaDAOInstance.list();
		if (elencoPresenti.isEmpty())
			throw new RuntimeException("testListCompagnia FAILED: lista non caricata correttamente.");
		System.out.println(".......testListCompagnia fine: PASSED.............");

	}

	private static void testGetCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testGetCompagnia inizio.............");
		List<Compagnia> elencoPresenti = compagniaDAOInstance.list();
		if (elencoPresenti.isEmpty())
			throw new RuntimeException("testGetCompagnia FAILED: nessuna entry presente.");
		Compagnia result = elencoPresenti.get(0);
		if (result == null)
			throw new RuntimeException("testGetCompagnia FAILED: get non andato a buon fine.");
		System.out.println(".......testGetCompagnia fine: PASSED.............");

	}

	private static void testUpdateCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testUpdateCompagnia inizio.............");
		List<Compagnia> elencoPresenti = compagniaDAOInstance.list();
		if (elencoPresenti.isEmpty())
			throw new RuntimeException("testUpdateCompagnia FAILED: nessuna entry presente.");
		Compagnia elementoDaAggiornare = new Compagnia(elencoPresenti.get(0).getId(), "ragionesociale2", 10000,
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1950"));
		int result = compagniaDAOInstance.update(elementoDaAggiornare);
		if (result == 0)
			throw new RuntimeException("testUpdateCompagnia FAILED: update non andato a buon fine.");
		System.out.println(".......testUpdateCompagnia fine: PASSED.............");

	}

	private static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testDeleteCompagnia inizio.............");
		compagniaDAOInstance.insert(
				new Compagnia("ragionesociale1", 10000, new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1950")));
		List<Compagnia> elencoPresenti = compagniaDAOInstance.list();
		int result = compagniaDAOInstance.delete(elencoPresenti.get(0));
		if (result == 0)
			throw new RuntimeException("testDeleteCompagnia FAILED: elemento non eliminato.");
		System.out.println(".......testDeleteCompagnia fine: PASSED.............");
	}

	private static void testFindByExampleCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindByExampleCompagnia inizio.............");
		List<Compagnia> result = compagniaDAOInstance.findByExample(new Compagnia("ra", 0, null));
		if (result == null)
			throw new RuntimeException("testFindByExampleCompagnia FAILED: ricerca non effettuata correttamente.");
		System.out.println(".......testFindByExampleCompagnia fine: PASSED.............");
	}

	private static void testFindAllByRagioneSocialeContiene(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindAllByRagioneSocialeContiene inizio.............");
		List<Compagnia> result = compagniaDAOInstance.findAllByRagioneSocialeContiene("gi");
		if (result.isEmpty())
			throw new RuntimeException(
					"testFindAllByRagioneSocialeContiene FAILED: ricerca non effettuata correttamente.");
		System.out.println(".......testFindAllByRagioneSocialeContiene fine: PASSED.............");
	}

	private static void testInsertImpiegato(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {
		System.out.println(".......testInsertImpiegato inizio.............");
		List<Compagnia> elencoCompagnie = compagniaDAOInstance.list();
		Impiegato impiegatoTest1 = new Impiegato("Mario", "Rossi", "ABC123",
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1950"),
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"), elencoCompagnie.get(0));
		Impiegato impiegatoTest2 = new Impiegato("Mario", "Rossi", "ABC123",
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1950"),
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1890"), elencoCompagnie.get(0));
		int elementiInseriti = impiegatoDAOInstance.insert(impiegatoTest1);
		elementiInseriti += impiegatoDAOInstance.insert(impiegatoTest2);
		if(elementiInseriti != 2)
			throw new RuntimeException("testInsertImpiegato FAILED: inserimenti non andati a buon fine.");
		System.out.println(".......testInsertImpiegato fine: PASSED.............");
	}
	
	private static void testFindAllByDataAssunzioneMaggioreDi(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi inizio.............");
		Date dataInput = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1890");
		List<Impiegato> result = compagniaDAOInstance.findAllByDataAssunzioneMaggioreDi(dataInput);
		if(result.isEmpty())
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi FAILED: ricerca non andata a buon fine.");
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi fine: PASSED.............");
	}

}
