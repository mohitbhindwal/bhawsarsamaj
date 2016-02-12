<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
   
      
      
 
<script src="js/registration.js"></script>
<style>
    .bv-form .help-block {
        margin-bottom: 0;
    }
    .bv-form .tooltip-inner {
        text-align: left;
    }
    .nav-tabs li.bv-tab-success > a {
        color: #3c763d;
    }
    .nav-tabs li.bv-tab-error > a {
        color: #a94442;
    }

    .bv-form .bv-icon-no-label {
        top: 0;
    }

    .bv-form .bv-icon-input-group {
        top: 0;
        z-index: 100;
    }

</style>
<div class="container">    
    <div id="signupbox" style="margin-top:50px;margin-left: 50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 pull-left">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="panel-title">Registration only for bhawsar</div>
            </div>  
            <div class="panel-body" >
                <form id="defaultForm" class="form-horizontal" role="form" action="index.jsp" method="post" onsubmit="callme();">
                    <div class="form-group">
                        <label for="firstname" class="col-md-3 control-label">First Name</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="firstname" placeholder="First Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-md-3 control-label">Last Name</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="lastname" placeholder="Last Name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">Gender</label>
                        <div class="col-md-9">
                            <select class="form-control" name="country">
                                <option value="MALE">MALE</option>
                                <option value="FEMALE">FEMALE</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-md-3 control-label">Email ID</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="email" placeholder="Email Address">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Password</label>
                        <div class="col-md-9">
                            <input type="password" class="form-control" name="password" placeholder="Password"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">Retype password</label>
                        <div class="col-md-9">
                            <input type="password" class="form-control" name="confirmPassword" placeholder="Retype Password" />
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-md-3 control-label">Birth Date</label>
                        <div class="col-md-3">
                            <select class="form-control" name="day">
                                <% for (int i = 1; i <= 31; i++) {%>
                                <option value="<%=i%>"><%=i%></option><%}%>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <select class="form-control" name="month">
                                <% for (int i = 1; i <= 12; i++) {%>
                                <option value="<%=i%>"><%=i%></option><%}%>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <select class="form-control" name="year">
                                <% for (int i = 1900; i <= 2050; i++) {%>
                                <option value="<%=i%>"><%=i%></option><%}%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Birth Time</label>
                        <div class="col-md-3">
                            <select class="form-control" name="hour">
                                <% for (int i = 0; i <= 23; i++) {%>
                                <option value="<%=i%>"><%=i%></option><%}%>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <select class="form-control" name="minute">
                                <% for (int i = 0; i <= 59; i++) {%>
                                <option value="<%=i%>"><%=i%></option><%}%>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <select class="form-control" name="seconds">
                                <% for (int i = 0; i <= 59; i++) {%>
                                <option value="<%=i%>"><%=i%></option><%}%>
                            </select>
                        </div>
                    </div>   
                    <div class="form-group">
                        <label for="firstname" class="col-md-3 control-label">Birth Place</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="fullName" placeholder="Birth Place">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-md-3 control-label">Address</label>
                        <div class="col-md-9">
                            <textarea class="form-control" type="textarea" id="message" placeholder="Please Enter Full Address" maxlength="200" rows="6"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <!-- Button -->                                        
                        <div class="col-md-offset-3 col-md-9">
                            <button id="validateBtn"   class="btn btn-primary"><i class="icon-hand-right"></i> &nbsp Sign Up</button>
                        </div>
                    </div>
                    <!-- #messages is where the messages are placed inside -->
                    <div class="form-group">
                        <div class="col-md-9 col-md-offset-3">
                            <div id="messages"></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div> 
</div>

<script type="text/javascript">
$(document).ready(function() {
    // Generate a simple captcha
    function randomNumber(min, max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    };
    $('#captchaOperation').html([randomNumber(1, 100), '+', randomNumber(1, 200), '='].join(' '));

    $('#defaultForm').bootstrapValidator({
//        live: 'disabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            firstName: {
                group: '.col-lg-4',
                validators: {
                    notEmpty: {
                        message: 'The first name is required and cannot be empty'
                    }
                }
            },
            lastName: {
                group: '.col-lg-4',
                validators: {
                    notEmpty: {
                        message: 'The last name is required and cannot be empty'
                    }
                }
            },
            username: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: 'The username is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The username must be more than 6 and less than 30 characters long'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: 'The username can only consist of alphabetical, number, dot and underscore'
                    },
                    remote: {
                        type: 'POST',
                        url: 'remote.php',
                        message: 'The username is not available'
                    },
                    different: {
                        field: 'password,confirmPassword',
                        message: 'The username and password cannot be the same as each other'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email address is required and can\'t be empty'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as username'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirm password is required and cannot be empty'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as username'
                    }
                }
            },
            birthday: {
                validators: {
                     notEmpty: {
                        message: 'The birthday field cannot be empty'
                    },
                    date: {
                        format: 'YYYY/MM/DD',
                        message: 'The birthday is not valid'
                    }
                }
            },
            gender: {
                validators: {
                    notEmpty: {
                        message: 'The gender is required'
                    }
                }
            },
            'languages[]': {
                validators: {
                    notEmpty: {
                        message: 'Please specify at least one language you can speak'
                    }
                }
            },
            'programs[]': {
                validators: {
                    choice: {
                        min: 2,
                        max: 4,
                        message: 'Please choose 2 - 4 programming languages you are good at'
                    }
                }
            },
            File: {
                validators: {
                    notEmpty: {
                        message: 'Please select value'
                    },
                    file: {
                        extension: 'pdf',
                        type: 'application/pdf',
                        message: 'Please choose a pdf file.'
                    }
                }
            },
             country: {
                validators: {
                    notEmpty: {
                        message: 'The country is required and can\'t be empty'
                    }
                }
            },
            captcha: {
                validators: {
                    callback: {
                        message: 'Wrong answer',
                        callback: function(value, validator) {
                            var items = $('#captchaOperation').html().split(' '), sum = parseInt(items[0]) + parseInt(items[2]);
                            return value == sum;
                        }
                    }
                }
            }
        }
    });

    // Validate the form manually
    $('#validateBtn').click(function() {
        $('#defaultForm').bootstrapValidator( );
    });
   function callme(){
       
        $('#defaultForm').bootstrapValidator();
        return false;
   }
  
});
</script>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery-2.0.3.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>