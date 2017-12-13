
var ToDo = angular.module('ToDo')

ToDo.controller('forgotpasswordController',function($scope,forgotpasswordService,$location){
	$scope.forgotpassword= function(){
		var a = forgotpasswordService.forgotpasswordUser($scope.user);
		console.log(a);
		a.then(function(response){
			console.log(response.data.message);
			localStorage.setItem('token',response.data.message);
			$scope.errorMessage=response.data.message;
			$location.path('forgotpassword');
		},function(response){

			$scope.errorMessage=response.data.message;

		});
	}
});