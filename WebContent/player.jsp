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
	    <legend>Player #${player.id }</legend>
	    <input type="hidden" name="playerId"  value="${player.id }" >
	    <label>Username<span>*</span></label>
	    <input type="text" name="playerInfo" id="un" value="${player.username }">
	     <span class="help-block">Username must be unique</span>
	    	    <label>First Name<span>*</span></label>
	    <input type="text" name="playerInfo" id="fn" value="${player.firstName }">
	    	    	    <label>Last Name<span>*</span></label>
	    <input type="text" name="playerInfo" id="ln" value="${player.lastName }">
	    <c:choose>
			<c:when test ="${player.teamId != 0}"> <label><a href="#teamModal" role="button" data-toggle="modal">Team ID</a></label></c:when>
			<c:when test ="${player.teamId == 0}"><label>Team ID</label></c:when>
		</c:choose>
	     <select  name="playerInfo" >
	     <c:if test="${player.teamId == 0 }"><option selected="selected" value="0">Not Assigned</option></c:if>
	    	<c:forEach var="t" items="${teams}">
	    		<c:if test="${player.teamId == t.id }"><option selected="selected" value="${t.id }">${t.id } ${t.name }</option></c:if>
	    		<c:if test="${player.teamId != t.id }"><option value="${t.id }">${t.id } ${t.name }</option></c:if>
	    	</c:forEach>
	    </select>
	      <br>
	    <button type="submit" class="btn submit btn-success">Update</button>
	  </fieldset>
	</form>
	<a href="#deleteModal" role="button" data-toggle="modal" class="btn btn-danger">Delete</a>
	<a href="players" class="btn btn-primary cancel">Cancel </a>
</div>

<div id="teamModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	
		<h4><a href="teams?id=${team.id }">Team #${team.id }</a></h4>
		<strong>Name:</strong> ${team.name }<br>
		<strong>Path ID:</strong> ${team.pathId }<br>
		<strong>Game ID: </strong> ${team.gameId }<br>

	</div>
</div>

<%@ include file="/WEB-INF/partials/_delete_modal.jsp"%>

<script>
function validateForm() {
	var iun = $('input#un').val();
	var ifn = $('input#fn').val();
	var iln = $('input#ln').val();
	if (iun == "" || ifn == "" || iln == ""){
		$('.alert').html("<strong>Fields with (*) can not be left blank</strong>");
		$('.alert').show();
		$('body').animate({ scrollTop: 0 }, 0);
		return false;
	}
}
$(document).ready(function(){
	$('.btn-danger').on("click", function(){
		$('a#delete_url').attr("href","players?delete=${player.id}" )
	});
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