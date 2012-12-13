<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/JavaScript" src="js/bootstrap.js"></script>
<script type="text/JavaScript" src="js/jquery-ui.min.js"></script>
<script type="text/JavaScript" src="js/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="css/main.css" type="text/css" />
<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" />
<title>Huskies Hunt | Add A Game</title>
</head>
<body>

<%@ include file="/WEB-INF/partials/_header.jsp"%>
<%@ include file="/WEB-INF/partials/_nav.jsp"%>

<div style="display: none;" class="alert alert-error"></div>

<div class="form-db">
	<form action="games" method="POST" onsubmit="return validateForm()">
	 <fieldset>
	    <legend>New Game</legend>
	    <label>Name<span>*</span></label>
	    <input type="text" name="gameInfo" id="name">
	    <label>Rules</label>
	    <textarea name="gameInfo" rows="10" cols="10"></textarea>
	    <label>Start Date</label>
	    <input type="text" name="gameInfo" id="startDateTime">
	    <label>End Date</label>
	     <input type="text" name="gameInfo" id="endDateTime" >
		<label>Game Type</label>
		<select name="gameInfo">
			<option  value="1">checkpoints in order</option>
			<option  value="2">checkpoints in no order</option>
		</select>
	      <br>
	    <button type="submit" class="btn submit btn-success">Save</button>
	  </fieldset>
	</form>
	
	<a href="games" class="btn btn-primary cancel">Cancel </a>

</div>
<script>
function validateForm() {
	var input = $('input#name').val();
	if (input == ""){
		$('.alert').html("<strong>Fields with (*) can not be left blank</strong>");
		$('.alert').show();
		$('body').animate({ scrollTop: 0 }, 0);
		return false;
	}
}
$(document).ready(function(){
/* 	$('#name').on("blur", function(){
		if($(this).val() == ""){
			$('.submit').attr('disabled', 'disabled');
			$('.alert').html("<strong>Name can not be blank</strong>");
			$('.alert').show();
		}else{
			$('.submit').removeAttr('disabled');
			$('.alert').hide();
		}
	}); */
	$('#startDateTime, #endDateTime').datetimepicker();
});

</script>
</body>
</html>