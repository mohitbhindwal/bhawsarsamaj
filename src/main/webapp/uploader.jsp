  
   
    <link href="jancy/css/jasny-bootstrap.css" rel="stylesheet" media="screen">
    <link href="jancy/css/jasny-bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="js/upload.js" rel="stylesheet">
    
    <script>
        
        alert('sdsdsdfd');
        
        
         $("#uploadtoserver").click(function(event) {
       evt.preventDefault();
    alert("post.js uploadtoserver");  
    uploadtoserver(event);
});


alert('sdfsdfsdf');

function uploadtoserver(evt){
    
}
    </script>
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/ html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  
<div class="fileinput fileinput-new" data-provides="fileinput">
  <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;"></div>
  <div>
    <span class="btn btn-default btn-file">
        <span class="fileinput-new">Select image</span>
        <span class="fileinput-exists">Change</span>
        <input type="file" name="...">
    </span>
    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
    <a id ="uploadtoserver" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Upload</a>
  </div>
</div>
  
        
    <script src="jancy/js/jasny-bootstrap.min.js"></script>
    <script src="jancy/js/jasny-bootstrap.js"></script>
    <script src="js/upload.js"></script>
    
    
    
    <!--For Size image-->
    
    <!--div class="fileinput fileinput-new" data-provides="fileinput">
  <div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
    <img data-src="holder.js/100%x100%" alt="...">
  </div>
  <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;"></div>
  <div>
    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="..."></span>
    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
  </div>
</div-->