var app = angular.module('ToDo');
app.factory('homeService', function($http) {
	var notes = {};

	notes.noteCreation = function(note) {
		return $http({
			method : "post",
			url : "addNote",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}

	notes.getAllNotes = function() {
		var notes = {};
		return $http({
			method : "GET",
			url : "getNoteByUserId",
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}

	notes.updateNote = function(note) {
		return $http({
			method : "PUT",
			url : "updateNote",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}

	notes.deleteNote = function(note) {
		return $http({
			method : "delete",
			url : "deleteNote" + '/' + note.id,
			data : note
		})
	}

	notes.uploadImage = function(note) {
		return $http({
			method : "put",
			url : "updateNote",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}

	
	notes.uploadImage = function(note) {
		return $http({
			method : "put",
			templateUrl : "updateNote",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	return notes;

});