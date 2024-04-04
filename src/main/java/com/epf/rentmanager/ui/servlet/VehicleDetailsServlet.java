package com.epf.rentmanager.ui.servlet;


import com.epf.rentmanager.exception.ServiceException;
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
        Vehicle vehicle;

        List<Reservation> rents = new ArrayList<>();


        try {
            vehicle = vehicleService.findById(vehicleId).get();
            rents = reservationService.findReservationsByVehicle(vehicleId);
            int nbReservations = rents.size();
            req.setAttribute("vehicle", vehicle);
            req.setAttribute("listReservations", rents);
            req.setAttribute("nbReservations", nbReservations);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }
}
