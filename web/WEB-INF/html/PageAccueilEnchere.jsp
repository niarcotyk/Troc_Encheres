<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="fr">
<head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Accueil</title>
</head>
<body>
<form action="PageAccueilEnchere" method="post">
    <c:choose>
        <c:when test="${sessionScope.utilisateur != null}">
            <jsp:include page="inclusion/NavBarCo.jsp"></jsp:include>
        </c:when>
        <c:otherwise>
            <jsp:include page="inclusion/NavBarDeco.jsp"></jsp:include>
        </c:otherwise>
    </c:choose>
    <div class="d-flex justify-content-center">
        <div class="shadow p-5 w-75 ">
            <div class="row">
                <div>
                    <h2 class="text-center mb-4">Liste des Enchères</h2>
                </div>
            </div>
            <div class="row">
                <label class="text-start mb-3">Filtres :</label>
            </div>
            <div class="row mb-5">
                <div class="col-5">
                    <div class="input-group mb-3">
                        <span class="input-group-text">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-search" viewBox="0 0 16 16">
                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                            </svg>
                        </span>
                        <input type="text" name="filtreSaisi" value="${saisie}" class="form-control"/>
                    </div>
                </div>
                <div class="col-5">
                    <div class="row">
                        <div class="col-5 text-center">
                            <label>Catégorie :</label>
                        </div>
                        <div class="col-7">
                            <select class="form-select form-select-sm" aria-label=".form-select-sm example"
                                    name="combo">

                                <option name="cat" value="0">Toutes</option>
                                <c:forEach items="${listeCategories}" var="chaqueCategorie">
                                    <option
                                            value="${chaqueCategorie.noCategorie}"
                                            <c:if test="${categorie == chaqueCategorie.noCategorie }">
                                                selected
                                            </c:if>
                                    >
                                            ${chaqueCategorie.libelle}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-2">
                    <button name="recherche" class="btn btn-dark btn mb-3">Rechercher</button>
                </div>
            </div>
            <div class="row">
                <c:if test="${sessionScope.utilisateur != null}">
                    <jsp:include page="inclusion/CheckBoxAchatsVentes.jsp"></jsp:include>
                </c:if>
            </div>
            <div class="row ">
                <c:forEach items="${rechercheParDefaut}" var="unElement">
                    <div class="col-6 p-3 align-items-center">
                        <div class="bg-light shadow-sm p-3">
                            <div class="w-100 row">
                                <div class="col-3">
                                </div>
                                <div class="col-9">
                                    <div>
                                        <div class="row">
                                            <a class="nav-link text-dark" href="${pageContext.request.contextPath}/PageEncherir?nomArticle=${unElement.idArticle}"
                                               name="nomArticle" value="${unElement.idArticle}">
                                            <h5 class="font-weight-bold">${unElement.nomArticle}</h5>
                                            </a>
                                        </div>
                                        <div class="row">
                                            <label>Prix : ${unElement.prixInitial} ${(unElement.prixInitial > 1)? "point" : "points"}</label>
                                        </div>
                                        <div class="row">
                                            <label>Fin de l'enchère : ${unElement.dateFinFormat}</label>
                                        </div>
                                        <div class="row">
                                            <a href="${pageContext.request.contextPath}/PageMonProfil?pseudoVendeur=${unElement.pseudoVendeur}">
                                                <label>Vendeur : ${unElement.pseudoVendeur}</label>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                            ${maVenteEnCours.pseudoVendeur}
                    </div>
                </c:forEach>
                <c:if test="${not empty mesVentesEnCours}">
                    <div class="row text-center">
                        <h3>
                            Mes ventes en cours
                        </h3>
                    </div>
                </c:if>
                <c:forEach items="${mesVentesEnCours}" var="maVenteEnCours">
                    <div class="col-6 p-3 align-items-center">
                        <div class="bg-light shadow-sm p-3">


                            <div class="w-100 row">
                                <div class="col-3">
                                </div>
                                <div class="col-9">
                                    <div>
                                        <div class="row">
                                            <a class="nav-link text-dark" href="${pageContext.request.contextPath}/PageEncherir?nomArticle=${maVenteEnCours.idArticle}"
                                               name="nomArticle" value="${maVenteEnCours.idArticle}">
                                            <h5 class="font-weight-bold">${maVenteEnCours.nomArticle}</h5>
                                            </a>
                                        </div>
                                        <div class="row">
                                            <label>Prix : ${maVenteEnCours.prixInitial} ${(unElement.prixInitial > 1)? "point" : "points"}</label>
                                        </div>
                                        <div class="row">
                                            <label>Fin de l'enchère : ${maVenteEnCours.dateFinEncheres}</label>
                                        </div>
                                        <div class="row">
                                            <a href="${pageContext.request.contextPath}/PageMonProfil?pseudoVendeur=${maVenteEnCours.pseudoVendeur}">
                                                <label>Vendeur : ${maVenteEnCours.pseudoVendeur}</label>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </c:forEach>
                <c:if test="${not empty mesVentesNonDebutees}">
                    <div class="row text-center">
                        <h3>
                            Mes ventes non débutées
                        </h3>
                    </div>
                </c:if>
                <c:forEach items="${mesVentesNonDebutees}" var="maVenteNonDebutee">
                    <div class="col-6 p-3 align-items-center">
                        <div class="bg-light shadow-sm p-3">


                            <div class="w-100 row">
                                <div class="col-3">
                                </div>
                                <div class="col-9">
                                    <div>
                                        <div class="row">
                                            <a class="nav-link text-dark" href="${pageContext.request.contextPath}/PageEncherir?nomArticle=${maVenteNonDebutee.idArticle}"
                                               name="nomArticle" value="${maVenteNonDebutee.idArticle}">
                                            <h5 class="font-weight-bold">${maVenteNonDebutee.nomArticle}</h5>
                                            </a>
                                        </div>
                                        <div class="row">
                                            <label>Prix : ${maVenteNonDebutee.prixInitial} ${(unElement.prixInitial > 1)? "point" : "points"}</label>
                                        </div>
                                        <div class="row">
                                            <label>Fin de l'enchère : ${maVenteNonDebutee.dateFinEncheres}</label>
                                        </div>
                                        <div class="row">
                                            <a href="${pageContext.request.contextPath}/PageMonProfil?pseudoVendeur=${maVenteNonDebutee.pseudoVendeur}">
                                                <label>Vendeur : ${maVenteNonDebutee.pseudoVendeur}</label>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </c:forEach>
                <c:if test="${not empty mesVentesTerminees}">
                    <div class="row text-center">
                        <h3>
                            Mes ventes terminées
                        </h3>
                    </div>
                </c:if>
                <c:forEach items="${mesVentesTerminees}" var="maVenteTerminee">
                    <div class="col-6 p-3 align-items-center">
                        <div class="bg-light shadow-sm p-3">


                            <div class="w-100 row">
                                <div class="col-3">
                                </div>
                                <div class="col-9">
                                    <div>
                                        <div class="row">
                                            <a class="nav-link text-dark"
                                               href="${pageContext.request.contextPath}/PageEncherir?nomArticle=${maVenteTerminee.idArticle}"
                                               name="nomArticle" value="${maVenteTerminee.idArticle}">
                                            <h5 class="font-weight-bold">${maVenteTerminee.nomArticle}</h5>
                                            </a>
                                        </div>
                                        <div class="row">
                                            <label>Prix : ${maVenteTerminee.prixInitial} ${(unElement.prixInitial > 1)? "point" : "points"}</label>
                                        </div>
                                        <div class="row">
                                            <label>Fin de l'enchère : ${maVenteTerminee.dateFinEncheres}</label>
                                        </div>
                                        <div class="row">
                                            <a href="${pageContext.request.contextPath}/PageMonProfil?pseudoVendeur=${maVenteTerminee.pseudoVendeur}">
                                                <label>Vendeur : ${maVenteTerminee.pseudoVendeur}</label>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </c:forEach>
                <c:if test="${not empty encheresOuvertes}">
                    <div class="row text-center">
                        <h3>
                            Les enchères ouvertes
                        </h3>
                    </div>
                </c:if>
                <c:forEach items="${encheresOuvertes}" var="enchereOuverte">
                    <div class="col-6 p-3 align-items-center">
                        <div class="bg-light shadow-sm p-3">
                            <div class="w-100 row">
                                <div class="col-3">
                                </div>
                                <div class="col-9">
                                    <div>
                                        <div class="row">
                                            <a class="nav-link text-dark" href="${pageContext.request.contextPath}/PageEncherir?nomArticle=${enchereOuverte.idArticle}"
                                                    name="nomArticle" value="${enchereOuverte.idArticle}">
                                            <h5 class="font-weight-bold">${enchereOuverte.nomArticle}</h5>
                                            </a>
                                        </div>

                                        <div class="row">
                                            <label>Prix : ${enchereOuverte.prixInitial} ${(unElement.prixInitial > 1)? "point" : "points"}</label>
                                        </div>
                                        <div class="row">
                                            <label>Fin de l'enchère : ${enchereOuverte.dateFinEncheres}</label>
                                        </div>
                                        <div class="row">
                                            <a href="${pageContext.request.contextPath}/PageMonProfil?pseudoVendeur=${enchereOuverte.pseudoVendeur}">
                                                <label>Vendeur : ${enchereOuverte.pseudoVendeur}</label>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </c:forEach>
                <c:if test="${not empty mesEncheresEnCours}">
                    <div class="row text-center">
                        <h3>
                            Mes enchères en cours
                        </h3>
                    </div>
                </c:if>
                <c:forEach items="${mesEncheresEnCours}" var="enchereEnCours">
                    <div class="col-6 p-3 align-items-center">
                        <div class="bg-light shadow-sm p-3">

                            </a>
                            <div class="w-100 row">
                                <div class="col-3">
                                </div>
                                <div class="col-9">
                                    <div>
                                        <div class="row">
                                            <a class="nav-link text-dark"  href="${pageContext.request.contextPath}/PageEncherir?nomArticle=${enchereEnCours.idArticle}"
                                                name="nomArticle" value="${enchereEnCours.idArticle}">
                                            <h5 class="font-weight-bold">${enchereEnCours.nomArticle}</h5>
                                            </a>
                                        </div>
                                        <div class="row">
                                            <label>Prix : ${enchereEnCours.prixInitial} ${(unElement.prixInitial > 1)? "point" : "points"}</label>
                                        </div>
                                        <div class="row">
                                            <label>Fin de l'enchère : ${enchereEnCours.dateFinEncheres}</label>
                                        </div>
                                        <div class="row">
                                            <a href="${pageContext.request.contextPath}/PageMonProfil?pseudoVendeur=${enchereEnCours.pseudoVendeur}">
                                                <label>Vendeur : ${enchereEnCours.pseudoVendeur}</label>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </c:forEach>
                <c:if test="${not empty mesEncheresRemportees}">
                    <div class="row text-center">
                        <h3>
                            Mes enchères remportées
                        </h3>
                    </div>
                </c:if>
                <c:forEach items="${mesEncheresRemportees}" var="enchereRemportee">
                    <div class="col-6 p-3 align-items-center">
                        <div class="bg-light shadow-sm p-3">


                            <div class="w-100 row">
                                <div class="col-3">
                                </div>
                                <div class="col-9">
                                    <div>
                                        <div class="row">
                                            <a class="nav-link text-dark" href="${pageContext.request.contextPath}/PageEncherir?nomArticle=${enchereRemportee.idArticle}"
                                                    name="nomArticle" value="${enchereRemportee.idArticle}">
                                            <h5 class="font-weight-bold">${enchereRemportee.nomArticle}</h5>
                                            </a>
                                        </div>
                                        <div class="row">
                                            <label>Prix : ${enchereRemportee.prixInitial} ${(unElement.prixInitial > 1)? "point" : "points"}</label>
                                        </div>
                                        <div class="row">
                                            <label>Fin de l'enchère
                                                : ${enchereRemportee.dateFinEncheres}</label>
                                        </div>
                                        <div class="row">
                                            <a href="${pageContext.request.contextPath}/PageMonProfil?pseudoVendeur=${enchereRemportee.pseudoVendeur}">
                                                <label>Vendeur : ${enchereRemportee.pseudoVendeur}</label>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
