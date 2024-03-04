package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Date;


public record Reservation(long id, long clientId, long vehiculeId, LocalDate debut, LocalDate fin) {


}
