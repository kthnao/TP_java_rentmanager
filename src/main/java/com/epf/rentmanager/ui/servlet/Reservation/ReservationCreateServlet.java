package com.epf.rentmanager.ui.servlet.Reservation;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Client> clients = new ArrayList<Client>();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        try {
            clients = clientService.findAll();
            vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        req.setAttribute("clients", clients);
        req.setAttribute("vehicles", vehicles);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.FRANCE);
        Reservation reservation = new Reservation(
                0L,
                Long.parseLong(req.getParameter("client")),
                Long.parseLong(req.getParameter("car")),
                LocalDate.parse(req.getParameter("begin"),formatter),
                LocalDate.parse(req.getParameter("end"),formatter)
        );
        boolean voitureDispo = false;
        boolean rentRules = false;

        try {
            voitureDispo = reservationService.vehicleDispo(reservation);
            if (voitureDispo) rentRules = reservationService.rentRules(reservation);
            if (rentRules) reservationService.create(reservation);

        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }

        if(!voitureDispo) {
            req.setAttribute("rentError", "La voiture n'est pas disponible durant cette période");
            doGet(req, resp);
        }
        else if(!rentRules) {
            req.setAttribute("rentError", "Le véhicule ne peux pas être réservé plus de 7 jours de suite par le même\n" +
                    "utilisateur et la location d'une voiture ne peut pas dépasser 30 jours");
            doGet(req, resp);
        }
        else{
            resp.sendRedirect(req.getContextPath() + "/rents/list");
        }
    }
}

