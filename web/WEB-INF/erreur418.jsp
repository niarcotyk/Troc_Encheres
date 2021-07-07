<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Erreur 418</title>
    </head>
    <body class="d-flex justify-content-center">
        <div class="w-50">
            <h1 class="row">Erreur 418</h1>
            <h2 class="row">I'm a teapot</h2>
            <img class="row" width="500" height="300" src="${pageContext.request.contextPath}/img/Error_418.png" alt="I am a teapot">
            <p class="row"><a href="${pageContext.request.contextPath}/PageAccueilEnchere">Retour</a></p>
        </div>
    </body>
</html>
