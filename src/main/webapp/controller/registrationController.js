
var app = angular.module('ToDo');

app.controller('registrationController',function($scope,registrationService,$location){
	$scope.registerUser= function(){
		var a = registrationService.registerUser($scope.user);
		console.log(a);
			a.then(function(response){
				console.log(response.data.message);
				localStorage.setItem('token',response.data.message);
				$scope.errorMessage=response.data.message;
				$location.path('login');
			},function(response){
				$scope.errorMessage=response.data.message;
				$location.path('registration');
			});
	}
});