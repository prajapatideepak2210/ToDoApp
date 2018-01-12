var ToDo = angular.module('ToDo');

ToDo
		.controller(
				'trashController',
				function($scope, trashService, Upload, $base64,
						mdcDateTimeDialog, $filter, $interval, $location,
						$mdDialog, $rootScope, $mdSidenav, $timeout,$state) {


					$scope.copyNote = function(note) {
						$scope.copyNote = trashService.copyNote(note);
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
						var getN = trashService.getAllNotes();
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
											trashService.updateNote(note[i]);
										}
									}
								}
							}, 50000);
							$scope.notes = response.data;
						}, function(response) {
							// $scope.errormessage = response.data.message;
						});
					}

					var getAuthor = function(){
						$scope.author = trashService.getAuthor();
						if($scope.author==null){
							$location.path('login');
						}
					}
					getAuthor();
					
					var getUser = function() {
						var getUser = trashService.getUser();
						getUser.then(function(response) {
							$scope.user = response.data;
							console.log($scope.user);
						}, function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						})
					}
					var getLabels = function() {
						var getLabels = trashService.getLabels();
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

						var trashNote = trashService.updateNote(note);
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
						var deleteNote = trashService.deleteNote(note);
						deleteNote.then(function(response) {
							$scope.message = response.data.message;
							console.log(response.data);
							getNotes();
						}, function(response) {
							$scope.errorMessage = response.data.message;
						})
					}


					/* Pin and unpin the Note */

					$scope.pinNote = function(note) {
						if (note.pin)
							note.pin = false;
						else
							note.pin = true;
						var pinNote = trashService.updateNote(note);
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
						var updateNote = trashService.updateNote(note);
						updateNote.then(function(response) {
							$scope.errorMessage = response.data.message;
							console.log(response.data);
							getNotes();
						}, function(response) {
							$scope.errorMessage = response.data.message;
							console.log(response.data);
						})
					}

					$scope.updateNoteModel = function() {
						console.log("hello update");
					}

					
					/*
					 * ====================================== reminder
					 * =========================================
					 */

					/* Removing the Note */

					$scope.removeReminder = function(note) {
						note.reminder = null;
						var removeReminder = trashService.updateNote(note);
						removeReminder.then(function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						}, function(response) {
							$scope.errormessage = response.data.message;
							console.log(response.data);
						});
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
						$scope.updateDailogNote = trashService.updateNote(note);

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

					$scope.view = true;
					$scope.customWidth = 300;
					$scope.grid = 0;
					$scope.changeView = function() {
						if ($scope.view) {
							$scope.view = false;
							$scope.customWidth = 700;
							$scope.gride = 33;
							getNotes();
						} else {
							$scope.customWidth = 300;
							$scope.view = true;
							getNotes();
						}
					}

/* ============================================== Lable ================================================= */

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
											$scope.createLabel = trashService
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
											var getLabels = trashService
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
											$scope.deleteLabel = trashService
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
											$scope.updateLabel = trashService
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
											$scope.addNote = trashService
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
											var getLabels = trashService
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
											$scope.updateNoteInLabel = trashService
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
						$scope.deleteLabel = trashService.deleteNoteLabel(label,
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
