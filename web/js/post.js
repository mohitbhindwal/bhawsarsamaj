/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    $("[data-toggle=tooltip]").tooltip();
});

     $("#post").click(function(evt){
         postdata(evt);

   });
   
   
   function postdata(evt){
             var dataString = 'mdata='+$('#postdata').val() ;
         if(evt!==null)
             evt.preventDefault();
         $.ajax({
                type: 'POST',
                url: 'getpost.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                //   alert(data);
                    
                     $('#wal').append(data);
                       setTimeout("postdata(null)",1000000);
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
   }
 



