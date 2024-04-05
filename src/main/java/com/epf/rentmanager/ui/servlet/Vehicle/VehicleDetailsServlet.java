package com.epf.rentmanager.ui.servlet.Vehicle;


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

@WebServlet("/cars/details")

public class VehicleDetailsServlet extends HttpServlet {

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

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long vehicleId = Long.parseLong(req.getParameter("id"));
        List<Client> clients = new ArrayList<>();

        try {
            List<Reservation> rents = reservationService.findReservationsByVehicle(vehicleId);
            Vehicle vehicle = null;
            for (Reservation rent : rents) {
                Client client = clientService.findById(rent.client_id()).get();
                vehicle = vehicleService.findById(rent.vehicle_id()).get();
                clients.add(client);
                if (!clients.contains(client)) {
                    clients.add(client);
                }
            }
            int nbRents = rents.size();
            int nbClients = clients.size();

            req.setAttribute("vehicle", vehicle);
            req.setAttribute("clients", clients);
            req.setAttribute("rents", rents);
            req.setAttribute("nbRents", nbRents);
            req.setAttribute("nbClients", nbClients);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
        }

    }

}
