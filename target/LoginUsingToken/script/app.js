var app = angular.module("ToDo", [ 'ui.router', 'ngMaterial']);

app.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {
			$stateProvider
			.state('login', {
				url : '/login',
				//template: 'login',
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
				//controller : 'homeController'
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
			$urlRouterProvider.otherwise('/login');
		} ]);