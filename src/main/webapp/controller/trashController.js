var ToDo = angular.module('ToDo');

ToDo.controller('trashController', function($scope, trashService , $state){
	$scope.trashNote = function(note) {
		if(note.trash)
			note.trash = false;
		else 
			note.trash = true;
		
		var trashNote=trashService.getTrashNotes();
		trashNote.then(function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data);
		}, function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data);
		});
		getNotes();	
	}
	
	$scope.deleteNote = function(note){
		var deleteNote = trashService.deleteNote();
		deleteNote.then(function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data.message);
		}, function(response){
			$scope.errorMessage=response.data.message;
			console.log(response.data.message);
		})
	}
})