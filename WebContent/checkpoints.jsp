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
<title>Huskies Hunt | Checkpoints</title>
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
 			<th>Longitude</th>
 			<th>Latitude</th>
 			<th>Path ID</th>
 			<th>Order in Path</th>
 			<th></th>
 			<th></th>
 		</tr>
 	</thead>
  	<tbody>
	  	<c:forEach var="c" items="${checkpoints}">
	 	    <tr>
	 	    	<td>${c.id}</td>
	 	    	<td>${c.name }</td>
	 	    	<td>${c.longitude }
	 			<td>${c.latitude}</td> 
				<td>
					<c:choose>
						<c:when test ="${c.pathId != 0}"><a href="paths?id=${c.pathId}">${c.pathId}</a></c:when>
						<c:when test ="${c.pathId == 0}">${c.pathId}</c:when>
					</c:choose>
				</td>
				<td>${c.pointOrder }</td>
			<td class="column-details"><a href="<c:url value="checkpoints?id=${c.id}"/>">View/Edit Details</a></td>
			<td class="column-details"><a href="#deleteModal" role="button" data-toggle="modal" class="red delete-btn" id="${c.id}">Delete</a></td>
			
	    	</tr>
		</c:forEach>
    </tbody>
</table>

<a href="add?checkpoint"><button type="button" class="btn btn-primary table-align">Add A Checkpoint</button></a>


  <%@ include file="/WEB-INF/partials/_delete_modal.jsp"%>
  
<script>
$(document).ready(function() {
	var param = document.URL.split('?edit=')[1];
	if(param === "fail"){
		$('.alert-error').show();
	}
	
	$('.delete-btn').on("click", function(){
		var id = $(this).attr("id");
		$('a#delete_url').attr("href","checkpoints?delete=" + id )
	});
});
</script>
</body>
</html>