package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/list")
public class VehicleListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VehicleService vehicleService = VehicleService.getInstance();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        try {
            vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        request.setAttribute("vehicles", vehicles);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);



    }
}
