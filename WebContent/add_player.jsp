<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/JavaScript" src="js/bootstrap.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="css/main.css" type="text/css" />
<title>Huskies Hunt | View/Edit Player</title>
</head>
<body>

<%@ include file="/WEB-INF/partials/_header.jsp"%>
<%@ include file="/WEB-INF/partials/_nav.jsp"%>

<div style="display: none;" class="alert alert-error"></div>

<div class="form-db">
	<form action="players" method="POST"  onsubmit="return validateForm()">
	 <fieldset>
	    <legend>New Player</legend>
	    <label>Username<span>*</span></label>
	    <input type="text" id="iun" name="playerInfo">
	     <span class="help-block">Username must be unique</span>
	     	    	    <label>Password<span>*</span></label>
	    <input type="text" id="ip" name="playerInfo">
	    	    <label>First Name<span>*</span></label>
	    <input type="text" id="ifn" name="playerInfo">
	    	    	    <label>Last Name<span>*</span></label>
	    <input type="text" id="iln" name="playerInfo">
	    <label>Team ID</label>
	   <select  name="playerInfo" >
	   	<option selected="selected" value="0">Not Assigned</option>
	    	<c:forEach var="t" items="${teams}">
	    		<option value="${t.id }">${t.id } ${t.name }</option>
	    	</c:forEach>
	    </select>
	      <br>
	    <button type="submit" class="btn submit btn-success">Save</button>
	  </fieldset>
	</form>
	<a href="players" class="btn btn-primary cancel">Cancel </a>
</div>
<script>
function validateForm() {
	var iun = $('input#iun').val();
	var ip = $('input#ip').val();
	var ifn = $('input#ifn').val();
	var iln = $('input#iln').val();
	if (iun == "" || ip == "" || ifn == "" || iln == ""){
		$('.alert').html("<strong>Fields with (*) can not be left blank</strong>");
		$('.alert').show();
		$('body').animate({ scrollTop: 0 }, 0);
		return false;
	}
}
$(document).ready(function(){
/* 	$('input').on("blur", function(){
		if($(this).val() == ""){
			$('.submit').attr('disabled', 'disabled');
			$('.alert').html("<strong>No fields can be blank</strong>");
			$('.alert').show();
		}else{
			$('.submit').removeAttr('disabled');
			$('.alert').hide();
		}
	}); */
});
</script>
</body>
</html>