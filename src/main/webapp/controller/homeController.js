var ToDo = angular.module('ToDo');

ToDo.controller('homeController', function($scope, homeService) {
	$scope.noteCreate = function() {
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
	
	$scope.trashNote = function(note) {
		if(note.trash)
			note.trash = false;
		else 
			note.trash = true;
		
		var trashNote=homeService.updateNote(note);
		trashNote.then(function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data);
		});
	}
	
	$scope.deleteNote = function(note){
		var deleteNote = homeService.deleteNote(note);
		deleteNote.then(function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data);
		})
	}
		  
	$scope.archiveNote = function(note){
		if(note.archive)
			note.archive=false;
		else
			note.archive=true;
		var archiveNote = homeService.updateNote(note);
		archiveNote.then(function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response){
			$scope.errorMessage= response.data.message;
			console.log(response.data);
		})
	}
	
	$scope.pinNote = function(note){
		if(note.pin)
			note.pin = false;
		else
			note.pin = true;
		var pinNote = homeService.updateNote(note);
		pinNote.then(function(response){
			$scope.errorMessage = response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response){
			$scope.errorMessage = response.data.message;
			console.log(response.data);
		})
	}
	
	$scope.updateNote = function(note){
		var updateNote = homeService.updateNote(note);
		updateNote.then(function(response){
			$scope.errorMessage = response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response){
			$scope.errorMessage = response.data.message;
			console.log(response.data);
		})
	}
	
});