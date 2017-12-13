var ToDo = angular.module('ToDo');

ToDo.controller('homeController',function($scope,homeService,$location){
	$scope.homeUser= function(){
		var a = homeService.homeUser($scope.user);
		console.log(a);
		a.then(function(response){
			console.log(response.data.message);
			localStorage.setItem('token',response.data.message);
			$scope.errorMessage=response.data.message;
			$location.path('home');
		},function(response){
			$scope.errorMessage=response.data.message;
			$location.path('home');
		});
	}
});