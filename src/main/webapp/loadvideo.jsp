
<%
String src = request.getParameter("src");
if(src!=null){
System.out.print("-------> Before Replace "+src);
//src = src.replace("watch?v=", "v/");
//src = src + "?html5=1";
src = src.replace("watch?v=", "embed/") ;
//src = src +"&output=embed";
System.out.print("-------> After Replace "+src);
%>  

<!--div  class="embed-responsive embed-responsive-16by9">
    <iframe class="embed-responsive-item"  src="https://www.youtube.com/embed/HmphBV0GyIY" allowfullscreen>
    </iframe>
</div-->


<style type="text/css">
    .bs-example{
    	margin: 20px;
    }
    .modal-content iframe{
        margin: 0 auto;
        display: block;
    }
</style>
<script type="text/javascript">
$(document).ready(function(){
    /* Get iframe src attribute value i.e. YouTube video url
    and store it in a variable */
  //  var url = $("#cartoonVideo").attr('src');
  ////  alert('HHHiiii');
    /* Assign empty url value to the iframe src attribute when
    modal hide, which stop the video playing */
    $("#myVModal").on('hide.bs.modal', function(){
        $("#myVideo").attr('src', '');
    });
    
    /* Assign the initially stored url back to the iframe src   
    attribute when modal is displayed again */
//    $("#myVModal").on('show.bs.modal', function(){
 //       $("#myVideo").attr('src', url);
 //   });
});
</script>
   <!-- Modal HTML -->
    <div id="myVModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h6 class="modal-title">Share Video</h6>
                       <div class="widget-area no-padding blank">
                            <div class="status-upload">
                                <form onsubmit="return shareVideo(this,${sessionScope.user.id},'<%=src%>');">
                                    <textarea id="postvideodata" style="border-width: 1px;border-color: #337AB7;height: 100px"></textarea>
                                <!--iframe id="cartoonVideo" width="560" height="315" src="<%=src%>" frameborder="0" allowfullscreen></iframe-->
                                    <button name="post" id="postvideo" class="btn btn-primary pull-left" style="margin-left: 5px"><i class="fa fa-share"></i> Share</button>
                                </form>
                            </div>
                        </div>
                </div>
                <div class="modal-body">
                    
                    <div  class="embed-responsive embed-responsive-16by9">
                        <iframe id="myVideo" class="embed-responsive-item"  src="<%=src%>" allowfullscreen>
                        </iframe>
 
                    </div>
                </div>
            </div>
        </div>
    </div>

<%}%>