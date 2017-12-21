var ToDo = angular.module('ToDo');

ToDo.controller('homeController', function($scope, homeService, Upload,
		$base64, mdcDateTimeDialog, $filter, $interval) {

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

	var getNotes = function() {
		var getNotes = homeService.getAllNotes();
		getNotes.then(function(response) {
			$scope.data = response.data;
			var notes = response.data;
			$interval(function() {
				for (var i = 0; i < $scope.data.length; i++) {

					if (notes[i].reminder) {
						var reminderNotesDate = notes[i].reminder;
						reminderNotesDate = $filter('date')(new Date(),
								'MMM dd yyyy HH:mm');
						var dates = $filter('date')(new Date(),
								'MMM dd yyyy HH:mm');
						console.log("" + dates);
						console.log("ReminderNotesDate---->"
								+ reminderNotesDate);
						if (dates == reminderNotesDate) {
							alert(notes[i].description);
						}
					}
				}
			}, 20000000000);
			$scope.notes = response.data;
		}, function(response) {
			$scope.errormessage = response.data.message;
		});
	}

	getNotes();

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
		reader.onload = $scope.imageIsLoaded;
		reader.readAsDataURL(note.noteBackGround);
		console.log(note.noteBackGround);
	}

	$scope.imageIsLoaded = function(image) {
		$scope.$apply(function() {
			$scope.stepsModel.push(image.target.result);
			console.log("image. target : " + image.target.result);
			var imageSrc = image.target.result;
			$scope.type.noteBackGround = imageSrc;
			console.log($scope.type)
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

	$scope.reminder = function(note) {
		/*
		 * var date = note.reminder; var reminderDate = $filter('date')(new
		 * Date(), 'yyyy-MM-dd hh:mm'); note.reminder = reminderDate;
		 */
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

});
