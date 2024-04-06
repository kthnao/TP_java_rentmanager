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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long resId = Long.parseLong(req.getParameter("id"));
        List<Vehicle> vehicles = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        List<Long> nbJours = new ArrayList<>();
        Client client = null;
        Vehicle vehicle = null;
        long nbJoursTotal = 0;
        try {
            List<Reservation> rents = reservationService.findAll();

            for (Reservation rent : rents) {
                nbJoursTotal = java.time.temporal.ChronoUnit.DAYS.between(rent.debut(), rent.fin()) + 1;
                vehicle = vehicleService.findById(rent.vehicle_id()).get();
                client = clientService.findById(rent.client_id()).get();
                nbJours.add(nbJoursTotal);
                vehicles.add(vehicle);
                clients.add(client);
                if (!vehicles.contains(vehicle)) {
                    vehicles.add(vehicle);
                }
                if (!clients.contains(client)) {
                    clients.add(client);
                }
            }


            req.setAttribute("client", client);
            req.setAttribute("vehicle", vehicle);
            req.setAttribute("rents", rents);
            req.setAttribute("clients", clients);
            req.setAttribute("vehicles", vehicles);
            req.setAttribute("nbJours", nbJours);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/rents/details.jsp");
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
