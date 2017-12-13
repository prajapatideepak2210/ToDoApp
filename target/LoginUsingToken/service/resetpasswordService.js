var app = angular.module('ToDo');
app.factory('resetpasswordService', function($http, $location){
	var reset ={};
	
	reset.resetpasswordUser = function(passwordUser){
		return $http({
			method : "put",
			url : "resetPassword",
			data : passwordUser
			
		})
	}
	return reset;
})