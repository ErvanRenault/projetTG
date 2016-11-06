
(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('MailController', MailController);

    MailController.$inject = ['$scope', '$http', 'Principal', 'LoginService', '$state', 'Student'];

    function MailController ($scope, $http, Principal, LoginService, $state, Student) {
        $scope.def = "Ceci est la page des mails";
        $scope.visibleStudent = false;
        $scope.object = "";
        $scope.content = "";
        $scope.mails = [];


        $scope.findStudents = function() {
            Student.query(function (result) {
                $scope.students = result;
                for (var student in result) {
                	var mail = result[student].mail
                	console.log(mail);
                }
					
				
                $scope.visibleStudent = !$scope.visibleStudent;
            });
        }
        
        $scope.listMails = function(mail) {
        	if ($scope.mails.indexOf(mail) == -1) {
        	$scope.mails.push(mail)
        }
        	else {
        		$scope.mails.splice(mail, 1)
        	}
        	console.log($scope.mails);
        }
        
        $scope.sendMail = function() {
        	console.log("yolo");
        	console.log($scope.object);
        	console.log($scope.content);
        	 $http({
           	  method: "POST",
           	  url: "http://127.0.0.1:8080/api/mail",
           	  data: {"mails" : $scope.mails, "object" : $scope.object, "content" : $scope.content}	
           	}).then(function successCallback(response) {
           	    console.log("yolo2");
           	    console.log(response);
           	  }, function errorCallback(response) {
           	    console.log("yolo3");
           	  });
        }
    }
})();
