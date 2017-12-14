var ToDo = angular.module('ToDo');

ToDo.controller('homeController',function($scope,homeService,$state){
	$scope.noteCreate= function(){
		console.log("Create Note.....");
		var create = homeService.noteCreation($scope.note);
		console.log("hi................."+$scope.note);
		create.then(function(response){
			console.log(response.data.message);
			$scope.errorMessage=response.data.message;
			$state.go('home');
		},function(response){
			$scope.errorMessage=response.data.message;
			$state.go('home');
		});
	}
	
	$scope.getNote=function(){
		var get=homeService.getAllNote($scope.note);
		get.then(function(note){
			
			$scope.notes=note.data;
			$state.go('home');
		},function(response){
			$scope.errormessage=response.data.message;
			$state.go('home');
		});
	}
});