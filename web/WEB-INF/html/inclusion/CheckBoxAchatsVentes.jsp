<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-4">
    <div>

        <input class="form-check-input" type="radio" name="AchatsVentes" id="achats" value="a"
                <c:if test="${empty check}">
                    checked
                </c:if>
                <c:if test="${check == 'a'}">
                    checked
                </c:if>

        />
        <label class="form-check-label" for="achats">Achats</label>
    </div>
    <div class="btn-group-vertical mt-2 mb-3" id="groupAchats" name="AchatsVentes" role="group"
         aria-label="Basic radio toggle button group">
        <div class="form-check form-switch">

            <input class="form-check-input" name="btnEnchereOuverte" type="checkbox" id="EncheresOuvertes"
                    <c:if test="${btnEnchereOuverte == 'on'}">
                        checked
                    </c:if>
                    <c:if test="${check == 'v'}">
                        disabled
                    </c:if>
                    <c:if test="${empty check}">
                        checked
                    </c:if>
            />
            <label class="form-check-label" for="EncheresOuvertes">Enchères ouvertes</label>
        </div>
        <div class="form-check form-switch">

            <input class="form-check-input" name="btnEnchereEnCour" type="checkbox" id="EncheresEnCours"
                    <c:if test="${btnEnchereEnCour == 'on'}">
                        checked
                    </c:if>
                    <c:if test="${check == 'v'}">
                        disabled
                    </c:if>
            />
            <label class="form-check-label" for="EncheresEnCours">Mes enchères en cours</label>
        </div>
        <div class="form-check form-switch">

            <input class="form-check-input" name="btnEnchereRemporte" type="checkbox" id="EncheresRemportes"
                    <c:if test="${btnEnchereRemporte == 'on'}">
                        checked
                    </c:if>
                    <c:if test="${check == 'v'}">
                        disabled
                    </c:if>
            />
            <label class="form-check-label" for="EncheresRemportes">Mes enchères remportées</label>
        </div>
    </div>
</div>
<div class="col-4">
    <div class="form-check">

        <input class="form-check-input" type="radio" name="AchatsVentes" id="mesVentes" value="v"
        <c:if test="${check == 'v'}">
            checked
        </c:if>

        />
        <label class="form-check-label" for="mesVentes">Mes ventes</label>
    </div>
    <div class="btn-group-vertical mt-2 mb-3" id="groupVentes" name="AchatsVentes" role="group"
         aria-label="Basic radio toggle button group">
        <div class="form-check form-switch">

            <input class="form-check-input" name="btnVenteEnCour" type="checkbox" id="VentesEnCours"
                    <c:if test="${btnVenteEnCour == 'on'}">
                        checked
                    </c:if>
                    <c:if test="${check == 'a'}">
                        disabled
                    </c:if>
                    <c:if test="${empty check}">
                        disabled
                    </c:if>
            />
            <label class="form-check-label" for="VentesEnCours">Mes ventes en cours</label>
        </div>
        <div class="form-check form-switch">

            <input class="form-check-input" name="btnVenteNonDebute" type="checkbox" id="VentesNonDebutes"
                    <c:if test="${btnVenteNonDebute == 'on'}">
                        checked
                    </c:if>
                    <c:if test="${check == 'a'}">
                        disabled
                    </c:if>
                    <c:if test="${empty check}">
                        disabled
                    </c:if>
            />
            <label class="form-check-label" for="VentesNonDebutes">Ventes non débutées</label>
        </div>
        <div class="form-check form-switch">

            <input class="form-check-input" name="btnVenteTerminee" type="checkbox" id="VentesTerminees"
                    <c:if test="${btnVenteTerminee == 'on'}">
                        checked
                    </c:if>
                    <c:if test="${check == 'a'}">
                        disabled
                    </c:if>
                    <c:if test="${empty check}">
                        disabled
                    </c:if>
            />
            <label class="form-check-label" for="VentesTerminees">Ventes terminées</label>
        </div>
    </div>
    <script language="JavaScript" type="text/javascript" src="Scripts\monscript.js"></script>
</div>
