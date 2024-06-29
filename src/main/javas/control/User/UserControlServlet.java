package main.javas.control.User;

import main.javas.bean.UserBean;
import main.javas.model.User.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserControlServlet")
public class UserControlServlet extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    UserBean currentUser = (UserBean) session.getAttribute("user");

    // Check if the current user is an admin
    if (currentUser == null || !currentUser.getAdmin()) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You must be an admin to perform this action.");
        return;
    }

    String action = request.getParameter("action");
    if (action == null) {
        throw new ServletException("Missing action parameter.");
    }

    switch (action) {
            case "delete":
                deleteUser(request, response);
                break;
            case "add":
                addAdmin(request, response);
                break;
            default:
                throw new ServletException("Invalid action parameter.");
    }
}

//elimina un utente dal database
    protected void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Get the id of the user to be deleted
    int idToDelete = Integer.parseInt(request.getParameter("id"));

    System.out.println("ID DA ELIMARE: " + idToDelete);

    // Retrieve the UserBean object for the user to be deleted
    UserModel userModel = new UserModel();
    UserBean userToDelete = null;
    try {
        userToDelete = userModel.doRetreiveByKey(idToDelete);
        if (userToDelete == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            return;
        }
        userModel.doDelete(userToDelete);
        response.sendRedirect("UserView.jsp"); // Redirect to a success page
    } catch (SQLException e) {
        throw new ServletException("Error deleting user", e);
    }
}

    //rende un utente admin
    protected void addAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Get the id of the user to be made admin
    int idToMakeAdmin = Integer.parseInt(request.getParameter("id"));

    // Retrieve the UserBean object for the user to be made admin
    UserModel userModel = new UserModel();
    UserBean userToMakeAdmin = null;
    try {
        userToMakeAdmin = userModel.doRetreiveByKey(idToMakeAdmin);
        if (userToMakeAdmin == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            return;
        }

        // Make the user admin
        userToMakeAdmin.setAdmin(true);

        // Update the user in the database
        userModel.updateUser(userToMakeAdmin);

        response.sendRedirect("UserView.jsp"); // Redirect to a success page
    } catch (SQLException e) {
        throw new ServletException("Error making user admin", e);
    }
}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
