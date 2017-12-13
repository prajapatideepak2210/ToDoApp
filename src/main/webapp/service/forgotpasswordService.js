var app = angular.module('ToDo');
app.factory('forgotpasswordService', function($http, $location){
	var forgot ={};
	
	forgot.forgotpasswordUser = function(user){
		return $http({
			method : "post",
			url : "forgotPassword",
			data : user
		})
	}
	return forgot;
})