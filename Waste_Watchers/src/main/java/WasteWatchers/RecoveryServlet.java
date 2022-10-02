package WasteWatchers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Recovery")
public class RecoveryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    String email = null;
    String subject = "Recover Email";
    String body = "Your password is ";
    String pswd = null;

    AppDao dao;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher  = req.getRequestDispatcher("/html/recover.jsp");
        dispatcher.include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        email = req.getParameter("email");
        System.out.println("Here");

        try {
            dao = new AppDao();
            pswd = dao.getPassword(email);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (pswd == null) {
            System.out.println("in pswrd null");

            req.setAttribute("emailError", "Invalid email");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/html/recover.jsp");
            dispatcher.include(req, resp);
        } else {


            body += pswd;
            EmailBuilder sender = new EmailBuilder();
            sender.setBody(body);
            sender.setEmail(email);
            sender.setSubject(subject);
            sender.start();
            RequestDispatcher dispatcher = req.getRequestDispatcher("/html/login.jsp");
            dispatcher.include(req, resp);
        }

    }

}
