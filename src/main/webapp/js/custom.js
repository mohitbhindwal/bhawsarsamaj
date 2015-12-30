
 
   



 
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
