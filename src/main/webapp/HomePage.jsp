<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HomePage</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/Header.css">
    <link rel="stylesheet" type="text/css" href="css/HomePage.css">
</head>
<body>
<%@include file="Header.jsp"%>
    <div class="box box-discounts">
        <h2>Offerte</h2>
    </div>

    <div class="box-container"> <!-- Aggiunto il contenitore per i box -->
        <div class="box box-new">
            <h2>Nuovi arrivi</h2>
        </div>

        <div class="box box-catalogue">
            <h2>Catalogo</h2>
        </div>
    </div>
</body>
</html>