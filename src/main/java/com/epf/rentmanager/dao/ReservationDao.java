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

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		if(reservation.client_id() == 0 || reservation.vehicle_id() == 0) {
			throw new DaoException("Le client et le véhicule sont obligatoires");
		}
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
		) {
			stmt.setLong(1, reservation.client_id());
			stmt.setLong(2, reservation.vehicle_id());
			stmt.setDate(3, Date.valueOf(reservation.debut()));
			stmt.setDate(4, Date.valueOf(reservation.fin()));
			stmt.execute();
			ResultSet resultSet = stmt.getGeneratedKeys();
			int id = 0;
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de la réservation: " + e.getMessage());
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
		) {
			stmt.setLong(1, reservation.id());
			stmt.execute();

			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de la réservation: " + e.getMessage());
		}
	}

	public Optional<Reservation> findById(long id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
		) {
			stmt.setLong(1, id);
			stmt.execute();
			ResultSet res = stmt.getResultSet();
			while(res.next()) {
				Reservation reservation = new Reservation(
						id,
						res.getLong("client_id"),
						res.getLong("vehicle_id"),
						res.getDate("debut").toLocalDate(),
						res.getDate("fin").toLocalDate()
				);
				return Optional.of(reservation);
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}
	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);){

			stmt.setLong(1, clientId);
			stmt.execute();
			ResultSet res = stmt.getResultSet();
			while(res.next()) {
				Reservation reservation = new Reservation(
						res.getLong("id"),
						clientId,
						res.getLong("vehicle_id"),
						res.getDate("debut").toLocalDate(),
						res.getDate("fin").toLocalDate()
				);
				reservations.add(reservation);
			}
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
		){
			stmt.setLong(1, vehicleId);
			stmt.execute();
			ResultSet res = stmt.getResultSet();
			while(res.next()) {
				Reservation reservation = new Reservation(
						res.getLong("id"),
						res.getLong("client_id"),
						vehicleId,
						res.getDate("debut").toLocalDate(),
						res.getDate("fin").toLocalDate()
				);
				reservations.add(reservation);
			}

			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
		) {
			stmt.execute();
			ResultSet res = stmt.getResultSet();
			while(res.next()) {
				Reservation reservation = new Reservation(
						res.getLong("id"),
						res.getLong("client_id"),
						res.getLong("vehicle_id"),
						res.getDate("debut").toLocalDate(),
						res.getDate("fin").toLocalDate()
				);
				reservations.add(reservation);
			}

			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

	public int count() throws DaoException {
		try{
			return this.findAll().size();
		}
		catch (Exception e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

	public boolean vehicleDispo(Reservation res) throws DaoException {
		try {
			List<Reservation> rents = this.findResaByVehicleId(res.vehicle_id());
			for (Reservation r : rents) {
				if (r.debut().isBefore(res.debut()) && r.fin().isAfter(res.fin())) {
					return false;
				}
				if (r.debut().isAfter(res.debut()) && r.fin().isBefore(res.fin())) {
					return false;
				}
				if (r.debut().isAfter(res.debut()) && r.debut().isBefore(res.fin())) {
					return false;
				}
				if (r.debut().isBefore(res.fin()) && r.fin().isAfter(res.fin())) {
					return false;
				}
				if (r.debut().isBefore(res.debut()) && r.fin().isAfter(res.debut())) {
					return false;
				}
				if (r.fin().isAfter(res.debut()) && r.fin().isBefore(res.fin())) {
					return false;
				}
				if (r.fin().equals(res.debut())){
					return false;
				}
				if (r.fin().equals(res.fin())){
					return false;
				}
				if (r.debut().equals(res.debut())){
					return false;
				}
				if (r.debut().equals(res.fin())){
					return false;
				}
			}
			return true;
		} catch (Exception e){
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

	public boolean rentMaxTrente(Reservation res) throws DaoException {
		try {
			List<Reservation> rents = this.findResaByVehicleId(res.vehicle_id());
			long totResAffil = 0; // Nombre total de jours d'affilé réservés par l'utilisateur pour cette voiture
			LocalDate lastEndDate = null; // Date de fin de la dernière réservation faite par l'utilisateur pour cette voiture
			long dureeResa = java.time.temporal.ChronoUnit.DAYS.between(res.debut(), res.fin()) + 1; // Durée de la réservation en cours
			if (dureeResa >= 30) {
				return false;
			}
			else {
				rents.add(res);
				for (Reservation r : rents) {
					if (r.vehicle_id() == res.vehicle_id()) {
						if (lastEndDate != null && r.debut().isEqual(lastEndDate.plusDays(1))) {
							totResAffil += java.time.temporal.ChronoUnit.DAYS.between(r.debut(), r.fin()) + 1;
						} else {
							totResAffil = 0;
							totResAffil += java.time.temporal.ChronoUnit.DAYS.between(r.debut(), r.fin()) + 1;
						}
						lastEndDate = r.fin();
					}
					if (totResAffil >= 30) {
						return false;
					}
				}
				return true;
			}
		} catch (Exception e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}
/*
	public boolean rentMaxTrente(Reservation res) throws DaoException {
		try {
			List<Reservation> rents = this.findResaByVehicleId(res.vehicle_id());
			long totResAffil = 0; // Nombre total de jours d'affilé réservés par l'utilisateur pour cette voiture
			boolean suiteRes = false; // Indique si les réservations faite par l'utilisateur se suivent
			LocalDate lastEndDate = null; // Date de fin de la dernière réservation faite par l'utilisateur pour cette voiture



			for (Reservation r : rents) {

				if (r.client_id() == res.client_id()) {

					if (lastEndDate != null && r.debut().isEqual(lastEndDate.plusDays(1))) {
						totResAffil += java.time.temporal.ChronoUnit.DAYS.between(r.debut(), r.fin()) + 1;
						suiteRes = true;
					} else {
						suiteRes = false;
						totResAffil = 0;
						totResAffil += java.time.temporal.ChronoUnit.DAYS.between(r.debut(), r.fin()) + 1;
					}
					lastEndDate = r.fin();
				}
				// Si le total des jours réservés dépasse 7, la réservation en cours ne peut pas être effectuée
				if (totResAffil > 7) {
					return false;
				}

			}
			return true;
		} catch (Exception e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

*/

}
