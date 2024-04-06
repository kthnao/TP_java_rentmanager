package com.epf.rentmanager.ui.servlet.Vehicle;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(req.getParameter("id"));
            Optional<Vehicle> vehicle = vehicleService.findById(vehicleId);
            req.setAttribute("vehicle", vehicle);
            req.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long vehicleId = Long.parseLong(req.getParameter("id"));
        Vehicle vehicle = new Vehicle(
                vehicleId,
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
            if (nbPlacesValide) vehicleService.update(vehicle);
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
