package WasteWatchers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/InsertFood")
public class InsertServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("Home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String itemname = req.getParameter("itemname");
        java.sql.Date expiry;
        java.util.Date selectedDate;

        HttpSession session = req.getSession(false);

        Cookie ck[] = req.getCookies();
        if (session == null) {
            resp.sendRedirect("Login");
        } else if (session.getAttribute("username") == null) {
            resp.sendRedirect("Login");
        } else if (ck.length > 0) {
            try {
                selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("expiry"));
                expiry = new java.sql.Date(selectedDate.getTime());

                AppDao dao = new AppDao();

                dao.insertFoodItem(username, itemname, expiry);

                resp.sendRedirect("Home");
            } catch (ClassNotFoundException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            resp.sendRedirect("Login");
        }

    }
}