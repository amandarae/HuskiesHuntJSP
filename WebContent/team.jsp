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
<title>Huskies Hunt | Edit Team</title>
</head>
<body>

<%@ include file="/WEB-INF/partials/_header.jsp"%>
<%@ include file="/WEB-INF/partials/_nav.jsp"%>

<div style="display: none;" class="alert alert-error"></div>

<div class="form-db">
	<form action="teams" method="POST" onsubmit="return validateForm()">
	 <fieldset>
	    <legend>Team #${team.id }</legend>
	    <input type="hidden" name="teamId" value="${team.id }" >
	    	   
	    <label>Name<span>*</span></label>
	    <input type="text" id="namei" name="teamInfo" value="${team.name }">
	     <c:choose>
			<c:when test ="${team.pathId!= 0}"> <label><a href="#pathModal" role="button" data-toggle="modal">Path ID</a></label></c:when>
			<c:when test ="${team.pathId == 0}"><label>Path ID</label></c:when>
		</c:choose>
	     <select  name="teamInfo" >
	     <c:if test="${team.pathId == 0 }"><option selected="selected" value="0">Not Assigned</option></c:if>
	    	<c:forEach var="p" items="${paths}">
	    		<c:if test="${team.pathId == p.id }"><option selected="selected" value="${p.id }">${p.id } ${p.name }</option></c:if>
	    		<c:if test="${team.pathId != p.id }"><option value="${p.id }">${p.id } ${p.name }</option></c:if>
	    	</c:forEach>
	    </select>
	    <c:choose>
			<c:when test ="${team.gameId!= 0}"> <label><a href="#gameModal" role="button" data-toggle="modal">Game ID</a></label></c:when>
			<c:when test ="${team.gameId == 0}"><label>Game ID</label></c:when>
		</c:choose>
	    <select  name="teamInfo" >
	     <c:if test="${team.gameId == 0 }"><option selected="selected" value="0">Not Assigned</option></c:if>
	    	<c:forEach var="g" items="${games}">
	    		<c:if test="${team.gameId == team.gameId }"><option selected="selected" value="${g.id }">${g.id } ${g.name }</option></c:if>
	    		<c:if test="${team.gameId != team.gameId }"><option value="${g.id }">${g.id } ${g.name }</option></c:if>
	    	</c:forEach>
	    </select>
	      <br>
	    <button type="submit" class="btn submit btn-success">Update</button>
	  </fieldset>
	</form>
	<a href="#deleteModal" role="button" data-toggle="modal" class="btn btn-danger">Delete</a>
	<a href="teams" class="btn btn-primary cancel">Cancel</a>
	
	 <label style="margin: 20px 0 20px">Players</label>
	 <% int count = 0;  %>
			<c:forEach var="p" items="${players}">
	        	<c:if test ="${p.teamId == team.id}"><% count++; %></c:if>
	      </c:forEach>
	    	<% if(count == 0){ %>
	    		<p><strong>No players have been assigned to this team</strong></p>
			<% }else if(count > 0) {%>
		    	<% if(count < 2 ){ %>
		    		<p><span style="color: red;">*There are less than 2 players on this team, please add players.</span></p>
				<%} %>
	    <table class="table table-bordered" style="width: 50%">
			<tr >
				<th style="width: 20px;">ID</th>
				<th>Username</th>
				<th>Name</th>
			</tr>
			<c:forEach var="p" items="${players}">
	        	<c:if test ="${p.teamId == team.id}">
	        		<tr >
				    	<td >${p.id }</td>
				    	<td ><a href="players?id=${p.id}">${p.username }</a></td>
				    	<td>${p.firstName } ${p.lastName }</td>
				    </tr>
	        	</c:if>
	      </c:forEach>
	    </table>
	    	<%} %>
	<a href="teams" class="btn btn-primary cancel">Go Back</a>
	<a href="#" class="btn btn-success cancel" id="add_players_btn">Add/Remove Players</a>

	<div id="add_players" style="display: none;"><form action="players" method="POST">
	<table class="table table-bordered">
	<c:forEach var="p" items="${players}">
	        	
	        		<tr >
				    	<td >${p.id }</td>
				    	<td ><a href="players?id=${p.id}">${p.username }</a></td>
				    	<td>
				    	
				    	<input type="hidden" name="playerId" value="${p.id }">
				    	<input type="hidden" name="playerUsername" value="${p.username }">
				    	<input type="hidden" name="playerFName" value="${p.firstName }">
				    	<input type="hidden" name="playerLName" value="${p.lastName }">
				    	<input type="hidden" name="teamId" value="${team.id }">
				    		<c:if test ="${p.teamId == team.id}"><input type="checkbox" name="playerCheck" class="playerCheck" checked="checked" value=1><input type="hidden" name="checked" class="checked" value=1></c:if>
				    		<c:if test ="${p.teamId != team.id}"><input type="checkbox" name="playerCheck " class="playerCheck" value=0><input type="hidden" name="checked" class="checked" value=0></c:if>
				    		
				    	</td>
				    </tr>
	        	
	      </c:forEach>
	  </table><button type="submit" class="btn btn-success cancel">Submit Changes</button></form>
	</div>
	
	 <label style="margin: 20px 0 20px">Team Progression</label>
	    <c:choose>
	    	<c:when test="${ empty progression }">
	    		<p><strong>No progression has been made by this team. Perhaps their <a href="games?id=${team.gameId }">game</a> has not started yet.</strong></p>
	    	</c:when>
	    	<c:when test="${not empty progression }">
	    	<%int i = 0; %>
	    <table class="table table-bordered" >
			<tr >
				<th style="width: 30px;">Checkpoint ID</th>
				<th>Completed</th>
				<th>Start Date</th>
			</tr>
	    	<c:forEach var="p" items="${progression}">
	    	<tr >
		    	<td >
		    		<a href="#checkpointModal" class="checkpointModal" id="${p.checkpointId }" role="button" data-toggle="modal">${p.checkpointId }</a>
		    	</td>
		    	<td >
		    		<c:choose>
		    			<c:when test="${p.completed  == true}">Yes</c:when>
		    			<c:when test="${p.completed == false}" >No</c:when>
		    		</c:choose>
		    	</td>
		    	<td>
		    		${p.startDate }
		    	</td>
	</tr></c:forEach></table>
	</c:when>
	</c:choose>
	
<div id="pathModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h4><a href="paths?id=${path.id }">Path #${path.id }</a></h4>
		<strong>Name:</strong> ${path.name }<br>
		<strong><em>to see a full list of checkpoints in this path, please click on the id above.</em></strong><br>
	</div>
</div>

<div id="gameModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h4><a href="games?id=${game.id }">Game #${game.id }</a></h4>
		<strong>Name:</strong> ${game.name }<br>
		<strong>Start Date:</strong> ${game.startDate }<br>
		<strong>End Date: </strong> ${game.endDate }<br>
		<strong>Rules (HTML): </strong> ${game.rules }<br>
	</div>
</div>

<c:if test="${not empty checkpoints }">
	<div id="checkpointModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<c:forEach var="c" items="${checkpoints }">
			<div id="check${c.id }" style="display: none;">
				<h4><a href="checkpoints?id=${c.id }">Checkpoint #${c.id }</a></h4>
				<strong>Name:</strong> ${c.name }<br>
				<strong>Latitude:</strong> ${c.latitude }<br>
				<strong>Longitude: </strong> ${c.longitude }<br>
				<strong>Order in Path: </strong> ${c.pointOrder }
			</div>
		</c:forEach>
		</div>
	</div>
	<a href="teams" class="btn btn-primary cancel">Go Back</a>
</c:if>

<%@ include file="/WEB-INF/partials/_delete_modal.jsp"%>
</div>
<script>
function validateForm() {
	var input = $('input#namei').val();
	if (input == ""){
		$('.alert').html("<strong>Fields with (*) can not be left blank</strong>");
		$('.alert').show();
		$('body').animate({ scrollTop: 0 }, 0);
		return false;
	}
}
$(document).ready(function(){
	$('.btn-danger').on("click", function(){
		$('a#delete_url').attr("href","teams?delete=${team.id}" )
	});
	$('.checkpointModal').on("click", function(){
		var viewId = $(this).attr("id");
		$('#check'+viewId).show();
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
	$('#add_players_btn').on("click", function(){
		$('#add_players').toggle();
	});
	$('.playerCheck').on("click", function(){
		if($(this).attr("value") == 0){
			$(this).attr("value","1");
			$(this).next().attr("value","1");
		}else{
			$(this).attr("value","0");
			$(this).next().attr("value","0");
		}
	});
})
</script>
</body>
</html>