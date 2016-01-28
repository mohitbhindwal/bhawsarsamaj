
<html>
<head>
<meta charset="utf-8">
<title>example</title>
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="white">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<head>

<body>

<div class="container">

	<h2><a href="../">Bootstrap List Filter</a></h2>

	<h3>Dynamic Search Example</h3>

	<form role="form">
		<div class="form-group">
			<input class="form-control" id="searchinput" type="search" placeholder="Search City..." />
		</div>
		<div id="searchlist" class="list-group">
			
			<!-- FILLED DYNAMICALLY -->
		</div>
	</form>
</div>

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="js/bootstrap-list-filter.src.js"></script>
<script>

$('#searchlist').btsListFilter('#searchinput', {
	sourceTmpl: '<a class="list-group-item" href="http://osm.org/#map=12/{lat}/{lon}"><span>{title}</span></a>',
	sourceData: function(text, callback) {
		return $.getScript('test.jsp?q='+text, function(json) {
			callback(json);
		});
	}
});
</script>
<script src="/labs-common.js"></script>
</body>
</html>
