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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> clients = new ArrayList<Client>();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        try {
            clients = clientService.findAll();
            vehicles = vehicleService.findAll();
            long resId = Long.parseLong(req.getParameter("id"));
            Optional<Reservation> reservation = reservationService.findById(resId);
            req.setAttribute("reservation",reservation);
            req.setAttribute("clients", clients);
            req.setAttribute("vehicles", vehicles);
            req.getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    long resId = Long.parseLong(req.getParameter("id"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        Reservation reservation = new Reservation(
                resId,
                Long.parseLong(req.getParameter("client")),
                Long.parseLong(req.getParameter("car")),
                LocalDate.parse(req.getParameter("begin"),formatter),
                LocalDate.parse(req.getParameter("end"),formatter)
        );
        try {
            reservationService.update(reservation);
            resp.sendRedirect(req.getContextPath() + "/rents/list");
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }



}
