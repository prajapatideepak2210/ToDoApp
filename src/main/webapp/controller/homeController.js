var ToDo = angular.module('ToDo');

ToDo.controller('homeController', function($scope, homeService) {
	$scope.noteCreate = function() {
		console.log("Create Note.....");
		var create = homeService.noteCreation($scope.note);
		create.then(function(response) {
			console.log(response.data.message);
			$scope.errorMessage = response.data.message;
			getNotes();
		}, function(response) {
			$scope.errorMessage = response.data.message;
		});
	}

	var getNotes = function() {
		var getNotes = homeService.getAllNotes();
		getNotes.then(function(response) {
			$scope.notes = response.data;
			console.log(response.data);
		}, function(response) {
			$scope.errormessage = response.data.message;
		});
	}
	getNotes();
});