<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="header.jsp"/>
    <body onload="noBack();" onpageshow="if(event.persisted) noBack();" onunload=""><div id="wrapper">
<jsp:include page="nagation.jsp"/><div id="page-wrapper" style="padding: 0px;margin: 0px" >
                <div class="container-fluid">
                    <div class="row" id="body">
                        <% request.setAttribute("user", session.getAttribute("user")); %>
                        <jsp:include page="body.jsp"/>
                       <p/>
                    </div>  <!-- /# row -->
                </div>  <!-- /# container-fluid -->
            </div> <!-- /# page-wrapper -->
        </div> <!-- /#wrapper -->
        <!-- jQuery -->
        <script src="js/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>

        <!-- Morris Charts JavaScript -->
        <script src="js/plugins/morris/raphael.min.js"></script>
        <script src="js/plugins/morris/morris.min.js"></script>
        <script src="js/plugins/morris/morris-data.js"></script>
        <script src="js/post.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>
