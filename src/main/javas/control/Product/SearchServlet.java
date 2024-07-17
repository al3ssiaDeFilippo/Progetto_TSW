package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.model.Product.SearchModel;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private SearchModel searchModel; // Dichiarazione della variabile di tipo SearchModel

    @Override
    public void init() throws ServletException {
        super.init();
        // Inizializzazione di searchModel
        searchModel = new SearchModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        // Verifica che searchModel sia stato inizializzato correttamente
        if (searchModel != null) {
            List<ProductBean> searchResult = searchModel.searchInDatabase(query);
            request.setAttribute("searchResult", searchResult);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/SearchResult.jsp");
            dispatcher.forward(request, response);
        } else {
            // Gestione del caso in cui searchModel sia null (in realtà dovrebbe essere inizializzato nel metodo init())
            throw new ServletException("SearchModel non inizializzato correttamente");
        }
    }
}
