<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="bower_components/angular-material/angular-material.min.css"/>
	<!-- All css files are here -->
	
	<link rel="stylesheet" href="css/login.css"/>
	<link rel="stylesheet" href="css/home.css">
	<link rel="stylesheet" href="css/navbar.css">
	<link rel="stylesheet" href="css/search.css">
	<link rel="stylesheet" href="css/sidebar.css">
	<link rel="stylesheet" href=""/>
	<link rel="stylesheet" href="bower_components/bower_components/angular-material-datetimepicker/css/material-datetimepicker.css"/>
	<link rel="stylesheet" href="bower_components/bower_components/angular-material-datetimepicker/dist/material-datetimepicker.min.css">
	<link rel="stylesheet" href="bower_components/colorpicker/colorPickerStyle.css">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  	<!-- <link rel="stylesheet" href="http://resources/demos/style.css"> -->
	<link rel="stylesheet" href="">

</head>
<body ng-app="ToDo">
	<div ui-view ></div>
</body>

<script language="JavaScript"  src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script>
<script src = "bower_components/angular/angular.js"></script>
<script src = "bower_components/bower_components/angular-material-datetimepicker/dist/angular-material-datetimepicker.min.js"></script>
<script src = "bower_components/bower_components/angular-material-datetimepicker/dist/angular-material-datetimepicker.min.js.map"></script>
<script src="bower_components/bower_components/angular-material-datetimepicker/js/angular-material-datetimepicker.js"></script>
<script src = "bower_components/angular-animate/angular-animate.min.js"></script>
<script src = "bower_components/angular-aria/angular-aria.min.js"></script>
<script src="bower_components/ng-file-upload/ng-file-upload.min.js"></script>
<script src="bower_components/bower_components/moment/min/moment.min.js"></script>
<script src="bower_components/bower_components/angular-dragdrop/src/angular-dragdrop.min.js"></script>
<script src="bower_components/colorpicker/colorPicker.js"></script>

<!-- <script src="bower_components/bower_components/angular-dragdrop/src/angular-dragdrop.js"></script>
<script src="bower_components/angular/angular.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script> -->

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</script>
    
    <script src="bower_components/angular-base64/angular-base64.js"></script>
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
	<script type="text/javascript" src="service/trashService.js"></script>
	<script type="text/javascript" src="service/dummyService.js"></script>
	<script type="text/javascript" src="service/archiveService.js"></script>
	
	<!-- All Controllers are here -->
	
	<script type="text/javascript" src="controller/loginController.js"></script>
	<script type="text/javascript" src="controller/registrationController.js"></script>
	<script type="text/javascript" src="controller/forgotpasswordController.js"></script>
	<script type="text/javascript" src="controller/resetpasswordController.js"></script>
	<script type="text/javascript" src="controller/homeController.js"></script>
	<script type="text/javascript" src="controller/trashController.js"></script>
	<script type="text/javascript" src="controller/dailogController.js"></script>
	<script type="text/javascript" src="controller/dummyController.js"></script>
	<script type="text/javascript" src="controller/archiveController.js"></script>
	
	<!-- All directives are here -->
	
	<script type="text/javascript" src="directive/navBar.js"></script>
	<script type="text/javascript" src="directive/sidebar.js"></script>
	<script type="text/javascript" src="directive/noteCard.js"></script>
	<script type="text/javascript" src="directive/noteForTrash.js"></script>
	<script type="text/javascript" src="directive/customfilter.js"></script>
	
</html>
