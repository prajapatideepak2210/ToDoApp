var note=angular.module('ToDo');
note.directive('noteCard', function(){
	return{
		templateUrl: "template/notecard.html"
	};
});