angular.module('CalculatorApp', [])
    .controller('CalculatorController', function ($scope) {

        $scope.updateDisplay = function (button, isOperator) {
            const display = $("#displayScreen");
            const input = display.text();

            let inputArray = [];
            let first, second;

            if (input.includes("/")) {
                inputArray = input.split("/");
                if (inputArray.length === 2 && inputArray[1] !== "") { // if last is empty means string ends with operator
                    if (isOperator) {
                        first = parseFloat(inputArray[0]);
                        second = parseFloat(inputArray[1]);
                        let answer = "" + (first / second);
                        if (answer.includes(".")) {
                            const answerArray = answer.split(".");
                            if (answerArray[1].length > 5) { // clips decimals to 5 decimal places
                                answer = answerArray[0] + "." + answerArray[1].substr(0, 5);
                            }
                        }
                        if (button !== "=") {
                            display.text(answer + "" + button);
                        } else {
                            display.text(answer + "");
                        }
                    } else {
                        display.text(input + "" + button);
                    }

                } else if (isOperator) {
                    this.clearDisplay();

                } else {
                    display.text(input + "" + button);
                }

            } else if (input.includes("*")) {
                inputArray = input.split("*");
                if (inputArray.length === 2  && inputArray[1] !== "") {
                    if (isOperator) {
                        first = parseFloat(inputArray[0]);
                        second = parseFloat(inputArray[1]);
                        let answer = "" + (first * second);
                        if (answer.includes(".")) {
                            const answerArray = answer.split(".");
                            if (answerArray[1].length > 5) {
                                answer = answerArray[0] + "." + answerArray[1].substr(0, 5);
                            }
                        }
                        if (button !== "=") {
                            display.text(answer + "" + button);
                        } else {
                            display.text(answer + "");
                        }

                    } else {
                        display.text(input + "" + button);
                    }

                } else if (isOperator) {
                    this.clearDisplay();

                } else {
                    display.text(input + "" + button);
                }

            } else if (input.includes("+")) {
                inputArray = input.split("+");
                if (inputArray.length === 2 && inputArray[1] !== "") {
                    if (isOperator) {
                        first = parseFloat(inputArray[0]);
                        second = parseFloat(inputArray[1]);
                        let answer = "" + (first + second);
                        if (answer.includes(".")) {
                            const answerArray = answer.split(".");
                            if (answerArray[1].length > 5) {
                                answer = answerArray[0] + "." + answerArray[1].substr(0, 5);
                            }
                        }
                        if (button !== "=") {
                            display.text(answer + "" + button);
                        } else {
                            display.text(answer + "");
                        }

                    } else {
                        display.text(input + "" + button);
                    }

                } else if (isOperator) {
                    this.clearDisplay();

                } else {
                    display.text(input + "" + button);
                }

            } else if (input.includes("-")) {
                inputArray = input.split("-");
                if (inputArray.length === 2 && inputArray[1] !== "") {
                    if (isOperator) {
                        first = parseFloat(inputArray[0]);
                        second = parseFloat(inputArray[1]);
                        let answer = "" + (first - second);
                        if (answer.includes(".")) {
                            const answerArray = answer.split(".");
                            if (answerArray[1].length > 5) {
                                answer = answerArray[0] + "." + answerArray[1].substr(0, 5);
                            }
                        }
                        if (button !== "=") {
                            display.text(answer + "" + button);
                        } else {
                            display.text(answer + "");
                        }

                    } else {
                        display.text(input + "" + button);
                    }

                } else if (isOperator) {
                    this.clearDisplay();

                } else {
                    display.text(input + "" + button);
                }

            } else {
                if (input === "0" && !isOperator) {
                    display.text("" + button); //removes leading zero from number
                } else {
                    if (button !== "=") {
                        display.text(input + "" + button);
                    } else {
                        display.text(input + "");
                    }
                }
            }
        };

        $scope.clearDisplay = function () {
            $("#displayScreen").text("0");
        }
    });

    //tried to make to make ng-model work on a span with a custom directive,
    // but it would not pick up the new value when added with js so just used jquery to grab and set text in display