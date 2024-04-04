package com.epf.rentmanager.ui.servlet.Client;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);

    Client client = new Client(0,
                req.getParameter("last_name"),
                req.getParameter("first_name"),
                req.getParameter("email"),
                LocalDate.parse(req.getParameter("birthdate"),formatter));
        boolean estMajeur = false;
        boolean emailDispo = false;
        boolean nomPrenomValide = false;

        try {
            estMajeur = clientService.estMajeur(client);
            if (estMajeur) emailDispo = clientService.emailDispo(client);
            if (emailDispo) nomPrenomValide = clientService.name_lenght(client);
            if (nomPrenomValide) clientService.create(client);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        if (!estMajeur) {
            req.setAttribute("clientError", "L'utilisateur doit être majeur.");
            doGet(req, resp);
        }
        else if (!emailDispo) {
            req.setAttribute("clientError", "L'email est déjà utilisé.");
            doGet(req, resp);
        }
        else if (!nomPrenomValide) {
            req.setAttribute("clientError", "Le nom et le prénom doivent contenir au moins 3 caractères.");
            doGet(req, resp);
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/users/list");
        }
    }
}
