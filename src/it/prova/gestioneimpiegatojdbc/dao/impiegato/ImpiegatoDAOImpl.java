package it.prova.gestioneimpiegatojdbc.dao.impiegato;

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

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {
	
	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTmp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from impiegato")) {

			while (rs.next()) {
				impiegatoTmp = new Impiegato();
				impiegatoTmp.setId(rs.getLong("i.id"));
				impiegatoTmp.setNome(rs.getString("nome"));
				impiegatoTmp.setCognome(rs.getString("cognome"));
				impiegatoTmp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTmp.setDataNascita(rs.getDate("datanascita"));
				impiegatoTmp.setDataAssunzione(rs.getDate("dataassunzione"));
				impiegatoTmp.getCompagnia().setId(rs.getLong("compagnia_id"));
				result.add(impiegatoTmp);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato result = null;

		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id = ?;")) {

			ps.setLong(1, idInput);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					result = new Impiegato();
					result.setId(rs.getLong("i.id"));
					result.setNome(rs.getString("nome"));
					result.setCognome(rs.getString("cognome"));
					result.setCodiceFiscale(rs.getString("codicefiscale"));
					result.setDataNascita(rs.getDate("datanascita"));
					result.setDataAssunzione(rs.getDate("dataassunzione"));
					result.getCompagnia().setId(rs.getLong("compagnia_id"));
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public int update(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"update impiegato set (nome,cognome,codicefiscale,datanascita,dataassunzione,compagnia_id) values (?,?,?,?,?,?);")) {

			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(input.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataAssunzione().getTime()));
			ps.setLong(6, input.getCompagnia().getId());
			result = ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Impiegato input) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into impiegato (nome,cognome,codicefiscale,datanascita,dataassunzione,compagnia_id) values (?,?,?,?,?,?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(input.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataAssunzione().getTime()));
			ps.setLong(6, input.getCompagnia().getId());
			result = ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;

		try (PreparedStatement ps = connection.prepareStatement("delete from impiegato where id = ?;")) {

			ps.setLong(1, input.getId());
			result = ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		List<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTmp = null;

		String query = "select * from impiegato where ";

		if (!(input.getNome() == null || input.getNome().isBlank()))
			query += "nome like '" + input.getNome() + "%' and ";
		if (!(input.getCognome() == null || input.getCognome().isBlank()))
			query += "cognome like '" + input.getCognome() + "%' and ";
		if (!(input.getCodiceFiscale() == null || input.getCodiceFiscale().isBlank()))
			query += "codicefiscale like '" + input.getCodiceFiscale() + "%' and ";
		if (!(input.getDataNascita() == null))
			query += "datanascita > '" + new java.sql.Date(input.getDataNascita().getTime()) + "' and ";
		if (!(input.getDataAssunzione() == null))
			query += "dataassunzione > '" + new java.sql.Date(input.getDataAssunzione().getTime()) + "' and ";
		if (!(input.getCompagnia() == null))
			query += "compagnia_id = " + input.getCompagnia().getId() + " and ";
		query += "true;";

		try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery(query)) {

			while (rs.next()) {
				impiegatoTmp = new Impiegato();
				impiegatoTmp.setId(rs.getLong("i.id"));
				impiegatoTmp.setNome(rs.getString("nome"));
				impiegatoTmp.setCognome(rs.getString("cognome"));
				impiegatoTmp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTmp.setDataNascita(rs.getDate("datanascita"));
				impiegatoTmp.setDataAssunzione(rs.getDate("dataassunzione"));
				impiegatoTmp.getCompagnia().setId(rs.getLong("compagnia_id"));
				result.add(impiegatoTmp);
			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (compagniaInput == null)
			throw new Exception("Dato inserito non valido.");
		List<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTmp = null;

		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where compagnia_id = ?;")) {

			ps.setLong(1, compagniaInput.getId());

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					impiegatoTmp = new Impiegato();
					impiegatoTmp.setId(rs.getLong("i.id"));
					impiegatoTmp.setNome(rs.getString("nome"));
					impiegatoTmp.setCognome(rs.getString("cognome"));
					impiegatoTmp.setCodiceFiscale(rs.getString("codicefiscale"));
					impiegatoTmp.setDataNascita(rs.getDate("datanascita"));
					impiegatoTmp.setDataAssunzione(rs.getDate("dataassunzione"));
					impiegatoTmp.getCompagnia().setId(rs.getLong("compagnia_id"));
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
	public int countByDataFondazioneCompagniaGreaterThan(Date dataInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (dataInput == null)
			throw new Exception("Dato inserito non valido.");
		int result = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(i.id) as mycount from compagnia c inner join impiegato i on i.compagnia_id = c.id where c.datafondazione > ?;")) {
			ps.setDate(1, new java.sql.Date(dataInput.getTime()));

			try (ResultSet rs = ps.executeQuery()) {
				result = rs.getInt("mycount");
			}
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzione() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTmp = null;

		try (Statement s = connection.createStatement();
				ResultSet rs = s.executeQuery(
						"select * from impiegato i inner join compagnia c on i.compagnia_id = c.id where i.dataassunzione < c.datafondazione;")) {

			while (rs.next()) {
				impiegatoTmp = new Impiegato();
				impiegatoTmp.setId(rs.getLong("i.id"));
				impiegatoTmp.setNome(rs.getString("nome"));
				impiegatoTmp.setCognome(rs.getString("cognome"));
				impiegatoTmp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTmp.setDataNascita(rs.getDate("datanascita"));
				impiegatoTmp.setDataAssunzione(rs.getDate("dataassunzione"));
				impiegatoTmp.getCompagnia().setId(rs.getLong("compagnia_id"));
				result.add(impiegatoTmp);
			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return result;
	}

}
