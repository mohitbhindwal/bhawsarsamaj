<link href="css/search.css" rel="stylesheet">
 
 
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
 
			<!-- FILLED DYNAMICALLY -->
		</div>
	</form>
        
        
  
<script src="js/jquery-2.0.3.js"></script>
<script src="js/bootstrap-list-filter.min.js"></script>

<script>

$('#searchlist').btsListFilter('#searchinput', {
	sourceTmpl: '<a class="list-group-item" href="http://localhost:7070/bhawsarsamaj?pid=12/{id}"><span><img  style="width:50px;height:50px" src = "{title}"/></span><span><I><B>{name}<B><I></span></a>',
	sourceData: function(text, callback) {
              alert(text);   
              callback('test');
		return $.getJSON('test.jsp?q='+text, function(json) {
			alert('test'+json);
                      callback(json);
		});
	}
});
</script>