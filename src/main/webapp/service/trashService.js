var app = angular.module('ToDo');
app.factory('trashService', function($http) {
	var notes = {};

	notes.getAllNotes = function() {
		return $http({
			method : "GET",
			url : "getAllNotes",
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	notes.getAuthor = function(){
		return localStorage.getItem('token');
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
	
	notes.deleteNoteLabel = function(label, note){
		console.log(note.id);
		return $http({
			method : 'POST',
			data : label,
			url : 'deleteLabelFromNote',
			headers : {
				'TokenAccess' : localStorage.getItem('token'),
				'NoteId' : note.id
			}
		})
	}
	
	notes.copyNote = function(note){
		return $http({
			method : 'POST',
			data : note,
			url : 'copyNote',
			headers : {
				'TokenAccess' : localStorage.getItem('token'),
			}
		})
	}
	
	return notes;
	
});