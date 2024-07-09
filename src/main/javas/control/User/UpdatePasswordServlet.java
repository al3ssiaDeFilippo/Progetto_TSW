package main.javas.control.User;

import main.javas.bean.UserBean;
import main.javas.model.User.UserModel;
import main.javas.util.PasswordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class UpdatePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("LogIn.jsp");
            return;
        }

        // Raccogli la nuova password dal modulo
        String newPassword = request.getParameter("newPassword");

        // Genera il salt e hash della nuova password
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(newPassword, salt);
        user.setPassword(hashedPassword);
        user.setSalt(salt);

        // Aggiorna i dati nel database
        UserModel userModel = new UserModel();
        try {
            userModel.updateUser(user);
            session.setAttribute("user", user); // Aggiorna l'oggetto user nella sessione
        } catch (SQLException e) {
            throw new IOException(e); // Gestione dell'errore nel salvataggio dei dati nel database
        }

        // Reindirizza l'utente alla pagina del profilo
        response.sendRedirect("Profilo.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
