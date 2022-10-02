package WasteWatchers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession(false).getAttribute("username") != null) {
            resp.setHeader("Pragma", "No-cache");
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            resp.setDateHeader("Expires", -1);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/html/profile.jsp");
            dispatcher.include(req, resp);
        } else {
            resp.sendRedirect("Login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            AppDao dao = new AppDao();
            String username = (String) session.getAttribute("username");
            RequestDispatcher dispatcher;
            String newUsername = req.getParameter("newUsername");
            String result = "";

            if (dao.checkUsername(newUsername) && req.getParameter("usernameSubmit") != null) {
                dao.setUsername(username, newUsername);
                req.setAttribute("username", newUsername);
                session.setAttribute("username", newUsername);
                result = "Username changed.";
            } else if (req.getParameter("passwordSubmit") != null) {
                String newPassword = req.getParameter("password");
                dao.setPassword(username, newPassword);
                result = "Password changed.";
            } else if (!dao.checkUsername(newUsername)) {
                result = "Username already exists, pick a different one.";
            }
            req.setAttribute("profileStatus", result);
            resp.setHeader("Pragma", "No-cache");
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            resp.setDateHeader("Expires", -1);
            dispatcher = req.getRequestDispatcher("/html/profile.jsp");
            dispatcher.include(req, resp);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

}
