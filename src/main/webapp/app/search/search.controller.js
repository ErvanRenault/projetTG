(function () {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope', '$http'];

    function SearchController($scope, $http) {
        $scope.lastname = "test";
        $scope.firstname = "test";
        $scope.speciality = "test";
        $scope.phone = "test";
        $scope.mail = "test";
        $scope.address = "test";
        $scope.birthdate = "";
        $scope.birthdateope = "";
        $scope.datePickerOpenStatus = {};
        var t = $scope.request = "test";
        var isFirstStat = true;
        var opToken = "[operator]";
        var fieldToken = "[field]";
        var valueToken = "[valueoperator]";
        var compareToken = "[compareToken]";
        var andToken = "||";

        $scope.buildRequest = function () {
            isFirstStat = true;
            $scope.request = "";
            if ($scope.lastname != "") {
                $scope.request += appendAnd() + fieldToken + "lastname" + opToken + "=" + compareToken + $scope.lastname;
            }
            if ($scope.firstname != "") {
                $scope.request += appendAnd() + fieldToken + "firstname" + opToken + "=" + compareToken + $scope.firstname;
            }
            if ($scope.speciality != "") {
                $scope.request += appendAnd() + fieldToken + "speciality" + opToken + "=" + compareToken + $scope.speciality;
            }
            if ($scope.phone != "") {
                $scope.request += appendAnd() + fieldToken + "phone" + opToken + "=" + compareToken + $scope.phone;
            }
            if ($scope.mail != "") {
                $scope.request += appendAnd() + fieldToken + "mail" + opToken + "=" + compareToken + $scope.mail;
            }
            if ($scope.address != "") {
                $scope.request += appendAnd() + fieldToken + "address" + opToken + "=" + compareToken + $scope.address;
            }
            if ($scope.birthDate != "") {
                $scope.request += appendAnd() + fieldToken + "birthdate" + opToken + $scope.birthdateope + compareToken + $scope.birthdate;
            }

            $http.get('api/search?request=' + $scope.request).then(function (response) {
                alert("1");
                $scope.students = response.data;
            }, function (response) {
                alert("2");
            });
        };
        $scope.datePickerOpenStatus.birthDate = false;

       $scope.openCalendar = function (date) {
            $scope.datePickerOpenStatus[date] = true;
        }

        function appendAnd() {
            if (isFirstStat) {
                isFirstStat = false;
                return "";
            } else {
                return andToken;
            }
        }
    }
})();
