<html>
<head>
	<link rel="stylesheet" href="bower_components/angular-material/angular-material.min.css"/>
	<!-- All css files are here -->
	
	<link rel="stylesheet" href="css/login.css"/>
	<link rel="stylesheet" href="css/home.css">
	<link rel="stylesheet" href="css/navbar.css">
	<link rel="stylesheet" href="css/sidebar.css">
</head>
<body ng-app="ToDo">
	<div ui-view ></div>
</body>
<script src = "bower_components/angular/angular.js"></script>
<script src = "bower_components/angular-animate/angular-animate.min.js"></script>
<script src = "bower_components/angular-aria/angular-aria.min.js"></script>
    
    <!-- <script src = "bower_components/angular-messages/angular-messages.min.js"></script> -->
    
    <script src="bower_components/angular-material/angular-material.min.js"></script> 
    <script src = "bower_components/angular-ui-router/release/angular-ui-router.js"></script>
      
    <!-- All script folders file is here -->
      
	<script type="text/javascript" src="script/app.js"></script>
	
	
	<!-- All Services are here -->
	
	<script type="text/javascript" src="service/loginService.js"></script>
	<script type="text/javascript" src="service/registrationService.js"></script>
	<script type="text/javascript" src="service/forgotpasswordService.js"></script>
	<script type="text/javascript" src="service/resetpasswordService.js"></script>
	<script type="text/javascript" src="service/homeService.js"></script>
	
	
	<!-- All Controllers are here -->
	
	<script type="text/javascript" src="controller/loginController.js"></script>
	<script type="text/javascript" src="controller/registrationController.js"></script>
	<script type="text/javascript" src="controller/forgotpasswordController.js"></script>
	<script type="text/javascript" src="controller/resetpasswordController.js"></script>
	<script type="text/javascript" src="controller/homeController.js"></script>
	
	
	<!-- All directives are here -->
	
	<script type="text/javascript" src="directive/navBar.js"></script>
	<script type="text/javascript" src="directive/sidebar.js"></script>
	
</html>