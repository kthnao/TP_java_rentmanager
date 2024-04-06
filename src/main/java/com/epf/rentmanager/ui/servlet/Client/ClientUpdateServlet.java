package com.epf.rentmanager.ui.servlet.Client;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
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
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(req.getParameter("id"));
            Optional<Client> client = clientService.findById(clientId);
            req.setAttribute("client", client);
            req.getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long clientId = Long.parseLong(req.getParameter("id"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        Client client = new Client(clientId,
                req.getParameter("last_name"),
                req.getParameter("first_name"),
                req.getParameter("email"),
                LocalDate.parse(req.getParameter("birthdate"), formatter));
        boolean estMajeur = false;
        boolean emailDispo = false;
        boolean nomPrenomValide = false;

        Client client2;
        try {
            client2 = clientService.findById(clientId).get();
            estMajeur = clientService.estMajeur(client);
            if (estMajeur) emailDispo = clientService.emailDispo(client);
            if (emailDispo || Objects.equals(client.email(), client2.email())) nomPrenomValide = clientService.name_lenght(client);
            if (nomPrenomValide) clientService.update(client);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        if (!estMajeur) {
            req.setAttribute("clientError", "L'utilisateur doit être majeur.");
            doGet(req, resp);
        } else if (!emailDispo && !Objects.equals(client.email(), client2.email())) {
            req.setAttribute("clientError", "L'email est déjà utilisé.");
            doGet(req, resp);
        } else if (!nomPrenomValide) {
            req.setAttribute("clientError", "Le nom et le prénom doivent contenir au moins 3 caractères.");
            doGet(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/users/list");
        }
    }
}
