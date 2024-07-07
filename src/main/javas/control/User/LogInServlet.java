package main.javas.control.User;
import main.javas.bean.CartBean;
import main.javas.model.Order.CartModel;
import main.javas.bean.UserBean;
import main.javas.model.User.UserModel;
import main.javas.util.Carrello;
import main.javas.util.PasswordUtils;

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
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

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

        //Inizio Modifiche Qui
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);
        userBean.setPassword(hashedPassword);
        userBean.setSalt(salt);
        //Fine Modifiche Qui

        userBean.setTelNumber(telNumber);
        userBean.setAdmin(Boolean.parseBoolean(admin));

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
        String nextPage = (String) request.getParameter("nextPage");

        UserBean userBean = userModel.doRetrieveByUsername(username);

        if (userBean == null) {
            request.setAttribute("errorMessage", "Username o password non validi");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(password, userBean.getSalt());

        if(userBean.getPassword().equals(hashedPassword) && userBean.getAdmin()) {
            session.setAttribute("user", userBean);
            response.sendRedirect("ProductView.jsp");
            return;
        }

        System.out.println("Hashed password: " + hashedPassword);
        System.out.println("Stored password: " + userBean.getPassword());
        System.out.println("Salt: " + userBean.getSalt());

        if (userBean.getPassword().equals(hashedPassword) && !userBean.getAdmin()) {
            System.out.println("User login successful."); // Debug print
            session.setAttribute("user", userBean);

            Carrello cart1 = (Carrello) session.getAttribute("cart");
            if (cart1 != null) {
                System.out.println("Cart found in session, saving it to the database..."); // Debug print
                CartModel cartModel = new CartModel();
                cartModel.doDeleteAllByUser(userBean.getIdUser());
                for (CartBean item : cart1.getProdotti()) {
                    item.setIdUser(userBean.getIdUser());
                    cartModel.doSave(item);
                }
            }

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
