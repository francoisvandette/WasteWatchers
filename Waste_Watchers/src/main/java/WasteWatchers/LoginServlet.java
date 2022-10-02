package WasteWatchers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession(false) == null) {
            req.getSession(true);
        }

        HttpSession session = req.getSession(false);
        Object logout = session.getAttribute(LogoutServlet.setLogout);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/html/login.jsp");
        dispatcher.include(req, resp);

        try {
            if (logout.equals(LogoutServlet.logoutYes)) {
                req.getSession(false).invalidate();
                System.out.println("Session invalidated.");
            }
        } catch (NullPointerException e) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            AppDao dao = new AppDao();

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (dao.login(username, password)) {
                HttpSession session = req.getSession();
                Cookie cookie = new Cookie("cookieID", session.getId());
                resp.addCookie(cookie);
                session.setAttribute("username", username);
                resp.sendRedirect("Home");

            } else {
                req.setAttribute("loginError", "Invalid login, or email has not been verified");
                doGet(req, resp);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
