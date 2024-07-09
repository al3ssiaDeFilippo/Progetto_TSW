package main.javas.control.User;

import main.javas.bean.CartBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.CartModel;
import main.javas.model.User.UserModel;
import main.javas.util.Carrello;
import main.javas.util.PasswordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LogInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserModel userModel = new UserModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String nextPage = (String) request.getParameter("nextPage");

        UserBean userBean = null;
        try {
            userBean = userModel.doRetrieveByUsername(username);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

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

        if (userBean.getPassword().equals(hashedPassword) && !userBean.getAdmin()) {
            session.setAttribute("user", userBean);

            Carrello cart1 = (Carrello) session.getAttribute("cart");
            if (cart1 != null) {
                CartModel cartModel = new CartModel();
                try {
                    cartModel.doDeleteAllByUser(userBean.getIdUser());
                    for (CartBean item : cart1.getProdotti()) {
                        item.setIdUser(userBean.getIdUser());
                        cartModel.doSave(item);
                    }
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }

            CartModel cartModel = new CartModel();
            List<CartBean> cartItems;
            try {
                cartItems = cartModel.doRetrieveAll(userBean.getIdUser());
            } catch (SQLException e) {
                throw new ServletException(e);
            }

            Carrello cart = new Carrello();
            for (CartBean item : cartItems) {
                cart.aggiungi(item);
            }

            session.setAttribute("cart", cart);

            if (nextPage == null || nextPage.isEmpty()) {
                nextPage = "ProductView.jsp";
            }
            response.sendRedirect(response.encodeRedirectURL(nextPage));
        } else {
            request.setAttribute("errorMessage", "Username o password non validi");
            request.getRequestDispatcher("LogIn.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
