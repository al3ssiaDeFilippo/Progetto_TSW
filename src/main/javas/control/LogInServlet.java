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
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserModel userModel = new UserModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("register".equals(action)) {
                register(request, response);
            } else if ("login".equals(action)) {
                login(request, response);
            } else if ("logout".equals(action)) {
                logout(request, response);
            }
        } catch (SQLException e) {
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
        String telNumberString = request.getParameter("telNumber");
        String type = request.getParameter("type");


        // Controllo se la stringa non è null e se corrisponde al formato di una data
        java.sql.Date birthdate = null;
        if (birthdateString != null && birthdateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                // Definizione del formato della data
                SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");

                // Conversione della stringa in una data util.Date
                java.util.Date dataNascitaUtil = formatoData.parse(birthdateString);

                // Conversione da util.Date a sql.Date
                birthdate = new java.sql.Date(dataNascitaUtil.getTime());
                System.out.println("Data di nascita valida: " + birthdate);
            } catch (ParseException e) {
                System.out.println("La stringa non è nel formato di data valido (yyyy-MM-dd).");
            }
        } else {
            System.out.println("La stringa fornita è null o non è nel formato di data corretto.");
        }

        // Controllo se la stringa non è null e se è un numero valido
        int telNumber = 0;
        if (telNumberString != null && telNumberString.matches("\\d+")) {
            try {
                // Conversione della stringa in un intero
                telNumber = Integer.parseInt(telNumberString);
                System.out.println("Numero di telefono valido: " + telNumber);
            } catch (NumberFormatException e) {
                System.out.println("La stringa non è un numero valido.");
            }
        } else {
            System.out.println("La stringa fornita è null o non è un numero.");
        }


        UserBean userBean = new UserBean();
        userBean.setSurname(surname);
        userBean.setName(name);
        userBean.setUsername(username);
        userBean.setBirthDate(birthdate);
        userBean.setAddress(address);
        userBean.setEmail(email);
        userBean.setPassword(password);
        userBean.setTelNumber(telNumber);
        userBean.setType(type);
        System.out.println("Debug: dati utente --> " + surname + " " + name + " " + username + " " + birthdate + " " + address + " " + email + " " + password + " " + telNumber + " " + type);

        System.out.println(userBean);

        userModel.doSave(userBean);

        request.getRequestDispatcher("LogIn.jsp").forward(request, response);
    }

    /*private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBean userBean = userModel.doRetrieveByUsername(username);

        if (userBean != null && userBean.getPassword().equals(password)) {
            request.getSession().setAttribute("user", userBean);
            response.sendRedirect("ProductView.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
        }
    }*/


    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String nextPage = (String) session.getAttribute("nextPage"); // Recupera nextPage dalla sessione

        UserBean userBean = userModel.doRetrieveByUsername(username);

        if (userBean != null && userBean.getPassword().equals(password)) {
            session.setAttribute("user", userBean);
            if (nextPage == null || nextPage.isEmpty()) {
                nextPage = "ProductView.jsp"; // Pagina di default se non è stato impostato un nextPage
            }
            response.sendRedirect(response.encodeRedirectURL(nextPage));
        } else {
            request.setAttribute("errorMessage", "Username o password non validi");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
        }
    }




    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("ProductView.jsp");
    }
}
