package main.javas.control;

import main.javas.model.CartBean;
import main.javas.model.CartModel;
import main.javas.model.UserBean;
import main.javas.model.UserModel;
import main.javas.util.Carrello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserModel userModel = new UserModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        System.out.println("Action: " + action); // Debug print

        try {
            if ("register".equals(action)) {
                System.out.println("Attempting to register..."); // Debug print
                register(request, response);
            } else if ("login".equals(action)) {
                System.out.println("Attempting to login..."); // Debug print
                login(request, response);
            } else if ("logout".equals(action)) {
                System.out.println("Attempting to logout..."); // Debug print
                logout(request, response);
            }
        } catch (SQLException e) {
            System.out.println("SQLException caught: " + e.getMessage()); // Debug print
            throw new ServletException("Database error in LogInServlet", e);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthdateString = request.getParameter("birthdate");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telNumber = request.getParameter("telNumber");
        String admin = request.getParameter("admin");

        System.out.println("Username: " + username); // Debug print
        System.out.println("Name: " + name); // Debug print
        System.out.println("Surname: " + surname); // Debug print
        System.out.println("Birthdate: " + birthdateString); // Debug print
        System.out.println("Address: " + address); // Debug print
        System.out.println("Email: " + email); // Debug print
        System.out.println("Password: " + password); // Debug print
        System.out.println("TelNumber: " + telNumber); // Debug print
        System.out.println("Admin: " + admin); // Debug print


        // Validazione dei campi obbligatori
        if (username == null || name == null || surname == null || birthdateString == null || address == null || email == null || password == null || telNumber == null || admin == null) {
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
            System.out.println("Parsed birthdate: " + birthdate.toString()); // Debug print
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "La data di nascita non è nel formato corretto (yyyy-MM-dd)");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
            System.out.println("Error parsing birthdate: " + e.getMessage()); // Debug print
            return;
        }

        // Creazione dell'oggetto UserBean
        UserBean userBean = new UserBean();
        userBean.setSurname(surname);
        userBean.setName(name);
        userBean.setUsername(username);
        userBean.setBirthDate(birthdate);
        userBean.setAddress(address);
        userBean.setEmail(email);
        userBean.setPassword(password);
        userBean.setTelNumber(telNumber);
        userBean.setAdmin(Boolean.parseBoolean(admin));

        System.out.println("Attempting to save user to database..."); // Debug print

        // Salvataggio dell'utente nel database
        userModel.doSave(userBean);

        System.out.println("User saved to database."); // Debug print

        // Reindirizzamento alla pagina di login con un messaggio di successo
        request.setAttribute("successMessage", "Registrazione completata con successo. Effettua il login.");
        request.getRequestDispatcher("LogIn.jsp").forward(request, response);
    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String nextPage = (String) session.getAttribute("nextPage");

        UserBean userBean = userModel.doRetrieveByUsername(username);

        if(userBean != null && userBean.getPassword().equals(password) && userBean.getAdmin()) {
            System.out.println("Admin login successful."); // Debug print
            session.setAttribute("user", userBean);
            response.sendRedirect("ProductView.jsp");
            return;
        }

        if (userBean != null && userBean.getPassword().equals(password)) {
            System.out.println("User login successful."); // Debug print
            session.setAttribute("user", userBean);

            // Caricamento del carrello dell'utente dal database
            CartModel cartModel = new CartModel();
            List<CartBean> cartItems = cartModel.doRetrieveAll(userBean.getIdUser());
            Carrello cart = new Carrello();
            for (CartBean item : cartItems) {
                cart.aggiungi(item);
            }
            session.setAttribute("cart", cart);

            // Reindirizzamento alla pagina successiva
            if (nextPage == null || nextPage.isEmpty()) {
                nextPage = "ProductView.jsp"; // Pagina predefinita se nextPage non è impostata
            }
            response.sendRedirect(response.encodeRedirectURL(nextPage));
        } else {
            System.out.println("User login failed."); // Debug print
            request.setAttribute("errorMessage", "Username o password non validi");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
        }
    }


    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            System.out.println("Invalidating session..."); // Debug print
            session.invalidate();
        }
        response.sendRedirect("ProductView.jsp");
    }
}
