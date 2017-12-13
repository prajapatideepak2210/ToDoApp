
var ToDo = angular.module('ToDo')

ToDo.controller('forgotpasswordController',function($scope,forgotpasswordService,$location){
	$scope.forgotpassword= function(){
		var a = forgotpasswordService.forgotpasswordUser($scope.user);
		console.log(a);
			a.then(function(response){
				console.log(response.data.message);
				localStorage.setItem('token',response.data.message);
				
				console.log("login success");
				$location.path('forgotpassword');
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