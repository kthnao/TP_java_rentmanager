package com.epf.rentmanager.ui.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
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
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservationService reservationService = ReservationService.getInstance();
        List<Reservation> reservations = new ArrayList<Reservation>();

        try {
            reservations = reservationService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        request.setAttribute("rents", reservations );
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);

    }

}
