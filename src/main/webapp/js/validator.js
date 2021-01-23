function validateSaveProduct(form) {
    if (!validateForm(form, [{
        id: "name",
        message: "Поле «Инвентарный номер» должно быть задано в формате АБ###### или ЧЗ######<BR>(# - обязательная цифра)",
        checker: checkNotEmpty
    }, {
        id: "price",
        message: "Поле «Цена» не заполнено",
        checker: checkNotEmpty
    }, {
        id: "description",
        message: "Поле «Описание» не заполнено",
        checker: checkNotEmpty
    }])) {
        console.log('false');
        return false;
    }
    console.log('true');
    return true;
}

function checkPositiveInteger(value) {
    return checkInteger(value) && value > 0;
}

function checkInteger(value) {
    return checkNumber(value) && value == Math.round(value);
}

function checkNumber(value) {
    return !isNaN(parseFloat(value)) && isFinite(value);
}

function checkRegexp(value, regexp) {
    return new RegExp(regexp).test(value);
}

function checkNotEmpty(value) {
    return value.length != 0;
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
