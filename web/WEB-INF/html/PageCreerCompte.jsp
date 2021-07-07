<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Page créer compte </title>
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
<%-- Attention enlever les expressions réguliere des inputs--%>
<div class="container shadow p-4">
    <h1 class="row d-flex justify-content-center mb-5 p-2">Mon Profil</h1>
    <form action="PageCreerCompte" method="post">
        <div class="p-3">
            <div class="row mb-3">
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="pseudo">Pseudo :</label>
                        </div>
                        <div class="col-8">
                            <input type="text" name="pseudo" id="pseudo" value="${pseudo}" required>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="nom">Nom : </label>
                        </div>
                        <div class="col-8">
                            <input type="text" name="nom" id="nom" value="${nom}" required pattern="[A-Za-z]{1,30}">
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="prenom">Prenom :</label>
                        </div>
                        <div class="col-8">
                            <input type="text" name="prenom" id="prenom" value="${prenom}" required pattern="[A-Za-z]{1,30}">
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="email">Email :</label>
                        </div>
                        <div class="col-8">
                            <input type="email" name="email" id="email" value="${email}" required>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="tel">Téléhone :</label>
                        </div>
                        <div class="col-8">
                            <input type="text" name="tel" id="tel" value="${tel}" pattern="^[0-9-.// |]{,15}">
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="rue">Rue :</label>
                        </div>
                        <div class="col-8">
                            <input type="text" name="rue" id="rue" value="${rue}" required>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="CP">Code postal :</label>
                        </div>
                        <div class="col-8">
                            <input type="text" name="CP" id="CP" value="${CP}" required pattern="[0-9]{5}">
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="ville">Ville :</label>
                        </div>
                        <div class="col-8">
                            <input type="text" name="ville" id="ville" value="${ville}" required pattern="^[A-Za-z'- ]{1,30}">
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="mdp">Mot de passe :</label>
                        </div>
                        <div class="col-8">
                            <input type="password" name="mdp" id="mdp" required>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="row">
                        <div class="col-4">
                            <label for="mdpC">Confirmation :</label>
                        </div>
                        <div class="col-8">
                            <input type="password" name="mdpC" id="mdpC" required>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col mr-5 d-flex flex-row-reverse">
                    <a href="${pageContext.request.contextPath}/PageAccueilEnchere">
                        <button class="btn btn-dark" name="boutonAction" >Créer</button>
                    </a>
                </div>
                <div class="col ml-5 ">
                    <a href="${pageContext.request.contextPath}/PageAccueilEnchere">
                        <button class="btn btn-dark" name="boutonAction">Annuler</button>
                    </a>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>