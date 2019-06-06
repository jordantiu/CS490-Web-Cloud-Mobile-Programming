var equation = document.getElementById("equation");
var number = document.getElementById("number");
var button = document.getElementsByClassName("button");
var elementsArray = [];

clear();

function clear() {
    equation.innerHTML = "";
    number.innerHTML = "";
    elementsArray = [];
}

function clearNumber() {
    number.innerHTML = "";
};

function equationAdd(a, b) {
    elementsArray.push(a);
    elementsArray.push(b);
    equation.innerHTML += a;
    equation.innerHTML += b;
}

function multiply(a, b) {
    var c = parseInt(elementsArray[a]) * parseInt(elementsArray[b]);
    elementsArray[a] = c.toString();
    elementsArray.splice(a + 1, 2);
}

function divide(a, b) {
    var c = parseInt(elementsArray[a]) / parseInt(elementsArray[b]);
    elementsArray[a] = c.toString();
    elementsArray.splice(a + 1, 2);
}

function modulo(a, b) {
    var c = parseInt(elementsArray[a]) % parseInt(elementsArray[b]);
    elementsArray[a] = c.toString();
    elementsArray.splice(a + 1, 2);
}

function add(a, b) {
    var c = parseInt(elementsArray[a]) + parseInt(elementsArray[b]);
    elementsArray[a] = c.toString();
    elementsArray.splice(a + 1, 2);
}

function substract(a, b) {
    var c = parseInt(elementsArray[a]) - parseInt(elementsArray[b]);
    elementsArray[a] = c.toString();
    elementsArray.splice(a + 1, 2);
}


function calculate() {
    for (var i = 0; i < elementsArray.length; i++) {
        if (elementsArray[i] == "*") {
            multiply(i - 1, i + 1);
            i = i - 2;
        } else if (elementsArray[i] == "/") {
            divide(i - 1, i + 1);
            i = i - 2;
        } else if (elementsArray[i] == "%") {
            modulo(i - 1, i + 1);
            i = i - 2;
        }
    };
    for (var i = 0; i < elementsArray.length; i++) {
        if (elementsArray[i] == "+") {
            add(i - 1, i + 1);
            i = i - 2;
        } else if (elementsArray[i] == "-") {
            substract(i - 1, i + 1);
            i = i - 2;
        }
    }
};

for (var i = 0; i < button.length; i++) {
    button[i].addEventListener('click', function() {
        switch (this.innerHTML) {
            case "+":
                {
                    equationAdd(number.innerHTML, "+");
                    clearNumber();
                    break;
                }
            case "-":
                {
                    equationAdd(number.innerHTML, "-");
                    clearNumber();
                    break;
                }

            case "*":
                {
                    equationAdd(number.innerHTML, "*");
                    clearNumber();
                    break;
                }
            case "/":
                {
                    equationAdd(number.innerHTML, "/");
                    clearNumber();
                    break;
                }

            case "%":
                {
                    equationAdd(number.innerHTML, "%");
                    clearNumber();
                    break;
                }
            case "Clear":
                {
                    clear();
                    break;

                }

            case "=":
                {
                    equationAdd(number.innerHTML, "=");
                    elementsArray.pop();
                    calculate();
                    number.innerHTML = elementsArray[0];
                    equation.innerHTML = "";
                    elementsArray.pop();
                    break;
                }

            default:
                {
                    number.innerHTML += this.innerHTML;
                    break;
                };

        };
    });
}
