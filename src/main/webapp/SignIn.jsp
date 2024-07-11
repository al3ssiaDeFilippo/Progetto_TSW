<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrazione</title>
    <link rel="stylesheet" type="text/css" href="css/SignIn.css">
    <script src="js/SignInFormValidation.js" defer></script>

</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Registrazione</h2>
<form id="registrationForm" action="<%= request.getContextPath() %>/RegisterServlet" method="post" class="registration-form">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <span id="usernameError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
    </div>
    <div>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <span id="nameError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
    </div>
    <div>
        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" required>
        <span id="surnameError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
    </div>
    <div>
        <label for="birthdate">Birth Date:</label>
        <input type="date" id="birthdate" name="birthdate" required>
        <span id="BirthDateError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <span id="emailError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <span id="passwordError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
        <div class="strength-bar-container">
            <div id="strengthBar" class="strength-bar"></div>
        </div>
    </div>
    <div>
        <label for="confirmPassword">Conferma Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>
        <span id="confirmPasswordError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
    </div>
    <div>
    <label for="telNumber">Telefono:</label>
        <input type="tel" id="telNumber" name="telNumber" required>
        <span id="telNumberError" class="error"></span> <!-- Elemento per visualizzare l'errore -->
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
