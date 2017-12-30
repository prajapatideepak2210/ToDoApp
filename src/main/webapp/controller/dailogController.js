var app = angular.module('ToDo');
app.controller('dailogController', function($scope, $mdDialog,note){

	console.log("note in controller note-title : "+note.title)
	$scope.dailogNote = note;
	$scope.dailogUpdate = function(note){
		console.log("hello dailog controller");
		
	}
	
	$scope.cancelDailog = function() {
		console.log("cancel the dailog");
	      $mdDialog.cancel();
	};
});