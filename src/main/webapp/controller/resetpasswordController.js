var ToDo = angular.module('ToDo')

ToDo.controller('resetpasswordController', function($scope,
		resetpasswordService, $location) {
	$scope.resetpassword = function() {
		var a = resetpasswordService.resetpasswordUser($scope.passwordUser,
				$scope.error);
		console.log(a);
		a.then(function(response) {
			console.log(response.data.message);
			localStorage.setItem('message', response.data.message);
			$scope.errorMessage=response.data.message;
			$location.path('resetpassword');
		}, function(response) {
			$scope.errorMessage=response.data.message;
		});
	}
});