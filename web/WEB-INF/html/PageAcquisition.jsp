<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Création Compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="container-fluid">
<header>
    <c:choose>
        <c:when test="${sessionScope.utilisateur != null}">
            <jsp:include page="inclusion/NavBarCo.jsp"></jsp:include>
        </c:when>
        <c:otherwise>
            <jsp:include page="inclusion/NavBarDeco.jsp"></jsp:include>
        </c:otherwise>
    </c:choose>
</header>
<div class=" row m-2 p-3 ">
    <h3 class="row m-5 ml-1 font-weight-bold">Vous avez remporté la vente</h3>
    <div class="col-4">
        <div class="w-100 bg-secondary shadow"><img src="" alt=""></div>
    </div>
    <div class="col-8 shadow p-3">

        <div class="row m-2">
            <h5>${nom_art}</h5>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Description</p>
            </div>
            <div class="col-8">
                <p>${description}</p>
            </div>
        </div>

        <div class=" row m-2 m-2">
            <div class="col-4">
                <p>Meilleur offre :</p>
            </div>
            <div class="col-8">
                <p>${prix_vente} pts</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Mise à prix :</p>
            </div>
            <div class="col-8">
                <p>${prix_initial} points</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Retrait</p>
            </div>
            <div class="col-8">
                <p>${rue}</p>
                <p>${CP} ${ville}</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Vendeur : </p>
            </div>
            <div class="col-8">
                <p>${pseudo}</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Tel : </p>
            </div>
            <div class="col-8">
                <p>${telephone}</p>
            </div>
        </div>

        <div class="m-4">
            <a href="${pageContext.request.contextPath}/PageAccueilEnchere"><input class="btn btn-outline-dark" type="button"
                                                                                   value="Back"></a>
        </div>
    </div>
</div>
<jsp:include page="inclusion/footer.jsp"></jsp:include>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
