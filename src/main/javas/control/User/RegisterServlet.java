package main.javas.control.User;

import main.javas.bean.UserBean;
import main.javas.model.User.UserModel;
import main.javas.util.PasswordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserModel userModel = new UserModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthdateString = request.getParameter("birthdate");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telNumber = request.getParameter("telNumber");
        String admin = request.getParameter("admin");

        // Validazione dei campi obbligatori
        if (username == null || name == null || surname == null || birthdateString == null || email == null || password == null || telNumber == null || admin == null) {
            request.setAttribute("errorMessage", "Tutti i campi sono obbligatori");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
            return;
        }

        // Parsing della data di nascita
        Date birthdate = null;
        try {
            SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataNascitaUtil = formatoData.parse(birthdateString);
            birthdate = new Date(dataNascitaUtil.getTime());
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "La data di nascita non è nel formato corretto (yyyy-MM-dd)");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
            return;
        }

        // Creazione dell'oggetto UserBean
        UserBean userBean = new UserBean();
        userBean.setSurname(surname);
        userBean.setName(name);
        userBean.setUsername(username);
        userBean.setBirthDate(birthdate);
        userBean.setEmail(email);

        // Generazione del salt e hash della password
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);
        userBean.setPassword(hashedPassword);
        userBean.setSalt(salt);
        userBean.setTelNumber(telNumber);
        userBean.setAdmin(Boolean.parseBoolean(admin));

        // Salvataggio dell'utente nel database
        try {
            userModel.doSave(userBean);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // Reindirizzamento alla pagina di login con un messaggio di successo
        request.setAttribute("successMessage", "Registrazione completata con successo. Effettua il login.");
        request.getRequestDispatcher("LogIn.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
