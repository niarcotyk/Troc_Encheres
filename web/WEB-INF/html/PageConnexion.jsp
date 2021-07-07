<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="fr">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <title>Connexion</title>
</head>
<body>

    <c:choose>
        <c:when test="${sessionScope.utilisateur != null}">
            <jsp:include page="inclusion/NavBarCo.jsp"></jsp:include>
        </c:when>
        <c:otherwise>
            <jsp:include page="inclusion/NavBarDeco.jsp"></jsp:include>
        </c:otherwise>
    </c:choose>
</div>
<div class="mx-auto" style="width: 400px">
    <div class="shadow p-5">
        <form action="PageConnexion" method="post">
            <div class="row">
                <div class="col-5 mb-3">
                    Identifiant :
                </div>
                <div class="col-7 mb-3">
                    <input type="text" name="Identifiant" placeholder="Identifiant"
                           value="${(cookie.utilisateurCookie.value != null) ? cookie.utilisateurCookie.value : ""}"
                           required>
                </div>
            </div>

            <div class="row">
                <div class="col-5 mb-3">
                    Mot de passe :
                </div>
                <div class="col-7 mb-3">
                    <input type="password" name="MotDePasse" placeholder="Mot de passe" required>
                </div>
            </div>

            <div class="row">
                <div class="col-5 mb-3">
                    <button  name="Connexion" class="btn btn-dark mb-3 mb-5">
                        Connexion
                    </button>
                </div>

                <div class="col-7 mb-3">
                    <div class="row form-check">
                        <div class="col-2 ">
                            <input class="form-check-input" type="checkbox" id="flexCheckDefault" name="souvenir" ${(cookie.utilisateurCookie.value != null) ? "checked" : ""}/>
                        </div>
                        <div class="col-10 ms-2 ps-1 pe-0">
                            <label class="form-check-label" for="flexCheckDefault"> Se souvenir de moi</label>
                        </div>
                    </div>
                    <div class="row">
                        <a class="nav-link" href="">Mot de passe oublié</a>
                    </div>
                </div>
            </div>
        </form>
        <form action="PageCreerCompte" method="get">
            <div class="row mb-2">
                <div class="d-flex justify-content-center">
                    <button type="submit" name="CreerUnCompte" class="btn btn-dark btn-lg "
                            style="width: 400px">Créer un compte
                    </button>
                </div>
            </div>
        </form>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>