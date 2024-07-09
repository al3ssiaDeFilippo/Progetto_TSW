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

public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean currentUser = (UserBean) session.getAttribute("user");

        // Check if the current user is an admin
        if (currentUser == null || !currentUser.getAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You must be an admin to perform this action.");
            return;
        }

        // Get the id of the user to be deleted
        int idToDelete = Integer.parseInt(request.getParameter("id"));

        // Retrieve the UserBean object for the user to be deleted
        UserModel userModel = new UserModel();
        try {
            UserBean userToDelete = userModel.doRetrieveByKey(idToDelete);
            if (userToDelete == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                return;
            }
            if (userModel.doDelete(userToDelete)) {
                System.out.println("User deleted successfully");
                response.sendRedirect("UserView.jsp"); // Redirect to a success page
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
