// sessão
function validarSessao() {
    var nome = sessionStorage.NOME_USUARIO;
    var cargo = sessionStorage.FK_CARGO;

    var nomeUsuario = document.getElementById("nomeUsuario");

    let location = window.location.pathname;
    switch(location.substring(1,location.lastIndexOf("/"))){
        case "Dashbord-Secretaria":
            if (cargo == 2) { window.location = "../Dashboard-Diretor/dashDiretor.html" }
            if (cargo == 3) { window.location = "../Dashboard-Professor/EntradaDash.html" }
            break;
        case "Dashboard-Diretor":
            if (cargo == 1) { window.location = "../Dashbord-Secretaria/dashbord-Escolas.html" }
            if (cargo == 3) { window.location = "../Dashboard-Professor/EntradaDash.html" }
            break;
        case "Dashboard-Professor":
            if (cargo == 1) { window.location = "./Dashbord-Secretaria/dashbord-Escolas.html" }
            if (cargo == 2) { window.location = "./Dashboard-Diretor/dashDiretor.html" }
            break;
    }

    if (nome != null) {
        nomeUsuario.innerHTML = nome;
    } else {
        window.location = "../login.html";
    }
}

function limparSessao() {
    sessionStorage.clear();
    window.location = "../login.html";
}

// carregamento (loading)
function aguardar() {
    var divAguardar = document.getElementById("div_aguardar");
    divAguardar.style.display = "flex";
}

function finalizarAguardar(texto) {
    var divAguardar = document.getElementById("div_aguardar");
    divAguardar.style.display = "none";

    var divErrosLogin = document.getElementById("div_erros_login");
    if (texto) {
        divErrosLogin.style.display = "flex";
        divErrosLogin.innerHTML = texto;
    }
}

