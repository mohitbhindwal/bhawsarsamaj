 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@page import="p1.SamajUtils" %>
 
 
<c:if test="${param.q != null}">

<% 
    String json = SamajUtils.getJSON(request.getParameter("q"),request.getContextPath()); 
    out.print(json);

%>


</c:if>

 
<c:if test="${param.q == null}">


<form role="form">
		<div class="form-group">
			<input class="form-control" id="searchinput" type="search" placeholder="Search Bhawsar..." />
		</div>
		<div id="searchlist" class="list-group">
 
			<!-- FILLED DYNAMICALLY -->
		</div>
	</form>
  
<script src="js/jquery-2.0.3.js"></script>
<script src="js/bootstrap-list-filter.min.js"></script>




<script>

$('#searchlist').btsListFilter('#searchinput', {
	sourceTmpl: '<a class="list-group-item" onclick="getbody(this,{id});" href="javascript:void(0);" ><span><img  style="width:50px;height:50px" src = "{title}"/></span><span><I><B>{name}<B><I></span></a>',
	sourceData: function(text, callback) {
             // alert(text);
              callback('test');
		return $.getJSON('search.jsp?q='+text, function(json) {
			//alert('test'+json);
                      callback(json);
		});
	}
});

function getbody(evt,pid){
  //  alert('userid is'+pid);
    try{
    $('#myModal').modal('hide');
    }catch(err){
        console.log('err'+err);
    }
 //   $('#loading-image').show();
//alert(evt+'   ' + pid);
         var dataString = 'userid='+pid;
   $.ajax({
        type: 'POST',
        url: 'getbody.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
            //alert('getbody success');
            $('#body').html(data);
        },
        complete: function(){
            
        
   //      $('#loading-image').hide();
      },
        error: function (request, error) {
            alert("Request: " + JSON.stringify(request));
        }
    });

}

</script>

<jsp:include page="pagination.jsp"/>



</c:if>

 
