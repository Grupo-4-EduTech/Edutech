let ultimoID = null;
let styleBolinhaVermelha = document.getElementById("bolinha_vermelha");

document.getElementById('sino_alerts').addEventListener("click", function() {

    var idUsuario = sessionStorage.getItem('ID_USUARIO');

    abrirEFecharNotificacoes();

    fetch(`/alert/puxarAlertas/${idUsuario}`, {
        method: "GET",
    }).then(function (resposta) {
        
        var base = document.getElementById('basesinha'); 

        // Limpar as notificações anteriores antes de adicionar novas
        base.innerHTML = ''; 

        resposta.json().then(function (resposta) {
            resposta.forEach(rest => {
                if(rest.tipoAlerta === "Atenção") {
                    base.innerHTML += `<div class="alert-box" id="box_alert">
                        <div class="alert-header">
                            <span class="alert-icon">👀</span>
                            <strong class="alert-title">${rest.tipoAlerta}</strong>
                        </div>
                        <p class="alert-description">
                            ${rest.mensagemAlerta}
                        </p>
                    </div>`;
                } else if (rest.tipoAlerta === "Aviso") {
                    base.innerHTML += `<div class="alert-box" id="box_alert">
                        <div class="alert-header">
                            <span class="alert-icon">📫</span>
                            <strong class="alert-title">${rest.tipoAlerta}</strong>
                        </div>
                        <p class="alert-description">
                            ${rest.mensagemAlerta}
                        </p>
                    </div>`;
                } else {
                    base.innerHTML += `<div class="alert-box" id="box_alert">
                    <div class="alert-header">
                        <span class="alert-icon">⚠️</span>
                        <strong class="alert-title">${rest.tipoAlerta}</strong>
                    </div>
                    <p class="alert-description">
                        ${rest.mensagemAlerta}
                    </p>
                </div>`;
                }
            })
        })
    });
});

function abrirEFecharNotificacoes() {
    var triangulo = document.getElementById('wra');
    var trianguloStyle = window.getComputedStyle(triangulo)
    
    var base = document.getElementById('basesinha');
    var baseStyle = window.getComputedStyle(base);

    if(styleBolinhaVermelha.style.display == "block") {
        styleBolinhaVermelha.style.display = "none";
    }

    if (trianguloStyle.display === 'none' && baseStyle.display === 'none') {
        triangulo.style.display = "block";
        base.style.display = "block";
    } else {
        triangulo.style.display = "none";
        base.style.display = "none";
    }
}


function novasNotificacoes() {
    var idUsuario = sessionStorage.ID_USUARIO;
    fetch(`/alert/puxarUltimoAlerta/${idUsuario}`, {
        method: "GET",
    }).then(function (resposta) {
        resposta.json().then(function (resposta) {
                const novoId = resposta[0].idAlerta;
                console.log("primeiro encontrado: ", novoId);
                if(ultimoID === null) {
                    ultimoID = novoId;
                    console.log("aqui eu atualizei o ultimo ID: " , ultimoID)
                } else if (novoId !== ultimoID) {
                    ultimoID = novoId;
                    console.log("aqui eu achei um novo", novoId)
                    console.log("atualizei o ultimo encontrado", ultimoID)
                    styleBolinhaVermelha.style.display = "block";
                } else if (novoId == ultimoID) {
                    document.getElementById('sino_alerts').classList.remove('tem-notificacao');
                    styleBolinhaVermelha.style.display = "none";
                }
            })
        })
}