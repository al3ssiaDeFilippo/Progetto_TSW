/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.31
 * Generated at: 2024-07-22 12:17:05 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import main.javas.bean.UserBean;
import main.javas.bean.UserBean;

public final class LogIn_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/Header.jsp", Long.valueOf(1721581151709L));
    _jspx_dependants.put("/Footer.jsp", Long.valueOf(1721581151649L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("main.javas.bean.UserBean");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/SignIn.css\">\r\n");
      out.write("    <title>Login</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");

    UserBean user = (UserBean) session.getAttribute("user");

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/reset.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/Header.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap\">\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css\">\r\n");
      out.write("    <title>Header Example</title>\r\n");
      out.write("    <style>\r\n");
      out.write("        /* Ensure the suggestion box is as wide as the search bar */\r\n");
      out.write("        .ui-autocomplete {\r\n");
      out.write("            max-width: 100%;\r\n");
      out.write("            box-sizing: border-box; /* Ensures padding is included in the element's total width and height */\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<!-- Header -->\r\n");
      out.write("<header class=\"header-container\">\r\n");
      out.write("    <!-- Aggiunta del logo -->\r\n");
      out.write("    <a href=\"HomePage.jsp\" class=\"logo-link\">\r\n");
      out.write("        <img class=\"logo-img\" src=\"Images/PosterWorldLogo.png\" alt=\"Image not found\">\r\n");
      out.write("    </a>\r\n");
      out.write("\r\n");
      out.write("    <!-- Aggiunta della barra di ricerca -->\r\n");
      out.write("    <div class=\"search-container\">\r\n");
      out.write("        <form id=\"searchForm\" action=\"SearchServlet\" method=\"get\">\r\n");
      out.write("            <input type=\"search\" name=\"q\" id=\"searchBar\" placeholder=\"Cerca...\">\r\n");
      out.write("            <button type=\"submit\">\r\n");
      out.write("                <img class=\"src-img\" src=\"Images/searchIcon.png\" alt=\"Cerca\">\r\n");
      out.write("            </button>\r\n");
      out.write("        </form>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <!-- Aggiunta dei bottoni -->\r\n");
      out.write("    <div class=\"button-container\">\r\n");
      out.write("        <a href=\"#footer\">\r\n");
      out.write("            <img class=\"footer-button header-icon\" src=\"Images/infoIcon.png\" alt=\"Image not found\">\r\n");
      out.write("        </a>\r\n");
      out.write("        <a href=\"carrello.jsp\">\r\n");
      out.write("            <img class=\"cart-button header-icon\" src=\"Images/cartIcon.png\" alt=\"Image not found\">\r\n");
      out.write("        </a>\r\n");
      out.write("        <div class=\"profile-menu-container\">\r\n");
      out.write("            <a href=\"#\" class=\"profile-icon\">\r\n");
      out.write("                <img class=\"login-button header-icon\" src=\"Images/userIcon.png\" alt=\"Image not found\">\r\n");
      out.write("            </a>\r\n");
      out.write("            <div class=\"profile-dropdown-menu\">\r\n");
      out.write("                ");

                    // Recupera l'utente dalla sessione
                    UserBean loggedInUser = (UserBean) session.getAttribute("user");
                    if (loggedInUser != null) {
                
      out.write("\r\n");
      out.write("                <a href=\"ProfileOverview.jsp\" class=\"link-like\">Il tuo Profilo</a>\r\n");
      out.write("                <form action=\"");
      out.print( request.getContextPath() );
      out.write("/LogOutServlet\" method=\"post\" class=\"logout-button link-like\">\r\n");
      out.write("                    <input type=\"submit\" value=\"Logout\">\r\n");
      out.write("                </form>\r\n");
      out.write("                ");

                } else {
                
      out.write("\r\n");
      out.write("                <a href=\"LogIn.jsp\" class=\"link-like\">Accedi</a>\r\n");
      out.write("                ");

                    }
                
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</header>\r\n");
      out.write("\r\n");
      out.write("<!-- Sub Header -->\r\n");
      out.write("<header class=\"sub-header-container\">\r\n");
      out.write("    <!-- Aggiunta della barra di navigazione -->\r\n");
      out.write("    <div class=\"nav-bar\">\r\n");
      out.write("        <a class=\"film-button\" href=\"Categories.jsp?category=Film\">Film</a>\r\n");
      out.write("        <a class=\"serieTV-button\" href=\"Categories.jsp?category=SerieTV\">Serie TV</a>\r\n");
      out.write("        <a class=\"anime-button\" href=\"Categories.jsp?category=Anime\">Anime</a>\r\n");
      out.write("        <a class=\"fumetti-button\" href=\"Categories.jsp?category=Fumetti\">Fumetti</a>\r\n");
      out.write("        <a class=\"giochi-button\" href=\"Categories.jsp?category=Giochi\">Videogiochi</a>\r\n");
      out.write("    </div>\r\n");
      out.write("</header>\r\n");
      out.write("\r\n");
      out.write("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\"></script>\r\n");
      out.write("<script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.min.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("    $(function() {\r\n");
      out.write("        $(\"#searchBar\").autocomplete({\r\n");
      out.write("            source: function(request, response) {\r\n");
      out.write("                $.ajax({\r\n");
      out.write("                    url: \"SearchServlet?action=autocomplete\",\r\n");
      out.write("                    type: \"GET\",\r\n");
      out.write("                    data: {\r\n");
      out.write("                        term: request.term\r\n");
      out.write("                    },\r\n");
      out.write("                    success: function(data) {\r\n");
      out.write("                        response(data);\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("            },\r\n");
      out.write("            minLength: 1,\r\n");
      out.write("            select: function(event, ui) {\r\n");
      out.write("                $(\"#searchBar\").val(ui.item.label);\r\n");
      out.write("                $(\"#searchForm\").submit(); // Invia il form quando viene selezionato un suggerimento\r\n");
      out.write("                return false;\r\n");
      out.write("            },\r\n");
      out.write("            open: function() {\r\n");
      out.write("                var $input = $(this),\r\n");
      out.write("                    $autocomplete = $input.autocomplete(\"widget\"),\r\n");
      out.write("                    inputWidth = $input.outerWidth();\r\n");
      out.write("\r\n");
      out.write("                $autocomplete.css(\"width\", inputWidth + \"px\");\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    });\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("    /* Header.css */\r\n");
      out.write("\r\n");
      out.write("    .logout-button input[type=\"submit\"] {\r\n");
      out.write("        background-color: red;\r\n");
      out.write("        color: white;\r\n");
      out.write("        border: none;\r\n");
      out.write("        padding: 10px 20px;\r\n");
      out.write("        text-align: center;\r\n");
      out.write("        text-decoration: none;\r\n");
      out.write("        display: inline-block;\r\n");
      out.write("        font-size: 16px;\r\n");
      out.write("        margin: 4px 2px;\r\n");
      out.write("        cursor: pointer;\r\n");
      out.write("        border-radius: 4px;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    .button-container {\r\n");
      out.write("        text-align: center;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    .profile-menu-container {\r\n");
      out.write("        position: relative;\r\n");
      out.write("        display: inline-block;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    .profile-icon {\r\n");
      out.write("        display: block;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    .profile-dropdown-menu {\r\n");
      out.write("        display: none;\r\n");
      out.write("        position: absolute;\r\n");
      out.write("        top: 100%;\r\n");
      out.write("        right: 0;\r\n");
      out.write("        background-color: #fff;\r\n");
      out.write("        border: 1px solid #ddd;\r\n");
      out.write("        box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);\r\n");
      out.write("        z-index: 999999; /* Increase this value as needed */\r\n");
      out.write("        min-width: 160px;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    .profile-dropdown-menu a {\r\n");
      out.write("        color: black;\r\n");
      out.write("        padding: 12px 16px;\r\n");
      out.write("        text-decoration: none;\r\n");
      out.write("        display: block;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    .profile-dropdown-menu a:hover {\r\n");
      out.write("        background-color: #ddd;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /* Mostra il menu a tendina quando si passa sopra l'icona del profilo */\r\n");
      out.write("    .profile-menu-container:hover .profile-dropdown-menu {\r\n");
      out.write("        display: block;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("<h2>Login</h2>\r\n");
 if (user == null) { 
      out.write("\r\n");
      out.write("<form id=\"loginForm\" action=\"");
      out.print( request.getContextPath() );
      out.write("/LogInServlet\" method=\"post\" class=\"registration-form\">\r\n");
      out.write("    <input type=\"hidden\" name=\"nextPage\" value=\"ProductView.jsp\">\r\n");
      out.write("\r\n");
      out.write("    <section>\r\n");
      out.write("        <div class=\"form-wrapper\">\r\n");
      out.write("            <input type=\"text\" id=\"username\" name=\"username\" placeholder=\"Username\" required>\r\n");
      out.write("            <label class=\"form-label\" for=\"username\">Username</label>\r\n");
      out.write("            <span id=\"usernameError\" class=\"error\"></span>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"form-wrapper\">\r\n");
      out.write("            <input type=\"password\" id=\"password\" name=\"password\" placeholder=\"Password\" required>\r\n");
      out.write("            <label class=\"form-label\" for=\"password\">Password</label>\r\n");
      out.write("            <span id=\"passwordError\" class=\"error\"></span>\r\n");
      out.write("        </div>\r\n");
      out.write("    </section>\r\n");
      out.write("    ");
 String errorMessage = (String) request.getAttribute("errorMessage"); 
      out.write("\r\n");
      out.write("    ");
 if (errorMessage != null) { 
      out.write("\r\n");
      out.write("    <p class=\"error\" style=\"color:red;\">");
      out.print( errorMessage );
      out.write("</p>\r\n");
      out.write("    ");
 } 
      out.write("\r\n");
      out.write("    <p>Non hai un account? <a href=\"SignIn.jsp\">Registrati</a></p>\r\n");
      out.write("\r\n");
      out.write("    <div class=\"form-wrapper\">\r\n");
      out.write("        <input type=\"submit\" value=\"Log in\">\r\n");
      out.write("    </div>\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
 }
      out.write('\r');
      out.write('\n');
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/reset.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/Footer.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap\">\r\n");
      out.write("    <title>Footer Example</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"site-wrapper\">\r\n");
      out.write("\r\n");
      out.write("    <main class=\"site-content\">\r\n");
      out.write("        <!-- Inserisci qui il contenuto principale della pagina -->\r\n");
      out.write("    </main>\r\n");
      out.write("\r\n");
      out.write("    <footer class=\"footer-container\" id=\"footer\">\r\n");
      out.write("\r\n");
      out.write("        <div class=\"presentation-box\">\r\n");
      out.write("\r\n");
      out.write("            <div class=\"footer-box shipping-box\">\r\n");
      out.write("                <img class=\"shipping-img\" src=\"Images/fastShipping.png\" alt=\"Image not found\">\r\n");
      out.write("                <p class=\"shipping-text\">Consegna in 3-5 giorni lavorativi</p>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div class=\"footer-box shopping-box\">\r\n");
      out.write("                <img class=\"shopping-img\" src=\"Images/newProducts.png\" alt=\"Image not found\">\r\n");
      out.write("                <p class=\"shopping-text\">Nuovi prodotti ogni giorno</p>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div class=\"footer-box transaction-box\">\r\n");
      out.write("                <img class=\"transaction-img\" src=\"Images/safeTransaction.png\" alt=\"Image not found\">\r\n");
      out.write("                <p class=\"transaction-text\">Pagamenti sicuri</p>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div class=\"footer-box assistence-box\">\r\n");
      out.write("                <img class=\"assistence-img\" src=\"Images/efficientAssistence.png\" alt=\"Image not found\">\r\n");
      out.write("                <p class=\"assistence-text\">Assistenza H24</p>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <!-- Nuova sezione Contatti -->\r\n");
      out.write("        <div class=\"footer-box contacts-box\">\r\n");
      out.write("\r\n");
      out.write("            <h3>Contatti</h3>\r\n");
      out.write("            <ul class=\"contacts-list\">\r\n");
      out.write("                <li><strong>Email:</strong> yourposterworld@gmail.com</li>\r\n");
      out.write("                <li><strong>Telefono:</strong> +393202347075</li>\r\n");
      out.write("            </ul>\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"footer-box social-box\">\r\n");
      out.write("\r\n");
      out.write("            <h3>Seguici sui Social</h3>\r\n");
      out.write("            <ul class=\"social-list\">\r\n");
      out.write("                <li><a href=\"https://www.instagram.com/yourposterworld/\"><img class=\"social-img\" src=\"Images/instagramIcon.png\" alt=\"Image not found\"></a></li>\r\n");
      out.write("                <li><a href=\"https://x.com/PosterYourWorld\"><img class=\"social-img\" src=\"Images/xIcon.png\" alt=\"Image not found\"></a></li>\r\n");
      out.write("                <li><a href=\"https://it.pinterest.com/yourposterworld0070/\"><img class=\"social-img\" src=\"Images/pinterestIcon.png\" alt=\"Image not found\"></a></li>\r\n");
      out.write("            </ul>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <!-- Nuova sezione Dove Trovarci -->\r\n");
      out.write("        <div class=\"footer-box find-us-box\">\r\n");
      out.write("            <h3>Dove Trovarci</h3>\r\n");
      out.write("            <iframe class=\"maps-box\" src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d7096.943900244428!2d14.6266922!3d27.2043286!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x13c71fbf46eaea4b%3A0xb455a244b8851a8!2z2YXYudix2LYg2KrZhdmG2YfZhtiqINin2YTYs9mG2YjZig!5e0!3m2!1sit!2sit!4v1719769779611!5m2!1sit!2sit\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>\r\n");
      out.write("        </div>\r\n");
      out.write("    </footer>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
