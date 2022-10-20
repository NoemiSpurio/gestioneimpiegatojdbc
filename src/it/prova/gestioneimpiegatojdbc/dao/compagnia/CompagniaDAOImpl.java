package it.prova.gestioneimpiegatojdbc.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.dao.AbstractMySQLDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {
	
	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Compagnia> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setId(rs.getLong("id"));
				compagniaTemp.setRagionesociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));

				result.add(compagniaTemp);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setId(rs.getLong("id"));
					result.setRagionesociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					result.setDataFondazione(rs.getDate("datafondazione"));
				} else {
					result = null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Compagnia input) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE compagnia SET ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {

			ps.setString(1, input.getRagionesociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia input) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into compagnia (ragionesociale, fatturatoannuo, datafondazione) values (?,?,?);")) {
			ps.setString(1, input.getRagionesociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));
			result = ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from compagnia where id = ?;")) {

			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Compagnia> result = new ArrayList<>();

		String query = "select * from compagnia where ";

		if (!(input.getRagionesociale() == null || input.getRagionesociale().isBlank()))
			query += "ragionesociale like '" + input.getRagionesociale() + "%' and ";
		if (!(input.getFatturatoAnnuo() == null))
			query += "fatturatoannuo > '" + input.getFatturatoAnnuo() + "' and ";
		if (!(input.getDataFondazione() == null))
			query += "datafondazione > '" + new java.sql.Date(input.getDataFondazione().getTime()) + "' and ";
		query += "true;";

		try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery(query)) {

			while (rs.next()) {
				Compagnia compagniaTmp = new Compagnia();
				compagniaTmp.setId(rs.getLong("id"));
				compagniaTmp.setRagionesociale(rs.getString("ragionesociale"));
				compagniaTmp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTmp.setDataFondazione(rs.getDate("datafondazione"));

				result.add(compagniaTmp);
			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public List<Impiegato> findAllByDataAssunzioneMaggioreDi(Date dataInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (dataInput == null)
			throw new Exception("Dato inserito non valido.");

		List<Impiegato> result = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from compagnia c inner join impiegato i on c.id = i.compagnia_id where i.dataassunzione > ?;")) {
			ps.setDate(1, new java.sql.Date(dataInput.getTime()));

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Impiegato impiegatoTmp = new Impiegato();
					Compagnia compagniaTmp = new Compagnia();

					impiegatoTmp.setId(rs.getLong("i.id"));
					impiegatoTmp.setNome(rs.getString("nome"));
					impiegatoTmp.setCognome(rs.getString("cognome"));
					impiegatoTmp.setCodiceFiscale(rs.getString("codicefiscale"));
					impiegatoTmp.setDataNascita(rs.getDate("datanascita"));
					impiegatoTmp.setDataAssunzione(rs.getDate("dataassunzione"));

					compagniaTmp.setId(rs.getLong("c.id"));
					compagniaTmp.setRagionesociale(rs.getString("ragionesociale"));
					compagniaTmp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTmp.setDataFondazione(rs.getDate("datafondazione"));

					impiegatoTmp.setCompagnia(compagniaTmp);
					result.add(impiegatoTmp);
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public List<Compagnia> findAllByRagioneSocialeContiene(String stringaContenuta) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (stringaContenuta == null || stringaContenuta.isBlank())
			throw new Exception("Valore non ammesso.");

		List<Compagnia> result = new ArrayList<>();

		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia where ragionesociale like ?;")) {

			String contenuto = "%" + stringaContenuta + "%";
			ps.setString(1, contenuto);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Compagnia compagniaTmp = new Compagnia();
					compagniaTmp.setId(rs.getLong("id"));
					compagniaTmp.setRagionesociale(rs.getString("ragionesociale"));
					compagniaTmp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTmp.setDataFondazione(rs.getDate("datafondazione"));

					result.add(compagniaTmp);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return result;
	}

}
