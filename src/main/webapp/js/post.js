/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 $('#loading-image').html('<img src="img/loading.gif" />');
 $('#loading-image').hide();
$('#loading-image').bind('ajaxStart', function(){
    $(this).show();
}).bind('ajaxStop', function(){
    $(this).hide();
});



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
  
 
function likeme(evt, postid,frompost) {
     alert('likeme');
     alert($(evt).attr('isclicked') );
     alert('postid' + postid + ' frompost '+frompost);
     alert($(evt).text());
     if( $(evt).attr('isclicked') === 'true')
     {
         alert('already checked');
         return ;
     }
    var dataString = 'postid=' + postid +'&frompost='+frompost;
    $.ajax({
        type: 'POST',
        url: 'like.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
            alert('likemevalue received'+data);
            if(frompost){
            $('#'+postid+'_likevalue').html('&nbsp;'+data.trim()+'&nbsp;');
            $('#'+postid+'_likevalue').css('color', '#337ab7');
        }else{
            $('#'+postid+'_clikevalue').html('&nbsp;'+data.trim()+'&nbsp;');
            $('#'+postid+'_clikevalue').css('color', '#337ab7');
        }
        $(evt).attr('isclicked','true');
           // $(evt).html('<i class="fa fa-thumbs-up icon"></i>'+data.trim());
        }
    });
}


 

function likebyuser(evt, postid, frompost) {
    alert('likebyuser postid' + postid + ' frompost ' + frompost);
    
    var dataString = 'postid=' + postid + '&frompost=' + frompost;
    $.ajax({
        type: 'POST',
        url: 'likebyuser.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
            alert('likemevalue received'+data);
            $('#mohitmodal').html(data);
             setTimeout(function(){
                 $('#myModal').modal('show'); 
             }, (1 *  500));
            $('#myModal').on('hidden.bs.modal', function (e) {
                   $('#mohitmodal').html('');
                  });
        }
    });
}

function sharebyuser(evt, postid, frompost) {
    alert('sharebyuser postid' + postid + ' frompost ' + frompost);
    alert($(evt).text());
    var dataString = 'postid=' + postid + '&frompost=' + false;
    $.ajax({
        type: 'POST',
        url: 'sharebyuser.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
             alert('sharebyuser received'+data);
            $('#mohitmodal').html(data);
                 setTimeout(function(){
                 $('#myModal').modal('show'); 
             }, (1 *  500));
            $('#myModal').on('hidden.bs.modal', function (e) {
                   $('#mohitmodal').html('');
                  });
            
            
            
            
            
            
            
            // $(evt).html('<i class="fa fa-thumbs-up icon"></i>'+data.trim());
        }
    });
}




function sendfriendrequest(sender,receiver , sendername ){
    
    
    alert('sendfriendrequest sender = '+sender+ ' receiver ' + receiver  );
 
   
    var dataString = 'sender=' + sender +'&receiver='+receiver + '&sendername='+sendername;
    $.ajax({
        type: 'POST',
        url: 'sendfriendrequest.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
            alert(data.trim());
              $('#sendfriendrequestdiv').html(data.trim());
        }
    });
}

function openmenu(menu){
        
    alert(menu);
       $.ajax({
        type: 'POST',
        url: 'friends.jsp',
         dataType: 'html',
     //   data: dataString,
        success: function (data) {
            alert(data.trim());
              $('#body').html(data.trim());
        }
    });
    
}


function shareposttomyfriends(sharebyid,sharebyusername, postid){
    
    alert('share by'+sharebyid + 'postid '+postid);
    var dataString = 'sharetomyfriends=' + true +'&sharebyuserid='+sharebyid +'&sharebyusername'+sharebyusername+'&postid='+postid;
          $.ajax({
        type: 'POST',
        url: 'sharebyuser.jsp',
         dataType: 'html',
         data: dataString,
        success: function (data) {
            $('#myModal').modal('hide');
            $('#mohitmodal').html('');
        },
          error: function (request, error) {
             $('#myModal').modal('hide');
             $('#mohitmodal').html('');
        }
    });
    
    
}