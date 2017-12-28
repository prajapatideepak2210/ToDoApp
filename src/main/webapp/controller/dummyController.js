var ToDo = angular.module('ToDo');
ToDo.controller('dummyController',function($location, dummyService){
	var dummyService=dummyService.service();
	dummyService.then(function(response){
		localStorage.setItem('token',response.data.message);
		$location.path('home');
	},function(response){
		console.log("home error");
	});

});
