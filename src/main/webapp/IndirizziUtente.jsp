<%@ page import="main.javas.bean.UserBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    // Controlla se l'utente è loggato
    UserBean user = (UserBean) session.getAttribute("user");
%>

<form action="ShippingServlet" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" id="idUser" name="idUser">
    <div>
        <% String UsName = user.getName();
           String UsSurname = user.getSurname();
           String Person = UsName + "_" + UsSurname;
           System.out.println(Person);
%>
        <input type="text" id="recipientName" name="recipientName" value= <%= Person %>>
    <div>
        <label for="address">Indirizzo:</label>
        <input type="text" id="address" name="address" required>
    </div>
    <div>
        <label for="city">Città:</label>
        <input type="text" id="city" name="city" required>
    </div>
    <div>
        <label for="cap">CAP:</label>
        <input type="text" id="cap" name="cap" required>
    </div>
    <div>
        <input type="submit" value="Salva indirizzo">
    </div>
</form>
</body>
</html>
