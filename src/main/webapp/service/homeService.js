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
		return $http({
			method : "GET",
			url : "getNoteByUserId",
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	notes.getUser = function(){
		return $http({
			method : "GET",
			url : "getUser",
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
	
	notes.getOwner = function(note){
		return $http({
			method : 'POST',
			url : 'getOwner',
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	notes.collaborateUserWithNote = function(userName, note){
		return $http({
			method : 'POST',
			url : 'collaborateUser',
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token'),
				'userNameForCollaborate' : userName
			}
		})
	}
	
	return notes;
	
});