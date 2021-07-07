<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.trocencheres.messages.LecteurMessage" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-5">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/PageAccueilEnchere">ENI Enchère</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 d-flex">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/PageConnexion">S'inscrire ou connecter</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="row my-3">
    <c:if test="${!empty listeCodesErreur}">
        <ul>
            <c:forEach var="code" items="${listeCodesErreur}">
                <li><h5 class=" text-center text-danger">${LecteurMessage.getMessageErreur(code)}</h5></li>
            </c:forEach>
        </ul>
    </c:if>
</div>