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
        try {

            Reservation reservation = reservationService.findById(resId).get();
            long nbJours = java.time.temporal.ChronoUnit.DAYS.between(reservation.debut(), reservation.fin()) + 1;
            Vehicle vehicle = vehicleService.findById(reservation.vehicle_id()).get();
            Client client = clientService.findById(reservation.client_id()).get();

            req.setAttribute("client", client);
            req.setAttribute("vehicle", vehicle);
            req.setAttribute("reservation", reservation);
            req.setAttribute("nbJours", nbJours);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/rents/details.jsp");
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
