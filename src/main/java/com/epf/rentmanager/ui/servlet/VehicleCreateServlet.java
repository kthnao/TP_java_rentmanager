package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Vehicle vehicle = new Vehicle(
                0,
                req.getParameter("manufacturer"),
                req.getParameter("modele"),
                Integer.parseInt(req.getParameter("seats"))
        );
        boolean constructeurValide = false;
        boolean modeleValide = false;
        boolean nbPlacesValide = false;

        try {
             constructeurValide = vehicleService.constructeurNonVide(vehicle);
            if (constructeurValide) modeleValide = vehicleService.modeleNonVide(vehicle);
            if (modeleValide) nbPlacesValide = vehicleService.nbPlacesValide(vehicle);
            if (nbPlacesValide) vehicleService.create(vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        if (!constructeurValide) {
            req.setAttribute("vehicleError", "Le véhicule doit avoir un constructeur.");
            doGet(req, resp);
        }
        else if (!modeleValide) {
            req.setAttribute("vehicleError", "Le véhicule doit avoir un modèle.");
            doGet(req, resp);
        }
        else if (!nbPlacesValide) {
            req.setAttribute("vehicleError", "Le nombre de place du véhicule doit être compris entre 9 et 2.");
            doGet(req, resp);
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/cars/list");
        }
    }
}

