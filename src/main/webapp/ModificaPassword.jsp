<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifica Password</title>
</head>
<body>
<%@ include file="Header.jsp" %>
    <h2>Modifica Password</h2>
    <form action="UpdateProfileServlet" method="post">
        <input type="hidden" name="action" value="updatePassword">
        <div>
            <label for="oldPassword">Vecchia Password:</label>
            <input type="password" id="oldPassword" name="oldPassword" required>
        </div>
        <div>
            <label for="newPassword">Nuova Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div>
            <label for="confirmPassword">Conferma Nuova Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <div>
            <input type="submit" value="Modifica Password">
        </div>
    </form>
<%@ include file="Footer.jsp" %>
</body>
</html>