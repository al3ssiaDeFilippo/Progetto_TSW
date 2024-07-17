package main.javas.control.Product;

import com.google.gson.Gson;
import main.javas.bean.ProductBean;
import main.javas.model.Product.SearchModel;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SearchServlet extends HttpServlet {
    private SearchModel searchModel;

    @Override
    public void init() throws ServletException {
        super.init();
        searchModel = new SearchModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("autocomplete".equals(action)) {
            String term = request.getParameter("term");
            List<ProductBean> searchResults = searchModel.searchInDatabase(term);
            List<String> productNames = searchResults.stream().map(ProductBean::getProductName).collect(Collectors.toList());
            String json = new Gson().toJson(productNames);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            String query = request.getParameter("q");
            if (searchModel != null) {
                List<ProductBean> searchResult = searchModel.searchInDatabase(query);
                request.setAttribute("searchResult", searchResult);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/SearchResult.jsp");
                dispatcher.forward(request, response);
            } else {
                throw new ServletException("SearchModel non inizializzato correttamente");
            }
        }
    }
}
