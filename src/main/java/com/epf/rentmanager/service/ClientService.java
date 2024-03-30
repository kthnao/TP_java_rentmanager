package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}
	
	
	public long create(Client client) throws ServiceException {
		if(client.nom() == null || client.prenom() == null) {
			throw new ServiceException("Le nom et le prénom du client sont obligatoires");
		}

		try {
			Client client1 = new Client(client.id(), client.nom().toUpperCase(), client.prenom(), client.email(), client.naissance());
			return clientDao.create(client1);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du client: " + e.getMessage());
		}

	}

	public Optional<Client> findById(long id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du client: " + e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException {
		List<Client> clients = new ArrayList<>();
		try {
			clients = clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération des clients: " + e.getMessage());
		}
		return clients;
	}

	public void delete(Client client) throws ServiceException {
		try {
			clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du client: " + e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du comptage des véhicules: " + e.getMessage());
		}
	}

	public boolean estMajeur(Client client) throws ServiceException{
		try {
			return clientDao.estMajeur(client);
		} catch (Exception e) {
			throw new ServiceException("Erreur lors de la vérification de la majorité: " + e.getMessage());
		}
	}

	public boolean emailDispo(Client client) throws ServiceException {
		List<String> emails = new ArrayList<String>();
		try{
			emails = clientDao.getAllEmail();
			for (String email : emails) {
				if (email.equals(client.email())) {
					return false;
				}
			}
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération des emails: " + e.getMessage());
		}
		return true;
	}


}
