(function () {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope', '$http'];

    function SearchController($scope, $http) {
        $scope.student = {};
        $scope.student.lastname = "test";
        $scope.student.firstname = "test";
        $scope.student.speciality = "test";
        $scope.student.phone = "test";
        $scope.student.mail = "test";
        $scope.student.address = "test";
        $scope.student.birthdate = "";
        $scope.student.birthdateope = "";





        $scope.datePickerOpenStatus = {};
        $scope.hide = 'student';
        $scope.show = function (str) {
            $scope.hide = str;
        };
        var t = $scope.request = "test";
        var isFirstStat = true;
        var opToken = "[operator]";
        var fieldToken = "[field]";
        var valueToken = "[valueoperator]";
        var compareToken = "[compareToken]";
        var andToken = "||";

        $scope.buildRequest = function (str) {
            isFirstStat = true;
            $scope.request = "";
            switch (str){
                case 'student':
                    buildStudentRequest();
                    break;
            }


            $http.get('api/search?request=' + $scope.request).then(function (response) {
                switch (str){
                    case 'student':
                        $scope.students = response.data;
                        break;
                }

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

        function buildStudentRequest() {


            if ($scope.student.lastname != "") {
                $scope.request += appendAnd() + fieldToken + "lastname" + opToken + "=" + compareToken + $scope.student.lastname;
            }
            if ($scope.student.firstname != "") {
                $scope.request += appendAnd() + fieldToken + "firstname" + opToken + "=" + compareToken + $scope.student.firstname;
            }
            if ($scope.student.speciality != "") {
                $scope.request += appendAnd() + fieldToken + "speciality" + opToken + "=" + compareToken + $scope.student.speciality;
            }
            if ($scope.student.phone != "") {
                $scope.request += appendAnd() + fieldToken + "phone" + opToken + "=" + compareToken + $scope.student.phone;
            }
            if ($scope.student.mail != "") {
                $scope.request += appendAnd() + fieldToken + "mail" + opToken + "=" + compareToken + $scope.student.mail;
            }
            if ($scope.student.address != "") {
                $scope.request += appendAnd() + fieldToken + "address" + opToken + "=" + compareToken + $scope.student.address;
            }
            if ($scope.student.birthDate != "") {
                $scope.request += appendAnd() + fieldToken + "birthdate" + opToken + $scope.student.birthdateope + compareToken + $scope.student.birthdate;
            }
        }
    }
})();
