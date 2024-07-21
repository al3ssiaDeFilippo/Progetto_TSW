com<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Database Error</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/ErrorPage.css">
</head>
<body>
<div class="error-container">
    <img src="<%= request.getContextPath() %>/Images/dbAlert.png" alt="Error Icon">
    <h2>OOPS! NOTHING WAS FOUND</h2>
    <p>The page you are looking for might have been removed had its name changed or is temporarily unavailable. <a href="index.jsp">Return to homepage</a></p>
    <a href="../HomePage.jsp">Return to Home</a>
</div>
</body>
</html>
