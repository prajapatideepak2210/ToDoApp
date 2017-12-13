var ToDo = angular.module('ToDo')

ToDo.controller('resetpasswordController', function($scope,
		resetpasswordService, $location) {
	$scope.resetpassword = function() {
		var a = resetpasswordService.resetpasswordUser($scope.passwordUser,
				$scope.error);
		console.log(a);
		a.then(function(response) {
			console.log("edcghwjehfcgjwhegfwjhdfwjhfdwejhfdwejhdfwejhd");
			console.log(response.data.message);
			localStorage.setItem('message', response.data.message);
			$location.path('login');
		}, function(response) {
			if (response.status == 400) {
				$scope.error = response.data.message;
				$location.path('registration')
			} else {
				$scope.error = message;
				$location.path('login')
			}
		});
	}
});