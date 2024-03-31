package com.epf.rentmanager.ui.servlet;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
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
import java.util.List;
import java.util.Optional;

@WebServlet("/cars/delete/*")
public class VehicleDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long vehicleId = Long.parseLong(req.getPathInfo().substring(1));
        Vehicle vehicle = new Vehicle(
                vehicleId,
                null,
                null,
                0
        );
        try {
            List<Reservation> rents = reservationService.findReservationsByVehicle(vehicleId);
            for (Reservation rent : rents) {
                if (rent.vehicle_id() == vehicleId) {
                    reservationService.delete(rent);
                }
            }
            vehicleService.delete(Optional.of(vehicle));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/cars/list");
    }


}