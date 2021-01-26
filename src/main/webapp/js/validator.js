function validateSaveProduct(form) {
    if (!validateForm(form, [{
        id: "name",
        message: "Поле «Name» некорректно заполнено",
        checker: checkText
    }, {
        id: "price",
        message: "Поле «Price» некорректно заполнено",
        checker: checkPositiveNumber
    }, {
        id: "description",
        message: "Поле «Description» некорректно заполнено",
        checker: checkText
    }])) {
        console.log('false');
        return false;
    }
    console.log('true');
    return true;
}

function checkText(value) {
    return checkRegexp(value, "^[a-zA-Z0-9\\s.,!\"'%\\^*?]+$");
}

function checkPositiveNumber(value) {
    return checkNumber(value) && value > 0;
}

function checkNumber(value) {
    return !isNaN(parseFloat(value)) && isFinite(value);
}

function checkRegexp(value, regexp) {
    return new RegExp(regexp).test(value);
}

function validateForm(form, data) {
    for (i in data) {
        if (!data[i].checker(form[data[i].id].value)) {
            errorMessage(form[data[i].id], data[i].message);
            return false;
        }
    }
    return true;
}

function errorMessage(element, message) {
    show(message, function () {
        element.focus()
    });
}

function show(message, action) {
    showMessage(message, [{
        caption: "Закрыть",
        handler: (action !== undefined ? action : function () {
        })
    }]);
}


function showMessage(message, buttons) {
    var body = document.getElementsByTagName("body")[0];
    var messageElement = document.createElement("div");
    messageElement.id = "confirm-message";
    var messageContent = document.createElement("div");
    var messageText = document.createElement("p");
    messageText.innerHTML = message;
    messageContent.appendChild(messageText);
    var buttonsElement = document.createElement("form");
    for (var index = 0, size = buttons.length; index < size; index++) {
        var button = document.createElement("button");
        button.type = "button";
        button.handler = buttons[index];
        button.onclick = function () {
            body.removeChild(messageElement);
            this.handler.handler();
        }
        button.appendChild(document.createTextNode(buttons[index].caption));
        buttonsElement.appendChild(button);
    }
    messageContent.appendChild(buttonsElement);
    messageElement.appendChild(messageContent);
    body.insertBefore(messageElement, body.firstChild);
}
