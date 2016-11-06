
(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('MailController', MailController);

    MailController.$inject = ['$scope', '$http', 'Principal', 'LoginService', '$state', 'Student', 'Company', 'Stage'];

    function MailController ($scope, $http, Principal, LoginService, $state, Student, Company, Stage) {
        $scope.def = "Ceci est la page des mails";
        $scope.object = "";
        $scope.content = "";
        $scope.mails = [];


        
        Student.query(function (result) {
            $scope.students = result;
        });
        
        Company.query(function (result) {
            $scope.companies = result;
        });
        
       
        
     
        $scope.listMails = function(student) {
        	var mail = student.mail;
        	var iMail = $scope.mails.indexOf(mail);
        	var i = $scope.students.indexOf(student);
        	if (iMail == -1) {
        	$scope.mails.push(mail)
        	$scope.students[i].Selected=true;
        	
        }
        	else {
        		console.log(mail);
        		$scope.mails.splice(iMail, 1)
        		$scope.students[i].Selected=false;
        	}
        	console.log($scope.mails);
        }
        
        $scope.addInMails = function(idCompany) {
        	console.log("blabla");
        	var students = $scope.students;
        	for (var i = 0; i < students.length; i++) {
        		console.log(students[i].firstName);
        		var idCompany2 = $scope.students[i].stage.company.id;
        		console.log(idCompany2);
        		if(idCompany2 == idCompany) {
        			console.log("houra");
        			$scope.students[i].Selected=true;
        			var mail = students[i].mail;
        			if ($scope.mails.indexOf(mail) == -1) {
        	        	$scope.mails.push(mail)
        	        	console.log($scope.mails);
        	        }
        		}
        	}
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
