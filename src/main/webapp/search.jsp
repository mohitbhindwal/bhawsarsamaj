<link href="css/search.css" rel="stylesheet">
<script>
$('#searchlist').btsListFilter('#searchinput', {itemChild: 'span'});
</script>
 
	<div class="row">
		<h2>Stylish Search Box</h2>
           <div id="custom-search-input">
                            <div class="input-group col-md-12">
                                <input type="text" class="search-query form-control" placeholder="Search" />
                                <span class="input-group-btn">
                                    <button class="btn btn-danger" type="button">
                                        <span class=" glyphicon glyphicon-search"></span>
                                    </button>
                                </span>
                            </div>
                        </div>
	</div>


 <h2><a href="../">Bootstrap List Filter</a></h2>

	<h3>Dynamic Search Example</h3>

	<form role="form">
		<div class="form-group">
			<input class="form-control" id="searchinput" type="search" placeholder="Search City..." />
		</div>
		<div id="searchlist" class="list-group">
			<a class="list-group-item" href="http://osm.org/#map=12/-29.11761/59.65834"><span>Avellaneda</span></a>
			<a class="list-group-item" href="http://osm.org/#map=12/-36.77698/59.85854"><span>Azul</span></a>
			<a class="list-group-item" href="http://osm.org/#map=12/40.70583/19.95222"><span>Berat</span></a>
			<a class="list-group-item" href="http://osm.org/#map=12/41.61028/20.00889"><span>Burrel</span></a>
			<a class="list-group-item" href="http://osm.org/#map=12/-5.55/12.2"><span>Cabinda</span></a>
			<a class="list-group-item" href="http://osm.org/#map=12/-13.78333/14.68333"><span>Caluquembe</span></a>
			<!-- FILLED DYNAMICALLY -->
		</div>
	</form>
        
        
  
<script src="js/jquery-2.0.3.js"></script>
<script src="js/bootstrap-list-filter.min.js"></script>

<script>

$('#searchlist').btsListFilter('#searchinput', {
	sourceTmpl: '<a class="list-group-item" href="http://osm.org/#map=12/{lat}/{lon}"><span>{title}</span></a>',
	sourceData: function(text, callback) {
              alert(text);   
              callback('test');
		return $.getJSON('test.jsp?q='+text, function(json) {
			alert('test');
                      callback(json);
		});
	}
});
</script>