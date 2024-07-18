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
    <section>
        <div class="form-wrapper">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <label class="form-label" for="username">Username</label>
            <span id="usernameError" class="error"></span>
        </div>
        <div class="form-wrapper">
            <input type="text" id="name" name="name" placeholder="Name" required>
            <label class="form-label" for="name">Name</label>
            <span id="nameError" class="error"></span>
        </div>
        <div class="form-wrapper">
            <input type="text" id="surname" name="surname" placeholder="Surname" required>
            <label class="form-label" for="surname">Surname</label>
            <span id="surnameError" class="error"></span>
        </div>
        <div class="form-wrapper">
            <input type="date" id="birthdate" name="birthdate" placeholder="Birth Date" required>
            <label class="form-label" for="birthdate">Birth Date</label>
            <span id="BirthDateError" class="error"></span>
        </div>
        <div class="form-wrapper">
            <input type="email" id="email" name="email" placeholder="Email" required>
            <label class="form-label" for="email">Email</label>
            <span id="emailError" class="error"></span>
        </div>
        <div class="form-wrapper">
            <input type="password" id="password" name="password" placeholder="Password" required>
            <label class="form-label" for="password">Password</label>
            <span id="passwordError" class="error"></span>
            <div class="strength-bar-container">
                <div id="strengthBar" class="strength-bar"></div>
            </div>
        </div>
        <div class="form-wrapper" id="confirmPasswordGroup">
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Conferma Password" required>
            <label class="form-label" for="confirmPassword">Conferma Password</label>
            <span id="confirmPasswordError" class="error"></span>
        </div>
        <div class="form-wrapper">
            <input type="tel" id="telNumber" name="telNumber" placeholder="Telefono" required>
            <label class="form-label" for="telNumber">Telefono</label>
            <span id="telNumberError" class="error"></span>
        </div>
    </section>
    <input type="hidden" name="admin" value="false">
    <div class="center-align">
        <p>Hai già un account? <a href="LogIn.jsp">Accedi</a></p>
    </div>
    <div class="form-wrapper">
        <input type="submit" value="Sign In">
    </div>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
