let arrow = document.querySelectorAll(".arrow");
for (var i = 0; i < arrow.length; i++) {
  arrow[i].addEventListener("click", (e)=>{
 let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
 arrowParent.classList.toggle("showMenu");
  });
}

let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".bx-menu");
console.log(sidebarBtn);
sidebarBtn.addEventListener("click", ()=>{
  sidebar.classList.toggle("close");
});

document.addEventListener('DOMContentLoaded', function() {
    const iframe = document.getElementsByName('miContenedor')[0]; // Obtener el iframe por nombre
    const menuLinks = document.querySelectorAll('.sub-menu a,.iocn-link a');
    const loadingDiv = document.getElementById('loading');

    // Agregar un evento de clic a cada enlace del menú
    menuLinks.forEach(function(link) {
        link.addEventListener('click', function() {
            // Ocultar la imagen con un efecto de desvanecimiento
            fadeOut(loadingDiv, 500); // 500 milisegundos de duración del desvanecimiento
        });
    });

    // Agregar un evento de carga al iframe
    iframe.addEventListener('load', function() {
        // Mostrar la imagen con un efecto de desvanecimiento al cargar completamente el contenido del iframe
        fadeIn(loadingDiv, 500); // 500 milisegundos de duración del desvanecimiento
    });
    function fadeOut(element, duration) {
    var opacity = 1;
    var interval = 50; // Intervalo de tiempo entre cada cambio de opacidad
    var delta = interval / duration;

    var fading = setInterval(function() {
        opacity -= delta;
        element.style.opacity = opacity;

        if (opacity <= 0) {
            clearInterval(fading);
            element.style.display = 'none';
        }
    }, interval);
}
});
