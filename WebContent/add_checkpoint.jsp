<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/JavaScript" src="js/bootstrap.js"></script>
<script src="//maps.googleapis.com/maps/api/js?sensor=false"></script>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="css/main.css" type="text/css" />
<title>Huskies Hunt | Add A Checkpoint</title>
<style>
#map_canvas {width:90%; height:300px; border:5px solid #DEEBF2; margin: 0 auto 10px;}
#placeModal {padding: 20px;}
#placeSave {float: right; margin-right: 25px;}
#addressFind {margin: -10px 0 0 10px;}
.tooltip { z-index: 2000 !important; }
</style>
</head>
<body>

<%@ include file="/WEB-INF/partials/_header.jsp"%>
<%@ include file="/WEB-INF/partials/_nav.jsp"%>

<div style="display: none;" class="alert alert-error"></div>

<div class="form-db">
	<form action="checkpoints" method="POST" onsubmit="return validateForm()">
	 <fieldset>
	    <legend>New Checkpoint </legend>
	 	<label>Name<span>*</span></label>
	    <input id="name" type="text" name="checkpointInfo" >
	    <label>Longitude<span>*</span></label>
	    <input id="longitude" type="text" name="checkpointInfo">
	    <label>Latitude<span>*</span></label>
	    <input id="latitude" type="text" name="checkpointInfo">
	     <label>Path ID</label>
	     <span class="help-block">The path this checkpoint belongs to</span>
	     <select  name="checkpointInfo" >
	    	<c:forEach var="p" items="${paths}">
	    		<option value="${p.id }">${p.id } ${p.name }</option>
	    	</c:forEach>
	    </select>
	    <label>Code</label>
	    <input type="text" name="checkpointInfo">
	     <span class="help-block">The code a player must enter to receive the challenge</span>
	    <label>Challenge</label>
	    <textarea  name="checkpointInfo" rows=10 cols=10></textarea>
  		<label>Challenge Answer</label>
	    <textarea  name="checkpointInfo" rows=10 cols=10></textarea>
	    <label>Clue</label>
	    <textarea  name="checkpointInfo" rows=10 cols=10></textarea>
	      <br>
	    <button type="submit" class="btn submit btn-success">Save</button>
	  </fieldset>
	</form>
	
	<a href="checkpoints"  class="btn btn-primary cancel">Cancel </a>
	
	<div id="placeModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	<button type="button" class="close placeCancel" data-dismiss="modal" aria-hidden="true">×</button>
		<input type="text" id="address"  title="Type in street address only. City/State are added for your convenience." data-placement="bottom"/><a href="#" id="addressFind" class="btn btn-primary">Find Address</a>
	</div>
	
	<div id="map_canvas"></div>
	    <a href="#" data-dismiss="modal" id="placeSave" class="btn btn-primary">Save</a> 
	</div>
</div>

</div>
<script>
function validateForm() {
	var iname = $('input#name').val();
	var ilat = $('input#latitude').val();
	var ilong = $('input#longitude').val();
	if (iname == "" || ilat == "" || ilong == ""){
		$('.alert').html("<strong>Fields with (*) can not be left blank</strong>");
		$('.alert').show();
		$('body').animate({ scrollTop: 0 }, 0);
		return false;
	}
}
$(document).ready(function(){
	 $('#address').tooltip();
	/* $('input#name, input#latitude, input#longitude').on("blur", function(){
		if($(this).val() == ""){
			$('.submit').attr('disabled', 'disabled');
			$('.alert').html("<strong>Fields with (*) can not be left blank</strong>");
			$('.alert').show();
		}else{
			$('.submit').removeAttr('disabled');
			$('.alert').hide();
		}
	}); */
	$('input#latitude, input#longitude').on("click", function(){
		$('#placeModal').modal();
		$('#placeModal').on("shown", function(){
			
			initialize();
		});
	});
	$('.placeCancel').on("click", function(){
		$('input#latitude, input#longitude').val("type in street address");
	});
});
function initialize() {
	var lat; var lng;
	$('#latitude').val() == "" ? lat = 43.676811329698296 : lat = $('#latitude').val();
	$('#longitude').val() == "" ? lng = -79.41029763234837 : lng = $('#longitude').val();
	var marker;
		
	var map = new google.maps.Map(
	        document.getElementById('map_canvas'), {
	          center: new google.maps.LatLng(lat,lng),
	          zoom: 13,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	      });
	marker = new google.maps.Marker({
	    position: new google.maps.LatLng(lat,lng),
	    map: map,
	    title: "(" + lat + ", " + lng + ")"
	});
	document.getElementById('latitude').value = lat;
	document.getElementById('longitude').value = lng;

	$('#addressFind').on("click", function(){
	 geocoder = new google.maps.Geocoder();

	    var address = document.getElementById("address").value + ", Toronto ON";

	    geocoder.geocode( { 'address': address}, function(results, status) {
	      if (status == google.maps.GeocoderStatus.OK) {
	        map.setCenter(results[0].geometry.location);
	        marker.setPosition(results[0].geometry.location);
	        marker.setTitle("(" + results[0].geometry.location.lat() + ", " + results[0].geometry.location.lng() + ")");
	    	document.getElementById('latitude').value = results[0].geometry.location.lat();
	    	document.getElementById('longitude').value = results[0].geometry.location.lng();
	      } else {
	        alert("Geocode was not successful for the following reason: " + status);
	      }
	    });
	  });
	  
google.maps.event.addListener(map,'click',function(event)
{
		marker.setPosition(event.latLng);
		marker.setTitle(event.latLng + "");

		  google.maps.event.addListener(map, 'center_changed', function() {
		    window.setTimeout(function() {
		    	map.panTo(marker.getPosition());
		    }, 3000);
		  });

		  google.maps.event.addListener(marker, 'click', function() {
			 map.setZoom(16);
			  map.setCenter(marker.getPosition());
		  });

	document.getElementById('latitude').value = event.latLng.lat();
	document.getElementById('longitude').value = event.latLng.lng();
});
}
</script>
</body>
</html>