<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrazione</title>
    <link rel="stylesheet" type="text/css" href="css/SignIn.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Registrazione</h2>
<form action="<%= request.getContextPath() %>/RegisterServlet" method="post" class="registration-form">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div>
        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" required>
    </div>
    <div>
        <label for="birthdate">Birth Date:</label>
        <input type="date" id="birthdate" name="birthdate" required>
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <label for="telNumber">Telefono:</label>
        <input type="tel" id="telNumber" name="telNumber" required>
    </div>
    <div>
        <input type="hidden" name="admin" value="false">
    </div>
    <div class="center-align">
        <p>Hai già un account? <a href="LogIn.jsp">Accedi</a></p>
    </div>
    <div>
        <input type="submit" value="Sign In">
    </div>
</form>

<%@ include file="Footer.jsp" %>
</body>
</html>
