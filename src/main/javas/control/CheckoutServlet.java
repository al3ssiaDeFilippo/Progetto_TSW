package main.javas.control;

import main.javas.model.UserBean;
import main.javas.model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private UserModel userModel;

    public void init() throws ServletException {
        super.init();
        // Inizializza il modello utente e la connessione al database
        userModel = new UserModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null) {
            // L'utente non è loggato, imposta la pagina di destinazione dopo il login
            String nextPage = "Checkout.jsp";
            session.setAttribute("nextPage", nextPage);
            response.sendRedirect("LogIn.jsp");
        } else {
            // L'utente è già loggato, procedi con il checkout
            processCheckout(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Prendo i valori dei parametri username e password
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Ricerco nel database se l'username è presente
            UserBean userBean = userModel.doRetrieveByUsername(username);

            // Se l'utente non è null e la password è corretta, allora il login va a buon fine
            if (userBean != null && userBean.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", userBean);
                // Reindirizza alla pagina di destinazione
                String nextPage = (String) session.getAttribute("nextPage");
                if (nextPage == null || nextPage.isEmpty()) {
                    nextPage = "ProductView.jsp"; // Pagina di default se non è stato impostato un nextPage
                }
                response.sendRedirect(nextPage);
            } else {
                // Altrimenti viene mostrato un messaggio di errore
                request.setAttribute("errorMessage", "Username o password non validi");
                request.getRequestDispatcher("LogIn.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Errore del database: " + e.getMessage());
        }
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Implementa qui la logica del checkout
        // ...
        // Dopo aver completato il checkout, reindirizza l'utente alla pagina di conferma
        response.sendRedirect("Checkout.jsp");
    }
}
