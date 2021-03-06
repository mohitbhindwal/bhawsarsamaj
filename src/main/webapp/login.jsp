  <%
    Cookie[] cookies=request.getCookies();
    String userName = "", password = "" ,rememberVal="";
    if (cookies != null) {
         for (Cookie cookie : cookies) {
             System.out.println("cookie "+cookie.getName()+" " +cookie.getValue());
             
           if(cookie.getName().equals("bhawsarid")) {
             userName = cookie.getValue();
           }
           if(cookie.getName().equals("bhawsarpwd")){
             password = cookie.getValue();
           }
           if(cookie.getName().equals("bhawsarrem")){
             rememberVal = cookie.getValue();
             if(rememberVal.equals("on")||rememberVal.equals("yes")||rememberVal.equals("checked")||rememberVal.equals("true"))
                 rememberVal="checked";
             else
                 rememberVal="";
           }
        }
    }
   %>


<% 
//response.setHeader("Pragma","no-cache"); 
//response.setHeader("Cache-Control","no-store"); 
//response.setHeader("Expires","0"); 
//response.setDateHeader("Expires",-1); 
%> 




<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>bhawsar.com</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Morris Charts CSS 
    <link href="css/plugins/morris.css" rel="stylesheet"-->

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward(); }
    </script>

</head>

<body style="margin-top: 0px" onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
  <div class="container" >
    <div class="row">
        <div class="col-md-12">
            <div class="pr-wrap">
                <div class="pass-reset">
                    <label>Enter the email you signed up with</label>
                    <input type="email" placeholder="Email" />
                    <input type="submit" value="Submit" class="pass-reset-submit btn btn-success btn-sm" />
                </div>
            </div>
            <div class="wrap">
                <p class="form-title">Welcome to bhawsarsamaj.com</p>
                
                <form class="login" action="Login" method="post">
                    <input type="text" placeholder="Username" name="Username"  value="<%=userName%>" />
                    <input type="password" placeholder="Password" name="Password"  value="<%=password%>"/>
                <label id="output"><%= request.getAttribute("error")==null?"":request.getAttribute("error")%></label>
                <input type="submit" value="Sign In" class="btn btn-primary btn-sm" id="callme"/>
                <!--input type="submit" value="Registration" class="btn btn-primary btn-sm" /-->
                <div class="remember-forgot">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"  name="rememberme" value="<%=rememberVal%>" <%=rememberVal%>/>
                                    Remember Me
                                </label>
                            </div>
                        </div>
                        <div class="col-md-6 forgot-pass-content">
                            <a onclick="getModal(this)" class="forgot-pass">Forgot Password</a>
               <!--button type="button" class="btn btn-link forgot-pass" data-toggle="modal" data-target="#forgotpwd">
                Forgot Password
               </button-->
                        </div>
                    </div>
                </div>
                   </form>
            </div>
        </div>
    </div>
    
                                    
                                    
 <div class="modal fade" id="forgotpwd" tabindex="-1" role="dialog" aria-labelledby="myModallLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
          <h4 class="modal-title" id="myModalLabel">Forgot Password</h4>
      </div>
      <div class="modal-body">
       <p>Don't worry if your password is lost, password will be sent on your mail id used to sing in.<p>
           <input type="text" name="forgotpwd" maxlength="50"></br></br></br>
           <button type="button" class="btn btn-primary">Send Password</button>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>                               
                                    
     
</div>

  

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script>
    <script src="js/post.js"></script>

</body>

</html>
