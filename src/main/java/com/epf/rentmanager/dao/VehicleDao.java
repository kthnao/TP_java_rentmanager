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
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}


	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String UPDATE_VEHICLES_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?;";

	public long create(Vehicle vehicle) throws DaoException {
		if(vehicle.constructeur() == null) {
			throw new DaoException("Le constructeur du véhicule est obligatoire");
		}
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			stmt.setString(1, vehicle.constructeur());
			stmt.setString(2, vehicle.modele());
			stmt.setInt(3, vehicle.nb_places());
			stmt.execute();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getInt("id");
				return id;
			}
			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule: " + e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(DELETE_VEHICLE_QUERY);
		){
			stmt.setLong(1, vehicle.id());
			stmt.execute();

			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule: " + e.getMessage());
		}
	}

	public Optional<Vehicle> findById(long id) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(FIND_VEHICLE_QUERY);
		){
			stmt.setLong(1, id);
			stmt.execute();
			ResultSet res = stmt.getResultSet();
			if (res.next()) {
				Vehicle vehicle = new Vehicle(
						id,
						res.getString("constructeur"),
						res.getString("modele"),
						res.getInt("nb_places")
				);
				return Optional.of(vehicle);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération du véhicule: " + e.getMessage());
		}
		return Optional.empty();
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_VEHICLES_QUERY);
		) {
			stmt.execute();
			ResultSet res = stmt.getResultSet();
			while(res.next()) {
				Vehicle vehicle = new Vehicle(
						res.getLong("id"),
						res.getString("constructeur"),
						res.getString("modele"),
						res.getInt("nb_places")
				);
				vehicles.add(vehicle);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des véhicules: " + e.getMessage());
		}
		return vehicles;

		}
	public int count() throws DaoException {
		try{
			return this.findAll().size();
		}
		catch (Exception e) {
			throw new DaoException("Erreur lors de la récupération des véhicules: " + e.getMessage());
		}
	}

	public boolean constructeurNonVide(Vehicle vehicle) throws DaoException{
		try {
			return vehicle.constructeur() != null && !vehicle.constructeur().isEmpty();
		} catch (Exception e) {
			throw new DaoException("Erreur lors de la validation du constructeur: " + e.getMessage());
		}
	}

	public boolean modeleNonVide(Vehicle vehicle) throws DaoException{
		try {
			return vehicle.modele() != null && !vehicle.modele().isEmpty();
		} catch (Exception e) {
			throw new DaoException("Erreur lors de la validation du modèle: " + e.getMessage());
		}
	}

	public boolean nbPlacesValide(Vehicle vehicle) throws DaoException {
		try {
			return vehicle.nb_places() >= 2 && vehicle.nb_places() <= 9;
		} catch (Exception e) {
			throw new DaoException("Erreur lors de la validation du nombre de places: " + e.getMessage());
		}
	}

	public void update(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(UPDATE_VEHICLES_QUERY)) {
			stmt.setString(1, vehicle.constructeur());
			stmt.setString(2, vehicle.modele());
			stmt.setInt(3, vehicle.nb_places());
			stmt.setLong(4, vehicle.id());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la mise à jour du véhicule.");
		}
	}


}
