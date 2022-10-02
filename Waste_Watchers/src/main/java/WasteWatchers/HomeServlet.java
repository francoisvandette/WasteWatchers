package WasteWatchers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        HttpSession session = req.getSession(false);

        Cookie ck[] = req.getCookies();
        if (session == null) {
            resp.sendRedirect("Login");
        }
        else if (session.getAttribute("username") == null) {
            resp.sendRedirect("Login");
        }
        else if (ck.length > 0) {
            String username = (String) session.getAttribute("username");
            AppDao app;
            try {
                app = new AppDao();
                req.setAttribute("inventory", app.getFoodTable(username));
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.setHeader("Pragma", "No-cache");
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            resp.setDateHeader("Expires", -1);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/html/home.jsp");
            dispatcher.include(req, resp);

        } else {
            resp.sendRedirect("Login");
        }

    }

}
