package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Date;


public record Reservation(long id, long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {


}
