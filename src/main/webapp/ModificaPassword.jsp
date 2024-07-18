<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifica Password</title>
    <link rel="stylesheet" type="text/css" href="css/ModificaPassword.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Modifica Password</h2>

<div class="form-container">
    <form action="<%= request.getContextPath() %>/UpdateProfileServlet" method="post">
        <input type="hidden" name="action" value="updatePassword">

        <div class="form-group">
            <label for="oldPassword">Vecchia Password:</label>
            <input type="password" id="oldPassword" name="oldPassword" required>
        </div>

        <div class="form-group">
            <label for="newPassword">Nuova Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Conferma Nuova Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>

        <div class="btn-submit">
            <input type="submit" value="Modifica Password">
        </div>
    </form>
</div>

<div class="back-link">
    <a href="Profilo.jsp">Torna al Profilo</a>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>
