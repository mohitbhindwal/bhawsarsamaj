
 
        $("#post").click(function(evt){
             evt.preventDefault();
         alert('asdasdaa');
            
            $.ajax({
                type: 'POST',
                url: 'simple.jsp',
                success: function(data) {
                    
                    alert(data);
                    $("p").text(data);
                }
            });
   });
 


$('#get-date').click(function(evt) {
  // Prevent the button from triggering a form submission.
  evt.preventDefault();
 $.ajax({
    url: 'simple.jsp',
    success: function(date) {
      // Once the server responds with a date, update the
      //  textbox with that result.
      $('#date').val(date);
    }
});
 });
 
 
       $('#callme1').click(function (evt)
            {
                
                   evt.preventDefault();
                $.ajax({
                    type: "post",
                    url: "simple.jsp", //this is my servlet
                    data: "input=" +mohit,
                    success: function(msg){      
                            $('#output').append(msg);
                    }   
                });
            });
