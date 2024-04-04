package com.epf.rentmanager.ui.servlet;

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
@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
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
        long clientId = Long.parseLong(req.getParameter("id"));
        Client client;
        Vehicle vehicle;
        List<Vehicle> vehicles = new ArrayList<>();
        List<String> vehicles_names = new ArrayList<>();
        try {
            List<Reservation> rents = reservationService.findReservationsByClient(clientId);
            client = clientService.findById(clientId).get();
            for (Reservation rent : rents) {
                long vehicleId=rent.vehicle_id();
                 vehicle=vehicleService.findById(vehicleId).get();
                if(!vehicles.contains(vehicle)){
                    vehicles.add(vehicle);
                    vehicles_names.add(vehicle.constructeur()+" "+vehicle.modele());
                }
            }
            int nbRents = rents.size();
            int nbVehicle = vehicles.size();

            req.setAttribute("client", client);
            req.setAttribute("listRents", rents);
            req.setAttribute("listVehicles", rents);
            req.setAttribute("nbRents", nbRents);
            req.setAttribute("nbVehicle", nbVehicle);
            req.setAttribute("vehicles_names", vehicles_names);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // resp.sendRedirect(req.getContextPath() + "/users");
    }

}
