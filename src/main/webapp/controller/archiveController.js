var ToDo = angular.module('ToDo');

ToDo
		.controller(
				'archiveController',
				function($scope, archiveService, Upload, $base64,
						mdcDateTimeDialog, $filter, $interval, $location,
						$mdDialog, $rootScope, $mdSidenav, $timeout,$state) {

					/* Creating the Note */

					$scope.noteCreate = function() {
						var create = archiveService.noteCreation($scope.note);
						create.then(function(response) {
							console.log(response.data.message);
							$scope.errorMessage = response.data.message;
							getNotes();
						}, function(response) {
							$scope.errorMessage = response.data.message;
						});
					}

					$scope.copyNote = function(note) {
						$scope.copyNote = archiveService.copyNote(note);
						$scope.copyNote.then(function(response) {
							$scope.data = response.data;
							console.log(response.data);
							getNotes();
						}, function(response) {
							$scope.errorMessage = response.data.message;
						})
					}

					$scope.notes = [];

					/* geting All Notes */

					var getNotes = function() {
						$scope.notes = [];
						var getN = archiveService.getAllNotes();
						getN.then(function(response) {
							console.log("Get All Notes", response.data);
							var note = response.data;
							$interval(function() {
								for (var i = 0; i < note.length; i++) {
									if (note[i].reminder != null) {
										reminderDate = $filter('date')(
												new Date(note[i].reminder),
												'MMM dd yyyy HH:mm');
										var currentDate = $filter('date')
												(new Date(),
														'MMM dd yyyy HH:mm');

										if (currentDate == reminderDate) {
											alert(note[i].description);
											note[i].reminder = null;
											archiveService.updateNote(note[i]);
										}
									}
								}
							}, 50000);
							$scope.notes = response.data;
						}, function(response) {
							// $scope.errormessage = response.data.message;
						});
					}

					var getUser = function() {
						var getUser = archiveService.getUser();
						getUser.then(function(response) {
							$scope.user = response.data;
							console.log($scope.user);
						}, function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						})
					}
					var getLabels = function() {
						var getLabels = archiveService.getLabels();
						getLabels.then(function(response) {
							$scope.labels = response.data;
							console.log(response.data);
						}, function(response) {
							$scope.errorMessage = response.data.message;
							console.log(response.data.message);
						})
					}
					getLabels();
					getUser();
					getNotes();

					/* Trashing and untrashing the Note */

					$scope.trashNote = function(note) {
						if (note.trash)
							note.trash = false;
						else
							note.trash = true;

						var trashNote = archiveService.updateNote(note);
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
						var deleteNote = archiveService.deleteNote(note);
						deleteNote.then(function(response) {
							$scope.message = response.data.message;
							console.log(response.data);
							getNotes();
						}, function(response) {
							$scope.errorMessage = response.data.message;
						})
					}

					/* Archive and unarchive the Note */

					$scope.archiveNote = function(note) {
						if (note.archive)
							note.archive = false;
						else
							note.archive = true;
						var archiveNote = archiveService.updateNote(note);
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
						var pinNote = archiveService.updateNote(note);
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
						var updateNote = archiveService.updateNote(note);
						updateNote.then(function(response) {
							$scope.errorMessage = response.data.message;
							console.log(response.data);
							getNotes();
						}, function(response) {
							$scope.errorMessage = response.data.message;
							console.log(response.data);
						})
					}

					

					/*
					 * ============================================= Image
					 * Uploading ========================================
					 */

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
							var updateResponse = archiveService
									.updateNote($scope.type);
							updateResponse.then(function(response) {
								console.log(response);
								getNotes();
							}, function(response) {
								console.log(response);
							});
						});
					}

					/*
					 * ====================================== reminder
					 * =========================================
					 */

					$scope.addReminder = function(note) {

						console.log("hello reminder : " + note.reminder);
						var reminder = archiveService.updateNote(note);
						reminder.then(function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						}, function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						});

					}

					/* Removing the Note */

					$scope.removeReminder = function(note) {
						note.reminder = null;
						var removeReminder = archiveService.updateNote(note);
						removeReminder.then(function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						}, function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						});
					}

					/* change color */

					$scope.options = [ 'transparent', '#FF8A80', '#FFD180',
							'#FFFF8D', '#CFD8DC', '#80D8FF', '#A7FFEB',
							'#CCFF90' ];
					$scope.color = "#FF8A80";

					$scope.colorChanged = function(newColor, note) {
						note.noteColor = newColor;
						var colorChanged = archiveService.updateNote(note);
						colorChanged.then(function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						}, function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						})

					}

					/* Dailog Box */

					$scope.showDialog = function(note) {
						$scope.note = note;
						$mdDialog.show({
							contentElement : '#myStaticDialog',
							clickOutsideToClose : true,
							parent : angular.element(document.body)
						});
					}

					$scope.updateDailogNote = function(note) {
						$scope.updateDailogNote = archiveService.updateNote(note);

						updateDailogNote.then(function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
							$mdDialog.hide();
						}, function(response) {
							$scope.errormessage = resposne.data.message;
							console.log(response.data);
						})
					}

/* ============================================= grid and list view ===================================== */

					
					
				/*	$scope.view="true";

					$scope.flex="30";
					$scope.align1="start";
					$scope.align2="start";
					$scope.changeView=function(){

						if($scope.view){
							$scope.flex="65";
							$scope.align1="center";
							$scope.align2="center";
							$scope.view=!$scope.view;
						}else
						{
							$scope.flex="30";
							$scope.align1="start";
							$scope.align2="start";
							$scope.view=!$scope.view;	
						}
						
						
					}*/
					
					
					
					$scope.view = true;
					$scope.customWidth = 300;
					$scope.grid = 0;
					$scope.changeView = function() {
						if ($scope.view) {
							$scope.view = false;
							$scope.customWidth = 700;
							$scope.gride = 33;
						} else {
							$scope.customWidth = 300;
							$scope.view = true;
						}
					}
					
					/*
					 * =============================================== Add
					 * Note============================================
					 */

					/*
					 * $scope.IsVisible = false; $scope.ShowHide = function () {
					 * //If DIV is visible it will be hidden and vice versa.
					 * $scope.IsVisible = $scope.IsVisible ? false : true; }
					 */

					/*
					 * =============================================Collaborator
					 * =============================================
					 */
					/*
					 * $scope.getOwner=function(note) { var collaborator =
					 * archiveService.getOwner(note);
					 * collaborator.then(function(response){
					 * console.log(response.data); $scope.localNoteOwner =
					 * response.data; }, function(response){ $scope.errormessage =
					 * response.data.message; }) }
					 */

					$scope.collaborators = function(note, event) {

						$mdDialog
								.show({

									locals : {
										data : note,
										owner : $scope.ownerDetails,
										listOfUser : $scope.userList
									},
									templateUrl : 'template/ColaboratorDialog.html',
									parent : angular.element(document.body),
									targetEvent : event,
									clickOutsideToClose : true,

									controller : function($scope, data) {

										$scope.note = data;

										$scope.collaborateUserWithNote = function(
												userName, note) {
											console.log("note : " + note.title);
											var collaborate = archiveService
													.collaborateUserWithNote(
															userName, note);
											collaborate
													.then(
															function(response) {
																$scope.message = response.data.message;
																console
																		.log(response.data);
																$mdDialog
																		.hide();
																getNotes();
															},
															function(response) {
																$scope.errorMessage = response.data.message;
																console
																		.log(response.data);
															})
										}

										var getOwner = function(data) {
											var collaborator = archiveService
													.getOwner(note);
											collaborator
													.then(
															function(response) {
																console
																		.log(response.data);
																$scope.ownerDetails = response.data;
															},
															function(response) {
																$scope.errormessage = response.data.message;

															})
										}

										getOwner(data);

										$scope.cancel = function() {
											$mdDialog.cancel();
										}

										$scope.deleteUserFromCollaborator = function(
												collabUser, note) {
											var deleteCollabUser = archiveService
													.deleteCollabUser(
															collabUser, note);
											deleteCollabUser
													.then(
															function(response) {
																$scope.message = response.data.message;
																console
																		.log(response.data);
																getNotes();
															},
															function(response) {
																$scope.message = response.data.message;
																console
																		.log(response.data);
															})
										}
									}
								});
					}

					/*
					 * ============================================== Lable
					 * =================================================
					 */

					$scope.lables = function() {
						$mdDialog
								.show({

									templateUrl : 'template/lableDailog.html',
									parent : angular.element(document.body),
									clickOutsideToClose : true,
									targetEvent : event,
									controller : function($scope) {
										console.log("hello dailog");
										$scope.cancel = function() {
											$mdDialog.cancel();
										}

										$scope.createLabel = function(labelName) {
											$scope.createLabel = archiveService
													.createLabel(labelName);
											$scope.createLabel
													.then(
															function(response) {
																$scope.message = response.data.message;
																console
																		.log(response.data);
																getLabels();
																getNotes();
																$mdDialog
																		.hide();
															},
															function(response) {
																$scope.errorMessage = response.data.message;
																console
																		.log(response.data);
															})

										}

										var getLabels = function() {
											var getLabels = archiveService
													.getLabels();
											getLabels
													.then(
															function(response) {
																$scope.labels = response.data;
																console
																		.log(response.data);
															},
															function(response) {
																$scope.errorMessage = response.data;
																console
																		.log(response.data);
															})
										}
										getLabels();

										$scope.deleteLabel = function(label) {
											console.log("hello label");
											console.log(label);
											$scope.deleteLabel = archiveService
													.deleteLabel(label);
											$scope.deleteLabel
													.then(
															function(response) {
																$scope.message = response.data;
																console
																		.log(response.data);
																getLabels();
																getNotes();
																$mdDialog
																		.hide();
															},
															function(response) {
																$scope.errorMessage = response.data;
																console
																		.log(response.data);
															})
										}

										$scope.updateLabel = function(label) {
											$scope.updateLabel = archiveService
													.updateLabel(label);
											$scope.updateLabel
													.then(
															function(response) {
																$scope.responseData = response.data;
																console
																		.log(response.data);
																$mdDialog
																		.hide();
															},
															function(response) {
																$scope.errorMessage = response.data;
																console
																		.log(response.data);
															})
										}
									}
								});

					}

					$scope.addNoteInLabel = function(note) {

						$mdDialog
								.show({

									locals : {
										data : note,
									},
									templateUrl : 'template/noteLabelDailog.html',
									parent : angular.element(document.body),
									clickOutsideToClose : true,
									targetEvent : event,
									controller : function($scope, data) {

										$scope.note = data;
										console.log("hello dailog");
										$scope.cancel = function() {
											$mdDialog.cancel();
										}

										$scope.addNote = function(labelName,
												note) {
											$scope.addNote = archiveService
													.addNoteInLabel(labelName,
															note);
											$scope.addNote
													.then(
															function(response) {
																$scope.message = response.data.message;
																console
																		.log(response.data);
																getLabels();
																getNotes();
																$mdDialog
																		.hide();
															},
															function(response) {
																$scope.errorMessage = response.data.message;
																console
																		.log(response.data.message);
															})
										}

										var getLabels = function() {
											var getLabels = archiveService
													.getLabels();
											getLabels
													.then(
															function(response) {
																$scope.labels = response.data;
																console
																		.log(response.data);
															},
															function(response) {
																$scope.errorMessage = response.data.message;
																console
																		.log(response.data);
															})
										}

										$scope.updateNoteInLabel = function(
												note, label) {
											$scope.updateNoteInLabel = archiveService
													.updateNoteInLabel(note,
															label);
											$scope.updateNoteInLabel
													.then(
															function(response) {
																$scope.data = response.data;
																console
																		.log(response.data);
																getLabels();
															}, function(
																	response) {

															})
										}

										getLabels();
									}

								})
						getLabels();
						getNotes();
					}

					$scope.deleteNoteFromLabel = function(label, note) {
						console.log(note);
						$scope.deleteLabel = archiveService.deleteNoteLabel(label,
								note);
						$scope.deleteLabel.then(function(response) {
							$scope.data = response.data;
							console.log(response.data);
							getLabels();
							getNotes();
						}, function(response) {
							$scope.errorMessage = response.data.message;
						})
					}

					/* ===========================================LogOut============================================== */

					$scope.logOut = function(user) {
						console.log(user);
						$mdDialog.show({

							locals : {
								data : user,
							},
							templateUrl : 'template/logoutDailog.html',
							parent : angular.element(document.body),
							clickOutsideToClose : true,
							controller : function($scope, data) {
								$scope.user = data;
								$scope.userLogout = function() {
									localStorage.removeItem('token');
									$location.path('login');
									$mdDialog.hide();
								}
							}
						})
					}

					/*
					 * =========================================== Note in label
					 * ========================================
					 */

					$scope.noteInLabel = function(notes, labelName) {
						
						localStorage.setItem('labelName', labelName);
						$scope.labelName = localStorage.getItem('labelName');
						$state.go("notesInLabel");
					}
					
	                $scope.labelName = localStorage.getItem('labelName');
	         

					/*
					 * =========================================== Search
					 * ================================================
					 */

					$scope.search = function() {
						$location.path('search');
					}

					$scope.searchAll = function(text) {
						var result = [];
						$scope.searchNotes = result;
						if (text.length > 0) {

							var notes = $scope.notes;
							var index = 0;
							var result = [];

							for (var i = 0; i < notes.length; i++) {
								if ((notes[i].title.toLowerCase()).search(text) > -1) {
									result[index++] = notes[i];
								} else if ((notes[i].description.toLowerCase())
										.search(text) > -1) {
									result[index++] = notes[i];
								}
							}
							console.log(result);
							$scope.searchNotes = result;
						}
						return result;
					}

					/*
					 * ====================================== toggel
					 * sidebar=======================================
					 */

					$scope.toggleRight = buildToggler('right');
					$scope.toggleLeft = buildToggler('left');
					function buildToggler(componentId) {
						return function() {
							$mdSidenav(componentId).toggle();
						};
					}

});
