var app = angular.module('ToDo');

app.factory('trashService', function($http) {
	var trashNotes = {};
	
	trashNotes.getTrashNotes=function(){
		console.log("Hello Trash Service");
		return $http({
			method : "get",
			url : "getNoteByUserId",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	
	trashNotes.deleteNote=function(note){
		console.log("inside deleteNote service.");
		return $http({
			method : "delete",
			url : "deleteNote",
			data : note,
			headers : {
				'TokenAccess' : localStorage.getItem('token')
			}
		})
	}
	return trashNotes;
});