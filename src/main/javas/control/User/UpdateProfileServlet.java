package main.javas.control.User;
import main.javas.bean.UserBean;
import main.javas.model.User.UserModel;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;import java.io.IOException;
import java.sql.Date;import java.sql.SQLException;
@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
    // Raccogli i nuovi dettagli del profilo dal modulo
    String nome = request.getParameter("nome");
    String cognome = request.getParameter("cognome");
    String nickname = request.getParameter("nickname");
    String dataNascita = request.getParameter("dataNascita");
    String email = request.getParameter("email");
    String telefono = request.getParameter("telefono");
    // Aggiorna l'oggetto user con i nuovi dettagli
    user.setName(nome);
    user.setSurname(cognome);
    user.setUsername(nickname);
    user.setBirthDate(Date.valueOf(dataNascita));
    user.setEmail(email);
    user.setTelNumber(telefono);
    // Aggiorna i dati nel database
    UserModel userModel = new UserModel();
    try {
        userModel.updateUser(user);
        session.setAttribute("user", user); // Aggiorna l'oggetto user nella sessione
        } catch (SQLException e) {
        e.printStackTrace();            // Gestione dell'errore nel salvataggio dei dati nel database
        response.sendRedirect("errorPage.jsp");
        return;
        }
    // Reindirizza l'utente alla pagina del profilo
    response.sendRedirect("Profilo.jsp");
}}