package com.epf.rentmanager.ui.servlet.Reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rents/list")
public class ReservationListServlet extends HttpServlet {

    /**
     *
     */

    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        List<Client> clients = new ArrayList<Client>();;
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        try {
            reservations = reservationService.findAll();
            for (Reservation reservation : reservations) {
                    Vehicle vehicle = vehicleService.findById(reservation.vehicle_id()).get();
                    vehicles.add(vehicle);
                    Client client = clientService.findById(reservation.client_id()).get();
                    clients.add(client);
            }
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        req.setAttribute("rents", reservations );
        req.setAttribute("vehicles", vehicles);
        req.setAttribute("clients", clients);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(req, resp);

    }

}
