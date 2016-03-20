/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


  
           var overlay ;
    $(document).bind('ajaxStart', function(){
       // alert('ajaxStart');
   // overlay = new ItpOverlay();
 
   //overlay.show("body_id");
    showProgress();
   
}).bind('ajaxStop', function(){
    // alert('ajaxStop');
    hideProgress();
  // overlay.hide("body_id");
});


  var spinnerVisible = false;
    function showProgress() {
        if (!spinnerVisible) {
            $("div#spinner").fadeIn("fast");
            spinnerVisible = true;
        }
    };
    function hideProgress() {
        if (spinnerVisible) {
            var spinner = $("div#spinner");
            spinner.stop();
            spinner.fadeOut("fast");
            spinnerVisible = false;
        }
    };
    
    

$(document).ready(function(){
    $("[data-toggle=tooltip]").tooltip();
});

$("#postdata").bind('paste', function(e) {
    var elem = $(this);

    setTimeout(function() {
        // gets the copied text after a specified time (100 milliseconds)
        var text = elem.val();
        var dataString = 'src=' + text;
        
        if ( text.search("youtube") == -1 && ( text.search("http") == -1 || text.search("https") == -1)){
               //document.write("Does not contain Apples" );
               return ;
            }
  
    $.ajax({
        type: 'POST',
        url: 'loadvideo.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
             
            //   $('#loadvideo').attr("src",data);
              if ($('#loadvideo').is(':empty')){
                 
             $('#loadvideo').html(data);
                  setTimeout(function(){
                 $('#myVModal').modal('show'); 
             }, (1 *  500));
           }else
           $('#loadvideo').html('');
        }
    });
        
       
    }, 100);
});

function shareVideo(evt,userid,videoURL){
     //   if(evt!==null)
    //  evt.preventDefault();
    var text = $('#postvideodata').val();   
   // alert('userid'+userid+' videoURL =' + videoURL + ' text =' + text);
     $('#myVModal').modal('hide'); 
     $('#postdata').val('');
     
      var dataString = 'data='+text + '&url='+videoURL ;
       
         $.ajax({
                type: 'POST',
                url: 'sharepost.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                  //  alert(data);
                    
                      $('#wal').prepend(data);
                     
                   //    setTimeout("postdata(null)",1000000);
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
            
            
    return false;
    }

  
 $("#upload").click(function(event) {
     uploadImage(event);
});

 $("#uploadvideo").click(function(event) {
     uploadVideo(event);
});

function uploadVideo(evt){
  //  alert('uploadVideo function');
     var dataString = 'src=' + '';
        $.ajax({
        type: 'POST',
        url: 'loadvideo.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
           //    alert(data);
            //   $('#loadvideo').attr("src",data);
              if ($('#loadvideo').is(':empty')){
              $('#loadvideo').html(data);
               setTimeout(function(){
                 $('#myVModal').modal('show'); 
             }, (1 *  500));
              
           }else
           $('#loadvideo').html('');
        }
    });
    
}
 

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
           // alert(data);
             if ($('#loadimage').is(':empty')){
            $('#loadimage').append(data);
         }else
             $('#loadimage').html('');
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
             
             if($('#postdata').val()===null || $('#postdata').val().trim().length === 0)
                 return ;
             
     //  alert('dataString ='+dataString);
         $.ajax({
                type: 'POST',
                url: 'sharepost.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                 //   alert(data);
                    
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
      
  //    alert('anchorClicked'+id);
        var dataString = 'commentdata='+ document.getElementById(id+'_comment').value+'&postid='+id;
   //   alert('dataString'+dataString);
      //var dataString = 'commentdata='+$('#<%= request.getParameter("id")%>_comment').val()+'&postid=<%= request.getParameter("id")%>' ;
       
         $.ajax({
                type: 'POST',
                url: 'sharecomment.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
             //   alert(data);
                
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
//     alert('likeme');
 //    alert($(evt).attr('isclicked') );
  //   alert('postid' + postid + ' frompost '+frompost);
  //   alert($(evt).text());
     if( $(evt).attr('isclicked') === 'true')
     {
   //      alert('already checked');
         return ;
     }
    var dataString = 'postid=' + postid +'&frompost='+frompost;
    $.ajax({
        type: 'POST',
        url: 'like.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
      //      alert('likemevalue received'+data);
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
 //   alert('likebyuser postid' + postid + ' frompost ' + frompost);
    
    var dataString = 'postid=' + postid + '&frompost=' + frompost;
    $.ajax({
        type: 'POST',
        url: 'likebyuser.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
        //    alert('likemevalue received'+data);
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
 //   alert('sharebyuser postid' + postid + ' frompost ' + frompost);
  //  alert($(evt).text());
    var dataString = 'postid=' + postid + '&frompost=' + false;
    $.ajax({
        type: 'POST',
        url: 'sharebyuser.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
        //     alert('sharebyuser received'+data);
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




function sendfriendrequest(sender, sendername ,receiver ,receivername){
    
    
//alert('sendfriendrequest sender = '+sender+ ' sendername ' + sendername + ' receiver ' +receiver +' receivername '+receivername );
 
   
    var dataString = 'sender=' + sender +'&receiver='+receiver + '&sendername='+sendername + '&receivername=' +receivername  ;
    $.ajax({
        type: 'POST',
        url: 'sendfriendrequest.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
        //    alert(data.trim());
              $('#sendfriendrequestdiv').html(data.trim());
        }
    });
}

function openmenu(menu){
        
  //  alert(menu);
       $.ajax({
        type: 'POST',
        url: menu,
         dataType: 'html',
     //   data: dataString,
        success: function (data) {
          //  alert(data.trim());
              $('#body').html(data.trim());
        }
    });
    
}


function shareposttomyfriends(sharebyid,sharebyusername, postid){
 //    $('#myModal').modal('hide');
     //       $('#mohitmodal').html('');
 //   alert('share by'+sharebyid + 'postid '+postid);
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
 
 function changeFriendRequestStatus(evt , requeststatus,friendrequestid){
      alert($( "#acceptreject_"+friendrequestid ).val() );
        
         
     $( "#acceptreject_"+friendrequestid ).html("<button  class='btn btn-primary' >Friend request "+ requeststatus +"</button>");
        
        
        
    var dataString = 'requeststatus=' + requeststatus +'&friendrequestid='+friendrequestid;
    // alert('dataString by'+dataString);
     
          $.ajax({
        type: 'POST',
        url: 'notification.jsp',
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
 
 function  getModal(evt){
 
      $('#forgotpwd').modal('show');
     
 }
 
 
 function unfriend(unfriendby,unfriendto ){
     var dataString = 'unfriendby=' + unfriendby +'&unfriendto='+unfriendto ;
    $.ajax({
        type: 'POST',
        url: 'logic.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
        $('#unfriend_'+unfriendto).html('');
        }
    });
     
 }
 
 
 
 
 