package WasteWatchers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ActivateAccount")
public class ActivateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
        String hash = req.getParameter("hash");
        
        AppDao dao;
        try {
            dao = new AppDao();
            if (dao.activateUser( hash)) {
                RequestDispatcher dispatcher =req.getRequestDispatcher("/html/login.jsp");
                dispatcher.include(req, resp);
            }
            else {
                req.setAttribute("loginError", "failed to verify user");
                RequestDispatcher dispatcher =req.getRequestDispatcher("/html/login.jsp");
                dispatcher.include(req, resp);
            }
        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
        }
        
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
