'use strict';

/* Services */ var varServices = angular.module('myServices', ['ngResource']); varServices.factory('FirstService', ['module1',function(module1){
    this.function1 = function () {
         return ...;
     };
     this.function2 = function () {
         return ...;
     };
     return this;
  }]);