package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	public long create(Client client) throws DaoException, ServiceException {
		if(client.nom() == null || client.prenom() == null) {
			throw new ServiceException("Le nom et le prénom du client sont obligatoires");
		}

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			stmt.setString(1, client.nom());
			stmt.setString(2, client.prenom());
			stmt.setString(3, client.email());
			stmt.setDate(4, Date.valueOf(client.naissance()));
			stmt.execute();
			ResultSet resultSet = stmt.getGeneratedKeys();
			int id = 0;
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
			return id;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client: " + e.getMessage());
		}
	}
	
	public long delete(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(DELETE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			stmt.setLong(1, client.id());
			stmt.execute();

			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client: " + e.getMessage());
		}
	}

	public Optional<Client> findById(long id) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(FIND_CLIENT_QUERY);
		){
			stmt.setLong(1, id);
			stmt.execute();
			ResultSet res = stmt.getResultSet();
			while (res.next()) {
				Client client = new Client(
					id,
					res.getString("nom"),
					res.getString(	"prenom"),
					res.getString("email"),
					res.getDate("naissance").toLocalDate()
				);
				return Optional.of(client);
			}


		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client: " + e.getMessage());
		}
		return Optional.empty();
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(FIND_CLIENTS_QUERY);
		){
			stmt.execute();
			ResultSet res = stmt.getResultSet();
	while(res.next()) {
				Client client = new Client(
					res.getLong("id"),
					res.getString("nom"),
					res.getString("prenom"),
					res.getString("email"),
					res.getDate("naissance").toLocalDate()
				);
				clients.add(client);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client: " + e.getMessage());
		}
		return clients;
	}



}

