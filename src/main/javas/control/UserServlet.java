package main.javas.control;

import main.javas.model.UserBean;
import main.javas.model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserModel userModel = new UserModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("register".equals(action)) {
                register(request, response);
            } else if ("login".equals(action)) {
                login(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in UserServlet", e);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthdate = request.getParameter("birthdate");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telNumber = request.getParameter("telNumber");

        UserBean userBean = new UserBean();
        userBean.setUsername(username);
        userBean.setName(name);
        userBean.setSurname(surname);
        userBean.setBirthDate(java.sql.Date.valueOf(birthdate));
        userBean.setAddress(address);
        userBean.setEmail(email);
        userBean.setPassword(password);
        userBean.setTelNumber(telNumber);

        userModel.doSave(userBean);

        request.getRequestDispatcher("LogIn.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBean userBean = userModel.doRetrieveByUsername(username);

        if (userBean != null && userBean.getPassword().equals(password)) {
            request.getSession().setAttribute("user", userBean);
            request.getRequestDispatcher("ProductView.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
        }
    }
}