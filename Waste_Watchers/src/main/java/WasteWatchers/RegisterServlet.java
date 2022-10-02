package WasteWatchers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO have passwords stored as hashes

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/html/register.jsp");
        dispatcher.include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AppDao dao = new AppDao(); // object that does all sql execution

            String username = req.getParameter("username"); // get values from form
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (username.trim() == "" || !username.matches("[a-zA-Z0-9]*")) { // if the username is just spaces or includes anything but num and letter
                req.setAttribute("error", "Only letters or numbers are allowed in your username");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/html/register.jsp");
                dispatcher.include(req, resp);
            } else if (!dao.checkUser(email, username)) { // checks if username or email is already taken
                req.setAttribute("error", "Username or email is already associated with an account");
                doGet(req, resp);
            } else if (!verifyEmail(email)) {
                req.setAttribute("error", "Not a valid email");
                doGet(req, resp);
            } else {
                try {
                    String hash = getHash();
                    if (dao.createUser(email, username, password, hash)) { // make a new user and their item table in database
                        EmailBuilder emailApp = new EmailBuilder();
                        emailApp.setEmail(email);
                        emailApp.setSubject("Verify your email");
                        emailApp.setBody("Click the link to verify your email http://localhost:8080/Waste_Watchers/ActivateAccount?hash=" + hash);
                        emailApp.start();
                        resp.sendRedirect("Login");
                    }
                } catch (java.sql.SQLSyntaxErrorException e) {

                }

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyEmail(String email) {
        boolean result = true;
        try {
            InternetAddress verifyEmail = new InternetAddress(email);
            verifyEmail.validate();
        } catch (AddressException e) {
            result = false;
        }
        return result;
    }

    private String getHash() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
