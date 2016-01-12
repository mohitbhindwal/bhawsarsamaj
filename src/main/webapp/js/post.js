/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    $("[data-toggle=tooltip]").tooltip();
});

  
 $("#upload").click(function(event) {
    alert("uploadImage() of post.js  will call the uploader.jsp");  
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
       alert('postdata() of post.js is called when share button is clicked');
        
      // if image is to be uploaded
        
       if (!$('#loadimage').is(':empty')){
        imageid = ShowContent(evt,callback);
         return ;
         }
        
         alert('return call imageid '+imageid);
       
             var dataString = 'data='+$('#postdata').val() ;
         if(evt!==null)
             evt.preventDefault();
         $.ajax({
                type: 'POST',
                url: 'sharepost.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                //   alert(data);
                    
                      $('#wal').append(data);
                   //    setTimeout("postdata(null)",1000000);
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
   }
 



