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
			url : "getAllNotes",
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
		console.log(note);
		return $http({
			method : "DELETE",
			url : "deleteNote",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token'),
				'note_id' : note.id
			}
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
	
	notes.deleteCollabUser = function(collabUser, note){
		return $http({
			method : 'POST',
			url : 'deleteCollabUser',
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token'),
				'userToDelete' : collabUser
			}
		})
	}
	
	notes.addNoteInLabel = function(labelName, note){
		console.log("hello user you are in service method");
		console.log(labelName);
		return $http({
			method : 'POST',
			url : 'addNoteInLabel',
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token'),
				'labelName' : labelName
			}
		})
	}
	
	notes.createLabel = function(labelName){
		return $http({
			method : 'POST',
			data : labelName,
			url : 'createLabel',
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	notes.getLabels = function(){
		return $http({
			method : 'GET',
			url : 'getLabels',
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		});
	}
	
	notes.deleteLabel = function(label){
		return $http({
			method : 'POST',
			data : label,
			url : 'deleteLabel',
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	notes.updateLabel = function(label){
		return $http({
			method : 'POST',
			data : label,
			url : 'updateLabel',
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	notes.updateNoteInLabel = function(note, label){
		console.log(label.id)
		return $http({
			method : 'POST',
			data : note,
			url : 'updateNoteInLabel',
			headers : {
				'TokenAccess' : localStorage.getItem('token'),
				'labelId' : label.id
			}
		})
	}
	
	return notes;
	
});