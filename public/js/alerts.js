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