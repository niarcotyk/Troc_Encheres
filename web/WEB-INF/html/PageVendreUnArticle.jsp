<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Création Compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body document.onload="ManageDate()">
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
        <div class="row">
            <h3 class="m-5 ml-1">Nouvelle vente</h3>
        </div>
        <form class="row" action="PageVendreUnArticle" method="post">
        <div class=" row m-2">
            <div class="col-4">
                <p>Article :</p>
            </div>
            <div class="col-8">
                <input type="text" name="nomArticle" required pattern="[0-9A-Za-zÀ-ÖØ-öø-ÿ-' ]{1,30}">
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Description :</p>
            </div>
            <div class="col-8">
                <input type="area" name="description" row="5" cols="33" required pattern="[0-9A-Za-zÀ-ÖØ-öø-ÿ-' ]{1,200}">
            </div>
        </div>

        <div class=" row m-2 m-2">
            <div class="col-4">
                <p>Catégorie</p>
            </div>
            <div class="col-8">
               <select name="combo" id="catégorie" required>
                   <option value=""> -- Choisir une catégorie --</option>
                   <c:forEach items="${listeCategories}" var="categorie">
                       <option value="${categorie.noCategorie}">${categorie.libelle}</option>
                   </c:forEach>
               </select>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Photo de l'article</p>
            </div>
            <div class="col-8">
                <input type="button" name="photo" value="UPLOADER" disabled>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Mise à prix :</p>
            </div>
            <div class="col-8">
                <input type="number" min="0" name="prix" required pattern="[0-9]">
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Début de l'enchère</p>
            </div>
            <div class="col-8">
                <input type="date" name="dateDebut" min="${dateDuJour}" id="dateDebutArticle" required>
            </div>
        </div>

        <div class=" row m-2">
            <div class="col-4">
                <p>Fin de l'enchère</p>
            </div>
            <div class="col-8">
                <input type="date" name="dateFin" min="${dateDuJour}" id="dateFinArticle" required>
            </div>
        </div>

        <div class="mx-0 m-4">
            <div class="border border-dark row m-2">
                <h3>Retrait</h3>
                <div class=" row m-2">
                    <div class="col-4">
                        <p>Rue :</p>
                    </div>
                    <div class="col-8">
                        <input type="text" name="rue" value="${rue}" required>
                    </div>
                </div>

                <div class=" row m-2">
                    <div class="col-4">
                        <p>Code postal :</p>
                    </div>
                    <div class="col-8">
                        <input type="text" name="CP" value="${CP}" required pattern="[0-9]{5}">
                    </div>
                </div>

                <div class=" row m-2">
                    <div class="col-4">
                        <p>Ville</p>
                    </div>
                    <div class="col-8">
                        <input type="text" name="ville" value="${ville}" required pattern="[0-9A-Za-zÀ-ÖØ-öø-ÿ-' ]{1,30}">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
                <div class="col">
                    <input class="btn btn-dark" type="submit" name="btn" value="enregistrer">
                </div>
                <div class="col">
                    <input class="btn btn-dark" type="submit" name="btn" value="annuler">
                </div>
                <div class="col">
                    <a class="btn btn-dark">Annuler vente</a>
                    <p>Arrive bientôt</p>
                </div>
        </div>
    </form>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>