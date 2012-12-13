<ul class="nav nav-pills">
	<li id="paths"><a href="paths">Paths</a></li>
	<li id="checkpoints"><a href="checkpoints">Checkpoints</a></li>
	<li id="games"><a href="games">Games</a></li>
	<li id="teams"><a href="teams">Teams</a></li>
	<li id="players"> <a href="players">Players</a></li>
</ul>
	

<script>
	$(document).ready(function() {
		var path = window.location.pathname.split('/');
		var page = path[path.length-1];
		$('li').removeClass('active');
		$('li#' + page).addClass('active');
	});
</script>