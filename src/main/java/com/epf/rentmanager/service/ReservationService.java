package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao){
    this.reservationDao = reservationDao;
}




public long create(Reservation reservation) throws ServiceException {
        if (reservation.client_id() == 0 || reservation.vehicle_id() == 0) {
            throw new ServiceException("Le client et le véhicule sont obligatoires");
        }
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création de la réservation: " + e.getMessage());
        }

    }

    public long delete(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        List<Reservation> reservations = new ArrayList<>();
        try {
            reservations = reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
        return reservations;
    }

    public Optional<Reservation> findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération de la réservation: " + e.getMessage());
        }
    }

    public List<Reservation> findReservationsByClient(long client_id) throws ServiceException {
        List<Reservation> reservations = null;
        try {
            reservations = reservationDao.findResaByClientId(client_id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
        return reservations;
    }

    public List<Reservation> findReservationsByVehicle(long vehicle_id) throws ServiceException {
        List<Reservation> reservations = null;
        try {
            reservations = reservationDao.findResaByVehicleId(vehicle_id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
        return reservations;
    }

    public void deleteReservations(Reservation reservation) throws ServiceException {
        try {
            reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }
    public int count() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des véhicules: " + e.getMessage());
        }
    }

    public boolean vehicleDispo(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.vehicleDispo(reservation);
            } catch (DaoException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean rentRules(Reservation res) throws ServiceException{
        try{
            return reservationDao.rentRules(res);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Reservation reservation) throws ServiceException {
        try {
            reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la mise à jour de la réservation.");
        }
    }


}
