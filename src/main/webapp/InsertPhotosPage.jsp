<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Insert Photos Page</title>
</head>
<body>

<%
    ProductModelDS model = new ProductModelDS();
    Collection<?> products = null;
    ProductBean bean = null;
    try {
        products = model.doRetrieveAll(null);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    if (products != null && !products.isEmpty()) {
        bean = (ProductBean) ((List<?>) products).get(products.size() - 1);
    }
    int productCode = bean.getCode();
%>

    <h1>Insert Photos for Customizations</h1>

    <!-- FORM 1 - Black Frame in PVC -->
    <div>
        <form action="ProductImageServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productCode" value="<%=productCode%>">
            <input type="hidden" id="frame_pvc_black" name="frame" value="pvc">
            <input type="hidden" id="frameColor_pvc_black" name="frameColor" value="black">
            <label for="photo_pvc_black">Photo for PVC - Black:</label><br>
            <input type="file" id="photo_pvc_black" name="photo"><br>
            <input type="submit" value="Upload Photo">
        </form>
    </div>

    <!-- FORM 2 - White Frame in PVC -->
    <div>
        <form action="ProductImageServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productCode" value="<%=productCode%>">
            <input type="hidden" id="frame_pvc_white" name="frame" value="pvc">
            <input type="hidden" id="frameColor_pvc_white" name="frameColor" value="white">
            <label for="photo_pvc_white">Photo for PVC - White:</label><br>
            <input type="file" id="photo_pvc_white" name="photo"><br>
            <input type="submit" value="Upload Photo">
        </form>
    </div>

    <!-- FORM 3 - Brown Frame in PVC -->
    <div>
        <form action="ProductImageServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productCode" value="<%=productCode%>">
            <input type="hidden" id="frame_pvc_brown" name="frame" value="pvc">
            <input type="hidden" id="frameColor_pvc_brown" name="frameColor" value="brown">
            <label for="photo_pvc_brown">Photo for PVC - Brown:</label><br>
            <input type="file" id="photo_pvc_brown" name="photo"><br>
            <input type="submit" value="Upload Photo">
        </form>
    </div>

    <!-- FORM 4 - Black Frame in Wood -->
    <div>
        <form action="ProductImageServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productCode" value="<%=productCode%>">
            <input type="hidden" id="frame_wood_black" name="frame" value="wood">
            <input type="hidden" id="frameColor_wood_black" name="frameColor" value="black">
            <label for="photo_wood_black">Photo for Wood - Black:</label><br>
            <input type="file" id="photo_wood_black" name="photo"><br>
            <input type="submit" value="Upload Photo">
        </form>
    </div>

    <!-- FORM 5 - White Frame in Wood -->
    <div>
        <form action="ProductImageServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productCode" value="<%=productCode%>">
            <input type="hidden" id="frame_wood_white" name="frame" value="wood">
            <input type="hidden" id="frameColor_wood_white" name="frameColor" value="white">
            <label for="photo_wood_white">Photo for Wood - White:</label><br>
            <input type="file" id="photo_wood_white" name="photo"><br>
            <input type="submit" value="Upload Photo">
        </form>
    </div>

    <!-- FORM 6 - Brown Frame in Wood -->
    <div>
        <form action="ProductImageServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productCode" value="<%=productCode%>">
            <input type="hidden" id="frame_wood_brown" name="frame" value="wood">
            <input type="hidden" id="frameColor_wood_brown" name="frameColor" value="brown">
            <label for="photo_wood_brown">Photo for Wood - Brown:</label><br>
            <input type="file" id="photo_wood_brown" name="photo"><br>
            <input type="submit" value="Upload Photo">
        </form>
    </div>

    <a href="ProductView.jsp">Torna al catalogo</a>
</body>
</html>