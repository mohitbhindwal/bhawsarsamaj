/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    $("[data-toggle=tooltip]").tooltip();
});

  
 $("#upload").click(function(event) {
  //  alert("uploadImage() of post.js  will call the uploader.jsp");  
    uploadImage(event);
});
 

function uploadImage(evt) {
    var dataString = 'data=' + $('#postdata').val();
    if (evt !== null)
        evt.preventDefault();
    $.ajax({
        type: 'POST',
        url: 'uploader.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
            alert(data);

            $('#loadimage').append(data);
            //    setTimeout("postdata(null)",1000000);
        },
        error: function (request, error) {
            alert("Request: " + JSON.stringify(request));
        }
    });
}
 
        
            
            
 
 
 
    
  
   
   $("#post").click(function(evt){
         postdata(evt,null);
});
   
   function postdata(evt,imageid){
    //   alert('postdata() of post.js is called when share button is clicked');
        
      // if image is to be uploaded
      
        if(evt!==null)
             evt.preventDefault();
        
       if (!$('#loadimage').is(':empty')){
        imageid = ShowContent(evt);
         return true;
         }
        
       //  alert('return call imageid '+imageid);
       
             var dataString = 'data='+$('#postdata').val() ;
       
         $.ajax({
                type: 'POST',
                url: 'sharepost.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                    alert(data);
                    
                      $('#wal').prepend(data);
                       $('#postdata').val('');
                   //    setTimeout("postdata(null)",1000000);
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
   }
 


  function anchorClicked(evt,id){
      
      alert('anchorClicked'+id);
        var dataString = 'commentdata='+ document.getElementById(id+'_comment').value+'&postid='+id;
      alert('dataString'+dataString);
      //var dataString = 'commentdata='+$('#<%= request.getParameter("id")%>_comment').val()+'&postid=<%= request.getParameter("id")%>' ;
       
         $.ajax({
                type: 'POST',
                url: 'sharecomment.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                alert(data);
                
                //document.getElementById(id+'_commentlist').appendChild(data);
                 $('#'+id+'_commentlist').append(data);
                  $('#'+id+'_comment').val('');
              //  $('#_commentlist').append(data);
                   //    setTimeout("postdata(null)",1000000);
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
  }
  
 