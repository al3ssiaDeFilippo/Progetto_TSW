package main.javas.control.User;

import main.javas.model.User.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CheckEmailServlet.class.getName());
    private final UserModel userModel = new UserModel();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean available = false;

        try {
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email parameter is missing or empty");
            }

            available = !userModel.doesEmailExist(email);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while checking email availability", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Database error\"}");
            return;
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid request parameter", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            return;
        }

        response.setContentType("application/json");
        response.getWriter().write("{\"available\":" + available + "}");
    }
}
