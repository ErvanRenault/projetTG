(function () {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope', '$http'];

    function SearchController($scope, $http) {
        $scope.student = {};
        $scope.student.id = "";
        $scope.student.lastname = "";
        $scope.student.lastname = "";
        $scope.student.firstname = "";
        $scope.student.speciality = "";
        $scope.student.phone = "";
        $scope.student.mail = "";
        $scope.student.address = "";
        $scope.student.birthdate = "";
        $scope.student.birthdateope = "";
        $scope.student.stage = "";
        $scope.student.enquete = "";
        $scope.student.teacher = "";
        $scope.student.contact = "";

        $scope.stage = {};
        $scope.stage.id = "";
        $scope.stage.startDate = "";
        $scope.stage.startDateOpe = "";
        $scope.stage.endDate = "";
        $scope.stage.endDateOpe = "";
        $scope.stage.address = "";
        $scope.stage.endingDateOpe = "";
        $scope.stage.endingDate = "";
        $scope.stage.student = "";
        $scope.stage.convention = "";
        $scope.stage.company = "";

        $scope.company = {};
        $scope.company.id = "";
        $scope.company.siret = "";
        $scope.company.name = "";
        $scope.company.address = "";
        $scope.company.mail = "";

        $scope.teacher = {};
        $scope.teacher.id = "";
        $scope.teacher.firstname = "";
        $scope.teacher.lastname = "";

        $scope.contact = {};
        $scope.contact.id = "";
        $scope.contact.lastname = "";
        $scope.contact.firstname = "";
        $scope.contact.mail = "";
        $scope.contact.phone = "";
        $scope.contact.company = "";

        $scope.convention = {};
        $scope.convention.id = "";
        $scope.convention.sujet = "";
        $scope.convention.studentSigned = "false";
        $scope.convention.companySigned = "false";
        $scope.convention.universitySigned = "false";
        $scope.convention.salary = "";

        $scope.enquete = {};
        $scope.enquete.id = "";
        $scope.enquete.name = "";
        $scope.enquete.phone = "";
        $scope.enquete.mail = "";
        $scope.enquete.hired = "false";
        $scope.enquete.stagePledge = "";
        $scope.enquete.jobPledge = "";
        $scope.enquete.comment = "";


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
            $scope.request = str + "|||";
            switch (str) {
                case 'student':
                    buildStudentRequest();
                    break;
                case 'stage':
                    buildStageRequest();
                    break;
                case 'company':
                    buildCompanyRequest();
                    break;
                case 'teacher':
                    buildTeacherRequest();
                    break;
                case 'contact':
                    buildContactRequest();
                    break;
                case 'convention':
                    buildConventionRequest();
                    break;
                case 'enquete':
                    buildEnqueteRequest();
                    break;
            }


            $http.get('api/search?request=' + $scope.request).then(function (response) {
                switch (str) {
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
                $scope.request += appendAnd() + fieldToken + "startDate" + opToken + $scope.student.birthdateope + compareToken + $scope.student.birthdate;
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
            if ($scope.student.stage != "") {
                $scope.request += appendAnd() + fieldToken + "stage" + opToken + "=" + compareToken + $scope.student.stage;
            }
            if ($scope.student.enquete != "") {
                $scope.request += appendAnd() + fieldToken + "enquete" + opToken + "=" + compareToken + $scope.student.enquete;
            }
            if ($scope.student.teacher != "") {
                $scope.request += appendAnd() + fieldToken + "teacher" + opToken + "=" + compareToken + $scope.student.teacher;
            }
            if ($scope.student.contact != "") {
                $scope.request += appendAnd() + fieldToken + "contact" + opToken + "=" + compareToken + $scope.student.contact;
            }

        }

        function buildStageRequest() {

            if ($scope.stage.startDate != "") {
                $scope.request += appendAnd() + fieldToken + "startDate" + opToken + $scope.stage.startDateOpe + compareToken + $scope.stage.startDate;
            }
            if ($scope.stage.endDate != "") {
                $scope.request += appendAnd() + fieldToken + "endDate" + opToken + $scope.stage.endDateOpe + compareToken + $scope.stage.startDate;
            }
            if ($scope.stage.address != "") {
                $scope.request += appendAnd() + fieldToken + "address" + opToken + "=" + compareToken + $scope.stage.address;
            }
            if ($scope.stage.endingDate != "") {
                $scope.request += appendAnd() + fieldToken + "endingDate" + opToken + $scope.stage.endingDateOpe + compareToken + $scope.stage.endingDate;
            }
            if ($scope.stage.student != "") {
                $scope.request += appendAnd() + fieldToken + "student" + opToken + "=" + compareToken + $scope.stage.student;
            }
            if ($scope.stage.convention != "") {
                $scope.request += appendAnd() + fieldToken + "convention" + opToken + "=" + compareToken + $scope.stage.convention;
            }
            if ($scope.stage.company != "") {
                $scope.request += appendAnd() + fieldToken + "company" + opToken + "="  + compareToken + $scope.stage.company;
            }
        }
    }
})();
