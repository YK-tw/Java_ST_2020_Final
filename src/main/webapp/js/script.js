function dropdownFunction(id) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    for (var i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show') && openDropdown !== document.getElementById(id)) {
            openDropdown.classList.remove('show');
        }
    }
    document.getElementById("dropdownSearch").style.display = 'none';
    document.getElementById(id).classList.toggle("show");
}

function searchShowFunction(id) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    for (var i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
            openDropdown.classList.remove('show');
        }
    }
    var obj = document.getElementById(id);
    if (obj.style.display == "block") {
        obj.style.display = "none";
    } else {
        obj.style.display = "block";
    }
}

window.onclick = function (event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

/*Показать контент виджетов*/

const widgets = document.querySelectorAll('.widget-sidebar-title');
widgets.forEach(function (widget) {
    widget.addEventListener('click', function () {
        widget.nextElementSibling.hidden = !widget.nextElementSibling.hidden;
        this.classList.toggle('widget-sidebar-title-active');
    });
});
