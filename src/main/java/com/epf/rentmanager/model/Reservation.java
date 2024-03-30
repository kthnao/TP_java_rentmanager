package com.epf.rentmanager.model;

import com.epf.rentmanager.service.ReservationService;

import java.time.LocalDate;
import java.util.Date;


public record Reservation(long id, long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {
    public static ReservationService instance;

    public Reservation() {
        this(0, 0, 0, null, null);
    }

    public Reservation(long id, long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Informations sur la réservation :" +
                ", ID : " + id + "\n" +
                "ID du client : " + client_id +
                ", ID du véhicule : " + vehicle_id + "\n" +
                "Date de début : " + debut +
                ", Date de fin : " + fin;
    }


}
