package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class CliRequest {

    /*
private static final ClientService clientService = ClientService.getInstance();
private static final VehicleService vehicleService = VehicleService.getInstance();

AnnotationConfigApplicationContext(AppConfiguration.class);
ClientService clientService = context.getBean(ClientService.class);
VehicleService vehicleService = context.getBean(VehicleService.class);
ReservationService reservationService = context.getBean(ReservationService.class);



    public static void main(String[] args) throws ServiceException {
            int choice = 0;

                do {
                    IOUtils.print("Bienvenue dans l'application RentManager ! Veuillez choisir un service parmi les options suivantes :");
                    IOUtils.print("1. Créer un client");
                    IOUtils.print("2. Lister tous les clients");
                    IOUtils.print("3. Créer un véhicule");
                    IOUtils.print("4. Lister tous les véhicules");
                    IOUtils.print("5. Supprimer un client");
                    IOUtils.print("6. Supprimer un véhicule");
                    IOUtils.print("7. Créer une réservation");
                    IOUtils.print("8. Lister toutes les réservations");
                    IOUtils.print("9. Lister toutes les Réservations associées à un Client donné.");
                    IOUtils.print("10. Lister toutes les Réservations associées à un Véhicule donné.");
                    IOUtils.print("11. Supprimer une réservation");
                    IOUtils.print("12. Sortir de l'application");
                    choice = IOUtils.readInt("Veuillez entrer l'option choisie : ");

                    switch (choice) {
                        case 1:
                            createClient();
                            break;
                        case 2:
                            listClients();
                            break;
                        case 3:
                            createVehicle();
                            break;
                        case 4:
                            listVehicles();
                            break;
                        case 5:
                            deleteClient();
                            break;
                        case 6:
                            deleteVehicle();
                            break;
                        case 7:
                            createReservation();
                            break;
                        case 8:
                            listReservations();
                            break;
                        case 9:
                            listReservationsByClient();
                            break;
                        case 10:
                            listReservationsByVehicle();
                            break;
                        case 11:
                            deleteReservation();
                            break;
                        case 12:
                            break;

                    }
                }while (choice != 12);


        }


    private static void createClient() throws ServiceException {
            Long Id_trap = 1L;
            String nom = IOUtils.readString("Veuillez entrer le nom du client : ", true);
            String prenom = IOUtils.readString("Veuillez entrer le prénom du client :  ", true);
            String email = IOUtils.readString("Veuillez entrer l'email du client : ", true);
            LocalDate naissance = IOUtils.readDate("Veuillez entrer la date de naissance du client (jj/mm/aaaa): ", true);
            Client client = new Client(Id_trap,nom, prenom, email, naissance);
            clientService.create(client);
        }

    private static void listClients() throws ServiceException {
        List<Client> clients = clientService.findAll();
        for (Client client : clients) {
            IOUtils.print("Liste des clients : " );
            IOUtils.print(client.toString());
        }


    }

    private static void deleteClient() throws ServiceException {
        Long id = IOUtils.readLong("Veuillez entrer l'identifiant du client à supprimer : ");
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            clientService.delete(client.get());
        } else {
            IOUtils.print("Client not found");
        }

    }

    private static void createVehicle() throws ServiceException {
        Long Id_trap = 1L;
        String constructeur = IOUtils.readString("Veuillez entrer le constructeur du véhicule : ", true);
        String modele = IOUtils.readString("Veuillez entrer le modèle du véhicule : ", true);
        int nb_places = IOUtils.readInt("Veuillez entrer le nombre de places du véhicule : ");
        Vehicle vehicle = new Vehicle(Id_trap,constructeur, modele, nb_places);
        vehicleService.create(vehicle);
    }

    private static void listVehicles() throws ServiceException {
        List<Vehicle> vehicles = vehicleService.findAll();
        for (Vehicle vehicle : vehicles) {
            IOUtils.print("Liste des véhicules : " );
            IOUtils.print(vehicle.toString());
        }
    }

    private static void deleteVehicle() throws ServiceException {
        Long id = IOUtils.readLong("Veuillez entrer l'identifiant du véhicule à supprimer : ");
        Optional<Vehicle> vehicle = vehicleService.findById(id);
        if (vehicle.isPresent()) {
            vehicleService.delete(vehicle.get());
        } else {
            IOUtils.print("Vehicle not found");
        }

    }
    private static void createReservation() throws ServiceException {
        Long Id_trap = 1L;
        Long id_v = IOUtils.readLong("Veuillez entrer l'identifiant du véhicule : ");
        Long id_c = IOUtils.readLong("Veuillez entrer l'identifiant du client : ");
        LocalDate debut = IOUtils.readDate("Veuillez entrer la date de début de réservation (jj/mm/aaaa): ", true);
        LocalDate fin = IOUtils.readDate("Veuillez entrer la date de fin de réservation (jj/mm/aaaa): ", true);
        Reservation reservation = new Reservation(Id_trap,id_c, id_v, debut, fin);
        ReservationService.getInstance().create(reservation);
    }

    private static void listReservations() throws ServiceException {
        List<Reservation> reservations = ReservationService.getInstance().findAll();
        for (Reservation reservation : reservations) {
            IOUtils.print("Liste des réservations : " );
            IOUtils.print(reservation.toString());
        }
    }
    private static void listReservationsByClient() throws ServiceException {
            long id = IOUtils.readLong("Veuillez entrer l'identifiant du client concerné : ");
        List<Reservation> reservations = ReservationService.getInstance().findReservationsByClient(id);
        for (Reservation reservation : reservations) {
            IOUtils.print("Liste des réservations : ");
            IOUtils.print(reservation.toString());
        }
    }

    private static void listReservationsByVehicle() throws ServiceException {
        long id = IOUtils.readLong("Veuillez entrer l'identifiant du véhicucle concerné : ");
        List<Reservation> reservations = ReservationService.getInstance().findReservationsByVehicle(id);
        for (Reservation reservation : reservations) {
            IOUtils.print("Liste des réservations : ");
            IOUtils.print(reservation.toString());
        }
    }

    private static void deleteReservation() throws ServiceException {
        Long id = IOUtils.readLong("Veuillez entrer l'identifiant de la réservation à supprimer : ");
        Optional<Reservation> reservation = ReservationService.getInstance().findById(id);
        if (reservation.isPresent()) {
            ReservationService.getInstance().delete(reservation.get());
        } else {
            IOUtils.print("Vehicle not found");
        }


    }*/
}

