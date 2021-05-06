package com.uniken.corporate.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uniken.corporate.bean.Corporate;
import com.uniken.corporate.dao.CorporateDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class CorporateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CorporateDao corporateDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		corporateDao = new CorporateDao();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();

		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;

		case "/insert":
			try {
				insertCorporate(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/delete":
			try {
				deleteCorporate(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/update":
			try {
				updateCorporate(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		default:
			listCorporate(request, response);
			break;
		}
	}

	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("corporate-form.jsp");
		dispatcher.forward(request, response);
	}

	
	// insert User
	private void insertCorporate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		try {
			String name = request.getParameter("name");
			String accountNumber = request.getParameter("accountNumber");
			
			Corporate newCorporate = new Corporate(name, accountNumber);
			corporateDao.insertCorporate(newCorporate);
			response.sendRedirect("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	// Delete User
	private void deleteCorporate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			corporateDao.deleteCorporate(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}

	// Edit User
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Corporate existingCorporate = corporateDao.selectCorporate(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("corporate", existingCorporate);
		dispatcher.forward(request, response);
	}

	// Update User
	private void updateCorporate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String accountNumber = request.getParameter("accountNumber");

		Corporate update = new Corporate(id, name, accountNumber);
		corporateDao.updateCorporate(update);
		response.sendRedirect("list");
	}

	// default
	private void listCorporate(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Corporate> listCorporate = corporateDao.selectAllCorporates();
			request.setAttribute("listCorporate", listCorporate);
			RequestDispatcher dispatcher = request.getRequestDispatcher("corporate-list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
