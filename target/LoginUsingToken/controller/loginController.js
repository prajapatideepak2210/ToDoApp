
var ToDo = angular.module('ToDo')

ToDo.controller('loginController',function($scope,loginService,$location){
	$scope.loginUser= function(){
		var a = loginService.loginUser($scope.user,$scope.error);
		console.log(a);
			a.then(function(response){
				console.log(response.data.message);
				localStorage.setItem('token',response.data.message);
				
				console.log("login success");
				$location.path('home');
			},function(response){
				if(response.status==409)
					{
						$scope.error=response.data.message;
					}
				else
					{	
						console.log("fail");
						$scope.error="Enter valid data";
					}
			});
	}
});