var app = angular.module("ToDo", ['ui.router','ngMaterial','ngFileUpload','base64','ngMaterialDatePicker','tb-color-picker']);

app.config([ '$stateProvider', '$urlRouterProvider',
	function($stateProvider, $urlRouterProvider) {
	$stateProvider
	.state('login', {
		url : '/login',
		templateUrl : 'template/login.html',
		controller : 'loginController'
	})
	.state('registration', {
		url : '/registration',
		templateUrl : 'template/registration.html',
		controller : 'registrationController'
	})

	.state('home', {
		url : '/home',
		templateUrl : 'template/home.html',
		controller : 'homeController'
	})

	.state('forgotpassword', {
		url : '/forgotpassword',
		templateUrl : 'template/forgotpassword.html',
		controller : 'forgotpasswordController'
	})

	.state('resetpassword',{
		url : '/resetpassword',
		templateUrl : 'template/resetpassword.html',
		controller : 'resetpasswordController'
	})

	.state('trash',{
		url : '/trash',
		templateUrl : 'template/trash.html',
		controller : 'homeController'
	})

	.state('archive',{
		url : '/archive',
		templateUrl : 'template/archive.html',
		controller : 'homeController'
	})
	
	
	
	.state('dummy',{
		url : '/dummy',
		templateUrl : 'template/dummy.html',
		controller : 'dummyController'
	})
	
	.state('search',{
		url : '/search',
		templateUrl : 'template/search.html',
		controller : 'homeController'
	})
	
	$urlRouterProvider.otherwise('/login');
} ]);