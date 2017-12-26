var app = angular.module('ToDo');

app.factory('dummyService', function($http, $location) {
	
var login={};
login.service=function(){
	return $http({
		method : 'GET',
		url : 'getToken',
	})
	}
return login;
})