document.getElementById('sino_alerts').addEventListener("click", function() {
    abrirEFecharNotificacoes();

    fetch("/alert/puxarAlertas", {
        method: "GET",
    }).then(function (resposta) {

        var base = document.getElementById('basesinha'); 

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
                }
                base.innerHTML += `<div class="alert-box" id="box_alert">
                    <div class="alert-header">
                        <span class="alert-icon">⚠️</span>
                        <strong class="alert-title">${rest.tipoAlerta}</strong>
                    </div>
                    <p class="alert-description">
                        ${rest.mensagemAlerta}
                    </p>
                </div>`;
            })
        })
    });
})

function abrirEFecharNotificacoes() {
    var triangulo = document.getElementById('wra');
    var trianguloStyle = window.getComputedStyle(triangulo)
    
    var base = document.getElementById('basesinha');
    var baseStyle = window.getComputedStyle(base);

    if (trianguloStyle.display === 'none' && baseStyle.display === 'none') {
        triangulo.style.display = "block";
        base.style.display = "block";
    } else {
        triangulo.style.display = "none";
        base.style.display = "none";
    }
}