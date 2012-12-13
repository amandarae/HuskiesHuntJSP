 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script type="text/JavaScript" src="js/bootstrap.js"></script>
	<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
	<link rel="stylesheet" href="css/main.css" type="text/css" />
<title>Huskies Hunt | Login</title>
</head>
<body>

<%@ include file="/WEB-INF/partials/_header.jsp"%>

  <form action="login" method="POST">
	  	<table class="modal-body">
	  	<tr><td>
		    <label>Username:</label>
		    </td><td>
		    <input type="text" name="username">	</td>
		</tr>
		<tr><td>
			<label>Password:</label></td><td>
			<input type="password" name="password">
			</td>
			</tr>
	  	</table>
	  	
	  	<div style="display: none;" class="alert alert-error">${error}</div>
	  	<div class="modal-footer">
		    <input type="submit" class="btn btn-primary" value="Submit">
	     </div>
	  </form>
	 
<script>
$(document).ready(function(){
	$('.logout').hide();
	var path = location.search;
	if(path === '?login=fail'){
		$('.alert').html("<strong>Session timed out. Please log in.</strong>");
	}else if(path == '?logout'){
		$('.alert').html("<strong>You have been logged out.</strong>");
	}
	if($('.alert').html() != "")
		$('.alert').show();
});
</script>
</body>
</html>