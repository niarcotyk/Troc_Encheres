//Initialisation des radio boutons
let rdAchats = document.getElementById('achats');
let rdMesVentes = document.getElementById('mesVentes');

//Initialisation des checkBox
let cbEncheresOuvertes = document.getElementById('EncheresOuvertes');
let cbEncheresEnCours = document.getElementById('EncheresEnCours');
let cbEncheresRemportes = document.getElementById('EncheresRemportes');
let cbVentesEnCours = document.getElementById('VentesEnCours');
let cbVentesNonDebutes = document.getElementById('VentesNonDebutes');
let cbVentesTerminees = document.getElementById('VentesTerminees');

//Appelle des function au moment du click des radio boutons
rdAchats.onclick = DesactiveVentes;
rdMesVentes.onclick = DesactiveAchats;

function  ManageDate(){
    document.getElementById("dateDebutArticle").addEventListener('change', function () {
        //$("#dateFinArticle").prop('max', )
        document.getElementById("dateFinArticle").min = document.getElementById("dateDebutArticle").value;
    });
    document.getElementById("dateFinArticle").addEventListener('change', function () {
        //$("#dateFinArticle").prop('max', )
        document.getElementById("dateDebutArticle").max = document.getElementById("dateFinArticle").value;
    });
}

//Etat des checkBox à l'activation du radio bouton Achats
function DesactiveVentes(){
    cbEncheresOuvertes.disabled = false;
    cbEncheresEnCours.disabled = false;
    cbEncheresRemportes.disabled = false;

    cbEncheresOuvertes.checked = true;
    cbEncheresEnCours.checked = false;
    cbEncheresRemportes.checked = false;

    cbVentesEnCours.disabled = true;
    cbVentesNonDebutes.disabled = true;
    cbVentesTerminees.disabled = true;

    cbVentesEnCours.checked = false;
    cbVentesNonDebutes.checked = false;
    cbVentesTerminees.checked = false;
}

//Etat des checkBox à l'activation du radio bouton Mes Ventes
function DesactiveAchats(){
    cbEncheresOuvertes.disabled = true;
    cbEncheresEnCours.disabled = true;
    cbEncheresRemportes.disabled = true;

    cbEncheresOuvertes.checked = false;
    cbEncheresEnCours.checked = false;
    cbEncheresRemportes.checked = false;

    cbVentesEnCours.checked = true;
    cbVentesNonDebutes.checked = false;
    cbVentesTerminees.checked = false;

    cbVentesEnCours.disabled = false;
    cbVentesNonDebutes.disabled = false;
    cbVentesTerminees.disabled = false;
}
