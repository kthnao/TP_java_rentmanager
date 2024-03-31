package com.epf.rentmanager.ui.servlet;

import javax.servlet.http.HttpServlet;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete/*")
public class ClientDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = new Client(
                Integer.parseInt(req.getPathInfo().substring(1)),
                null,
                null,
                null,
                null

        );
        try {
            clientService.delete(client);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/users/list");
    }


}