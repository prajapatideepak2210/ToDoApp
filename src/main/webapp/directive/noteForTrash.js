var note=angular.module('ToDo');
note.directive('noteForTrash', function(){
	return{
		templateUrl: "template/notefortrash.html"
	};
});