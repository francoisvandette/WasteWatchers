package WasteWatchers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FoodDelete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("Home");
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] foodItem = req.getParameterValues("itemCheck");
        String username = req.getParameter("username");
        HttpSession session = req.getSession(false);

        Cookie ck[] = req.getCookies();
        if (session == null) {
            resp.sendRedirect("Login");
        } else if (session.getAttribute("username") == null) {
            resp.sendRedirect("Login");
        } else if (ck.length > 0) {

            if (foodItem != null) {
                try {
                    AppDao app = new AppDao();
                    for (String i : foodItem) {
                        app.deleteItem(i, username);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                resp.sendRedirect("Home");
            } else {
                resp.sendRedirect("Home");
            }
        } else {
            resp.sendRedirect("Login");
        }

    }
}
