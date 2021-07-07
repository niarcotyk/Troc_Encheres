<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page Enchérir</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
<div class=" row m-2 p-3 ">
    <div class="col-4">
        <div class="w-100 bg-secondary shadow"><img src="" alt=""></div>
    </div>
    <div class="col-8 shadow p-3">
        <div class="row m-2">
            <h3 class="font-weight-bold">Enchère</h3>
        </div>

        <div class="row m-2">
            <div class="col-3">
                <h5>${nom_art}</h5>
            </div>
            <div class="col-3 d-flex flex-row-reverse w-75">
                <p>points disponibles : ${credit}</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Description</p>
            </div>
            <div class="col-8">
                <p>${description}</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Catégorie</p>
            </div>
            <div class="col-8">
                <p>${categorie}</p>
            </div>
        </div>

        <div class=" row m-2 m-2">
            <div class="col-4">
                <p>Meilleur offre :</p>
            </div>
            <div class="col-8">
                <c:if test="${acquereur != null}">
                    <p>${offre} pts par ${acquereur}</p>
                </c:if>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Mise à prix :</p>
            </div>
            <div class="col-8">
                <p>${prixInitial} points</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Fin de l'enchère :</p>
            </div>
            <div class="col-8">
                <p>${dateFinEnchere}</p>
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
                <p>${vendeur}</p>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <label for="maProposition">Ma proposition</label>
            </div>
            <div class="col-8">
                <div class="row">
                    <div class="col">
                        <form  action="PageEncherir" method="post" id="encherir">
                            <input type="number" id="maProposition" name="maProposition" min="${Proposition}" value="${Proposition}"  />
                        </form>
                    </div>
                    <div class="col">
                        <button type="submit" class="btn btn-outline-dark" form="encherir">Enchérir</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>