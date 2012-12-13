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
<title>Huskies Hunt | Teams</title>
</head>
<body>
<%@ include file="/WEB-INF/partials/_header.jsp"%>
<%@ include file="/WEB-INF/partials/_nav.jsp"%>

<div class="alert alert-block alert-error fade in" style="display: none;">
   <button type="button" class="close" data-dismiss="alert">×</button>
   <h4 class="alert-heading">There was an error processing the data, please try again</h4>
 </div>
<table class="table table-condensed table-db">
 	<thead>
 		<tr>
 			<th>#</th>
 			<th>Name</th>
 			<th>Path ID</th>
 			<th>Game ID</th>
 			<th># of Players</th>
 			<th></th>
 			<th></th>
 		</tr>
 	</thead>
  	<tbody>
	  	<c:forEach var="t" items="${teams}">
	 	    <tr>
	 	    	<td>${t.id}</td>
	 			<td>${t.name}</td> 
				<td>
				<c:choose>
				<c:when test ="${t.pathId != 0}"><a href="paths?id=${t.pathId}">${t.pathId}</a></c:when>
				<c:when test ="${t.pathId == 0}">${t.pathId}</c:when>
				</c:choose>
				</td>
				<td>
				<c:choose>
				<c:when test ="${t.gameId != 0}"><a href="games?id=${t.gameId}">${t.gameId}</a></c:when>
				<c:when test ="${t.gameId == 0}">${t.gameId}</c:when>
				</c:choose>
				</td>
			<td> 
			<% int count = 0; %>
			<c:forEach var="p" items="${players}">
	        	<c:if test ="${p.teamId == t.id}"><% count++; %></c:if>
	      </c:forEach>
	      <%= count %>
	      </td>
			<td class="column-details"><a href="<c:url value="teams?id=${t.id}"/>" >View/Edit Details</a></td>
			<td class="column-details"><a href="#deleteModal" role="button" data-toggle="modal" class="red delete-btn" id="${t.id}">Delete</a></td>
	    	</tr>
		</c:forEach>
    </tbody>
</table>

<a href="add?team" class="btn btn-primary table-align">Add A Team</a>

<%@ include file="/WEB-INF/partials/_delete_modal.jsp"%>
  
<script>
$(document).ready(function() {
	var param = document.URL.split('?edit=')[1];
	if(param === "fail"){
		$('.alert-error').show();
	}
	
	$('.delete-btn').on("click", function(){
		var id = $(this).attr("id");
		$('a#delete_url').attr("href","teams?delete=" + id )
	});
});
</script>
</body>
</html>