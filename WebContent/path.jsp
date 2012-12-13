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
<title>Huskies Hunt | View/Edit Path</title>
</head>
<body>

<%@ include file="/WEB-INF/partials/_header.jsp"%>
<%@ include file="/WEB-INF/partials/_nav.jsp"%>

<div style="display: none;" class="alert alert-error"></div>

<div class="form-db">
	<form action="paths" method="POST" onsubmit="return validateForm()">
	 <fieldset>
	    <legend>Path #${path.id }</legend>
	    <input type="hidden" name="pathId" value="${path.id }" >
	    <label>Name<span>*</span></label>
	    <input type="text" name="pathInfo" value="${path.name }">
	    	
	      <br>
	    <button type="submit" class="btn submit btn-success btn-success">Update</button>
	  </fieldset>
	</form>
	<a href="#deleteModal" role="button" data-toggle="modal" class="btn btn-danger">Delete</a>
	

		    <label style="margin: 20px 0 0">Checkpoints</label>
	    	    <c:choose>
	    	<c:when test="${empty checkpoints }">
	    		<p><strong>No checkpoints have been assigned to this path</strong></p>
	    		</c:when>
	    		<c:when test="${ not empty checkpoints }">
	    <table class="table table-bordered" style="width: 50%">
			<tr >
				<th style="width: 20px;">Order</th>
				<th>Name</th>
			</tr>
	    	<c:forEach var="c" items="${checkpoints}">
	    	<tr rowspan="2">
		    	<td style="line-height: 75px;">
		    		${c.pointOrder }
		    	</td>
		    	<td style="line-height: 75px;">
		    		<a href="checkpoints?id=${c.id}">${c.name }</a>
		    	</td>
			<td style="width: 90px;">	<form action="checkpoints" method="post" class="orderForm" style="margin: 0; padding: 0;"><input type="hidden" name="checkpointInfo" class="newOrder" value="${c.pointOrder }"> 
			<input type="hidden" name="pathId" value="${path.id }"> 
			<input type="hidden" name="listIndex" value="${c.pointOrder}"> 
			<input type="hidden" name="orderMovement" value="up"> 
			<input type="hidden" name="checkpointId" value="${c.id }"> 
			<button type="submit" class="up" style="width: 90px">Move Up</button></form>
			<br>
			<form action="checkpoints" method="post" class="orderForm" style="margin: 0; padding: 0;"><input type="hidden" name="checkpointInfo" class="newOrder" value="${c.pointOrder }"> 
			<input type="hidden" name="pathId" value="${path.id }"> 
			<input type="hidden" name="listIndex" value="${c.pointOrder}"> 
			<input type="hidden" name="orderMovement" value="down"> 
			<input type="hidden" name="checkpointId" value="${c.id }"> 
			<button type="submit" class="down" id="${c.pointOrder}">Move Down</button></form></td>
	    	</tr>
	    	</c:forEach>

	    </table>
	    </c:when>
	    </c:choose>
	<a href="paths" class="btn btn-primary cancel">Cancel </a>
</div>

<%@ include file="/WEB-INF/partials/_delete_modal.jsp"%>
<script>
function validateForm() {
	var input = $('input').val();
	if (input == ""){
		$('.alert').html("<strong>Fields with (*) can not be left blank</strong>");
		$('.alert').show();
		$('body').animate({ scrollTop: 0 }, 0);
		return false;
	}
}
$(document).ready(function(){
	$('.btn-danger').on("click", function(){
		$('a#delete_url').attr("href","paths?delete=${path.id}" )
	});
	$('.up').first().hide();
	var down = $('.down');
	$('#'+ down.length).hide();
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