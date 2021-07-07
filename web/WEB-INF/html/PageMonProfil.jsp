<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--BOOTSTRAP CSS-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <title>Profil</title>
</head>
<body>
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
<div class="container-xl d-flex justify-content-center">
    <div class="shadow p-5 w-50 d-flex justify-content-center">
        <div class="w-100">
            <div class="row">
                <div class="col-4 mt-2 mb-2">
                    <label>Pseudo : </label>
                </div>
                <div class="col-8 mt-2 mb-2">
                    <label name="pseudo">${pseudo}</label>
                </div>
            </div>
<c:if test="${sessionScope.utilisateur.pseudo == pseudoVendeur}">
            <div class="row">
                <div class="col-4 mt-2 mb-2">
                    <label>Nom :</label>
                </div>
                <div class="col-8 mt-2 mb-2">
                    <label name="nom">${nom}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-4 mt-2 mb-2">
                    <label>Prénom :</label>
                </div>
                <div class="col-8 mt-2 mb-2">
                    <label name="prenom">${prenom}</label>
                </div>
            </div>
</c:if>
            <div class="row">
                <div class="col-4 mt-2 mb-2">
                    <label>Email : </label>
                </div>
                <div class="col-8 mt-2 mb-2">
                    <label name="email">${email}</label>
                </div>
            </div>

            <c:if test="${sessionScope.utilisateur.pseudo == pseudoVendeur}">
                <div class="row">
                    <div class="col-4 mt-2 mb-2">
                        <label>Téléphone : </label>
                    </div>
                    <div class="col-8 mt-2 mb-2">
                        <label name="telephone">${telephone}</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-4 mt-2 mb-2">
                        <label>Rue </label>
                    </div>
                    <div class="col-8 mt-2 mb-2">
                        <label name="rue">${rue}</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-4 mt-2 mb-2">
                        <label>Code postal </label>
                    </div>
                    <div class="col-8 mt-2 mb-2">
                        <label name="CP">${CP}</label>
                    </div>
                </div>
            </c:if>
                <div class="row">
                    <div class="col-4 mt-2 mb-2">
                        <label>Ville </label>
                    </div>
                    <div class="col-8 mt-2 mb-2">
                        <label name="ville">${ville}</label>
                    </div>
                </div>
            <c:if test="${sessionScope.utilisateur.pseudo == pseudoVendeur}">


                <div class="row m-5 mt-3">
                    <div class="col d-flex justify-content-center mt-2 mb-2">
                        <a href="${pageContext.request.contextPath}/PageModifierProfil">
                            <button type="button" class="btn btn-dark">Modifier</button>
                        </a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
