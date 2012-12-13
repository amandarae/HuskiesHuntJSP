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
<title>Huskies Hunt | Edit Game</title>
</head>
<body>

<%@ include file="/WEB-INF/partials/_header.jsp"%>
<%@ include file="/WEB-INF/partials/_nav.jsp"%>

<div style="display: none;" class="alert alert-error"></div>

<div class="form-db">
	<form action="games" method="POST" onsubmit="return validateForm()">
	 <fieldset>
	    <legend>Game #${game.id }</legend>
	    <input type="hidden" name="gameId" value="${game.id }" >
	 	   
	    <label>Name<span>*</span></label>
	    <input id="name" type="text" name="gameInfo" value="${game.name }">
	    <label>Rules</label>
	    <textarea name="gameInfo" rows="10" cols="10">${game.rules }</textarea>
	    <label>Start Date</label>
	    <input type="text" name="gameInfo" id="startDateTime" value="${game.startDate }">
	    <label>End Date</label>
	     <input type="text" name="gameInfo" id="endDateTime" value="${game.endDate }">
		<label>Game Type</label>
		<select name="gameInfo" >
		<c:if test="${game.type == '1' }"><option value="1" selected="selected">checkpoints in order</option><option value="2" >checkpoints in no order</option></c:if>
			<c:if test="${game.type == '2' }"><option value="1">checkpoints in order</option><option value="2" selected="selected">checkpoints in no order</option></c:if>
		</select>
	      <br>
	    <button type="submit" class="btn submit btn-success">Update</button>
	  </fieldset>
	</form>
	<a href="#deleteModal" role="button" data-toggle="modal" class="btn btn-danger">Delete</a>
	<a href="games"><button type="reset" class="btn btn-primary cancel">Cancel</button></a>

</div>

<%@ include file="/WEB-INF/partials/_delete_modal.jsp"%>

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
	$('.btn-danger').on("click", function(){
		$('a#delete_url').attr("href","games?delete=${game.id}" )
	});
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