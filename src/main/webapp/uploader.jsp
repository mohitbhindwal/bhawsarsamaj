  
   
    <link href="jancy/css/jasny-bootstrap.css" rel="stylesheet" media="screen">
    <link href="jancy/css/jasny-bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="js/upload.js" rel="stylesheet">
    
    <script>
       
        
   function callback(response)
    {
         postdata(evt,response);
          // alert('callback' +response); //response data from ajax call
    }

function ShowContent(jud,callback){
    
    var url = 'imageuploader.jsp';
    var form = $("#pimage");
    var data = new FormData(form[0]);

    $.ajax({
        type        : 'post',
        dataType    : 'html',
        url         : url,
         async: true, 
        data        : data,
        enctype     : "multipart/form-data",
        cache       : false,
        contentType : false,

        processData : false,
        success     : function(data) {
            alert('['+data+']');
            callback(data.trim());
           // return data.trim() ; 
            
          //  $('#applyPop').css('display', 'none');
        },
        complete : function(data) {

        },
        error : function(data, status, error) {
            alert('Fail! :<');
        
           
                removeElement('pimage');
        }
    });
}

    function removeElement(elementId) {
        // Removes an element from the document
        var element = document.getElementById(elementId);
        element.parentNode.removeChild(element);
    }
 
 
    </script>
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/ html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  
    <form action="imageuploader.jsp" id="pimage" method="post"  enctype="multipart/form-data">
        <div class="fileinput fileinput-new" data-provides="fileinput">
            <div id="imagepic" class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;"></div>
            <div>
                    <span class="btn btn-default btn-file">
                    <span class="fileinput-new">Select image</span>
                    <span class="fileinput-exists">Change</span>
                    <input id="file" type="file" name="file" />
                </span>
                <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                <a onclick='ShowContent()' class="btn btn-default fileinput-exists" data-dismiss="fileinput">Upload</a>
            </div>
        </div>
    </form>
    
        
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
    
    