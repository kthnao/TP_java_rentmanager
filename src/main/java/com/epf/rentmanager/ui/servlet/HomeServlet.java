package com.epf.rentmanager.ui.servlet;

import java.io.IOException;

import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class  HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	@Autowired
	VehicleService vehicleService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			request.setAttribute("vehicleCount",vehicleService.count());

		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
