// 'use strict';

angular.module('Translate', [])
    .controller('Ctrl', function ($scope, $http) {
        $scope.translated = "";
        $scope.languages = new Array();

        const handler = $http.get("https://dictionary.yandex.net/api/v1/dicservice.json/getLangs" +
            "?key=dict.1.1.20190617T194813Z.ca93ddf313036564.7928044a9aeb1c668e5c64a188d1e8818d62332d"
        );
        handler.success(function (data) {
            if (data != null) {
                data.forEach(function (lang) {
                    $('#lang-dropdown')
                        .append($("<option></option>")
                            .attr("value",lang)
                            .text(lang));
                })
            }
        })
        handler.error(function (data) {
            alert("There was some error processing your request. Please try after some time.");
        });

        $scope.translate = function () {
            const text = document.getElementById("text-to-translate").value;
            if (text != null && text != "") {
                const lang = $('#lang-dropdown').val();

                var handler = $http.get("https://translate.yandex.net/api/v1.5/tr.json/translate" +
                    "?key=trnsl.1.1.20130922T110455Z.4a9208e68c61a760.f819c1db302ba637c2bea1befa4db9f784e9fbb8" +
                    "&text=" + text +
                    "&lang=" + lang
                );

                handler.success(function (data) {
                    if (data != null && data.text != undefined && data.text != null) {
                         $scope.translated = data.text[0];
                    }
                })
                handler.error(function (data) {
                    alert("There was some error processing your request. Please try after some time.");
                });
            }
        }
    });
