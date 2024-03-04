package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;

	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}

	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}

		return instance;
	}


	public long create(Vehicle vehicle) throws ServiceException {
		if (vehicle.constructeur() == null) {
			throw new ServiceException("Le constructeur du véhicule est obligatoire");
		}
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du véhicule: " + e.getMessage());
		}

	}

	public Optional<Vehicle> findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du véhicule: " + e.getMessage());
		}

	}

	public List<Vehicle> findAll() throws ServiceException {
		List<Vehicle> vehicles = new ArrayList<>();
		try {
			vehicles = vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération des véhicules: " + e.getMessage());
		}
		return vehicles;
	}

	public void delete(Vehicle vehicle) throws ServiceException {
		try {
			 vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du véhicule: " + e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du comptage des véhicules: " + e.getMessage());
		}
	}

	
}
