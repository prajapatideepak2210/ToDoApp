var app = angular.module('ToDo');
app.factory('homeService', function($http) {
	var notes = {};

	notes.noteCreation = function(note) {
		console.log("Notes inside servicve");
		return $http({
			method : "post",
			url : "addNote",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	notes.getAllNotes=function(note){
		return $http({
			method : "get",
			url : "getAllNotes",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	return notes;
})