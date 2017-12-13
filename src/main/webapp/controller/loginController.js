var ToDo = angular.module('ToDo');

ToDo.controller('loginController',function($scope,loginService,$location){
	$scope.loginUser= function(){
		var a = loginService.loginUser($scope.user);
		console.log(a);
		a.then(function(response){
			console.log(response.data.message);
			localStorage.setItem('token',response.data.message);
			$scope.errorMessage=response.data.message;
			console.log("login success");
			$location.path('home');
		},function(response){
			$scope.errorMessage=response.data.message;
			$location.path('login');
		});
	}
});