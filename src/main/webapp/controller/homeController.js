var ToDo = angular.module('ToDo');

ToDo.controller('homeController', function($scope, homeService, Upload,
		$base64, mdcDateTimeDialog, $filter, $interval, $mdDialog, $rootScope) {
	
	/* Creating the Note */
	
	$scope.noteCreate = function() {
		var create = homeService.noteCreation($scope.note);
		create.then(function(response) {
			console.log(response.data.message);
			$scope.errorMessage = response.data.message;
			getNotes();
		}, function(response) {
			$scope.errorMessage = response.data.message;
		});
	}
	
	/* geting All Notes */
	
	var getNotes = function() {
		var getNotes = homeService.getAllNotes();
		getNotes.then(function(response) {
			$scope.data = response.data;
			var note = response.data;
			
			$interval(function() {
				for (var i = 0; i < $scope.data.length; i++) {
					if (note[i].reminder != null) {
						reminderDate = $filter('date')(new Date(note[i].reminder),
								'MMM dd yyyy HH:mm');
						var currentDate = $filter('date')(new Date(),
								'MMM dd yyyy HH:mm');
						
						if (currentDate == reminderDate) {
							alert(note[i].description);
							note[i].reminder = null;
							homeService.updateNote(note[i]);
						}
					}
				}
			}, 50000);
			$scope.notes = response.data;
		}, function(response) {
			$scope.errormessage = response.data.message;
		});
	}

	getNotes();
	
	/* Trashing and untrashing the Note */
	
	$scope.trashNote = function(note) {
		if (note.trash)
			note.trash = false;
		else
			note.trash = true;

		var trashNote = homeService.updateNote(note);
		trashNote.then(function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response) {
			fileUpload
			$scope.errorMessage = response.data.message;
			console.log(response.data);
		});
	}
	
	/* deleting the Note */
	
	$scope.deleteNote = function(note) {
		var deleteNote = homeService.deleteNote(note);
		deleteNote.then(function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
		})
	}
	
	/* Archive and unarchive the Note */
	
	$scope.archiveNote = function(note) {
		if (note.archive)
			note.archive = false;
		else
			note.archive = true;
		var archiveNote = homeService.updateNote(note);
		archiveNote.then(function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
		})
	}
	
	/* Pin and unpin the Note */
	
	$scope.pinNote = function(note) {
		if (note.pin)
			note.pin = false;
		else
			note.pin = true;
		var pinNote = homeService.updateNote(note);
		pinNote.then(function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
		})
	}
	
	/* Updating the Note */
	
	$scope.updateNote = function(note) {
		var updateNote = homeService.updateNote(note);
		updateNote.then(function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
			getNotes();
		}, function(response) {
			$scope.errorMessage = response.data.message;
			console.log(response.data);
		})
	}
	
	$scope.updateNoteModel = function(){
		console.log("hello update");
	}

	/* Image Uploading */
	
	$scope.type = {};
	$scope.openHiddenButton = function(note) {
		$('#image').trigger('click');
		$scope.type = note;
	}

	$scope.stepsModel = [];
	$scope.imageUpload = function(note) {
		var reader = new FileReader();
		console.log("note : " + note);
		reader.onload = $scope.imageLoader;
		reader.readAsDataURL(note.noteBackGround);
		console.log(note.noteBackGround);
	}

	$scope.imageLoader = function(image) {
		$scope.$apply(function() {
			$scope.stepsModel.push(image.target.result);
			var imageSrc = image.target.result;
			$scope.type.noteBackGround = imageSrc;
			var updateResponse = homeService.updateNote($scope.type);
			updateResponse.then(function(response) {
				console.log(response);
				getNotes();
			}, function(response) {
				console.log(response);
			});
		});
	}

	/* reminder */

	$scope.addReminder = function(note) {
		
		console.log("hello reminder : " + note.reminder);
		var reminder = homeService.updateNote(note);
		reminder.then(function(response) {
			$scope.errormessage = response.data.message;
			console.log(response.data);
		}, function(response) {
			$scope.errormessage = response.data.message;
			console.log(response.data);
		});

	}
	
	/* Removing the Note */
	
	$scope.removeReminder = function(note){
		note.reminder = null;
		var removeReminder = homeService.updateNote(note);
		removeReminder.then(function(response){
			$scope.errormessage = response.data.message;
			console.log(response.data);
		}, function(response) {
			$scope.errormessage = response.data.message;
			console.log(response.data);
		});
	}
	
	/* change color	*/
	
	$scope.options = ['transparent','#FF8A80', '#FFD180', '#FFFF8D', '#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90'];
	$scope.color = "#FF8A80";
	     
	$scope.colorChanged = function(newColor, note) {
		note.noteColor = newColor;
		var colorChanged = homeService.updateNote(note);
		colorChanged.then(function(response){
			$scope.errormessage = response.data.message;
			console.log(response.data);
		}, function(response) {
			$scope.errormessage = response.data.message;
			console.log(response.data);
		})
	}
	
	/* Dailog Box */
	
	$scope.showDialog=function(note){
		$scope.note = note;
		$mdDialog.show({
			contentElement: '#myStaticDialog',
		    clickOutsideToClose:true,
		    parent: angular.element(document.body)
		});
	}
	
	$scope.cancelDailog = function(){
		$mdDialog.cancel();
	}
	
	$scope.updateDailogNote = function(note){
		$scope.updateDailogNote = homeService.updateNote(note);
		
		updateDailogNote.then(function(response){
			$scope.errormessage = response.data.message;
			console.log(response.data);
			$mdDialog.hide();
		}, function(response){
			$scope.errormessage = resposne.data.message;
			console.log(response.data);
		})
	}
	
	/*=============================================== Add Note ============================================*/
	
	/*$scope.IsVisible = false;
    $scope.ShowHide = function () {
        //If DIV is visible it will be hidden and vice versa.
        $scope.IsVisible = $scope.IsVisible ? false : true;
    }*/
	
	/*============================================= Collaborator =============================================*/
	
	$scope.collaborators = function(note,event)
	{
		console.log("inside collaboarator");
		$mdDialog.show({
			locals:{
				data : note,
				owner :$scope.owner,
				listOfUser: $scope.userList
			},
			templateUrl : 'template/tabDialog.html',
			 parent: angular.element(document.body),
		     targetEvent: event,
		     clickOutsideToClose: true,
		     controllerAs: 'controller',
		     //controller: opencollaboratorsModel
		});
	}
	
});
