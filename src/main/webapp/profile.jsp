<!--profile.jsp start-->
<script>
function onuploadimage(divname,userid){
    alert('onuploadimage clicked '+divname+' '+userid);
    var dataString = 'divname='+divname+'&userid='+userid;
         //    alert('dataString'+dataString);
    $.ajax({
        type: 'POST',
        url: 'uploader.jsp',
        dataType: 'html',
        data: dataString,
        success: function (data) {
          //  alert(data);

            $('#'+divname+'').append(data);
            //    setTimeout("postdata(null)",1000000);
        },
        error: function (request, error) {
            alert("Request: " + JSON.stringify(request));
        }
    });
}
</script>

<div class="row" style="margin: 5px">
    <table class="table" >
        <tr>
            <td> <button id="myavtar" onclick="onuploadimage('myavtardiv',${user.id});" class="btn btn-lg btn-block btn-primary" name="${user.id}">Avtar</button></td>
            <td> <button id="profilepic1" onclick="onuploadimage('profilepicdiv1',${user.id});" class="btn btn-lg btn-block btn-primary" name="${user.id}">Profile Pic 1</button></td>
            <td> <button id="profilepic2" onclick="onuploadimage('profilepicdiv2',${user.id});" class="btn btn-lg btn-block btn-primary" name="${user.id}">Profile Pic 2</button></td>
            <td> <button id="profilepic3" onclick="onuploadimage('profilepicdiv3',${user.id});" class="btn btn-lg btn-block btn-primary">Profile Pic 3</button></td>
        </tr>
        <tr>
            <td><div id="myavtardiv"></div></td>
            <td><div id="profilepicdiv1"></div></td>
            <td><div id="profilepicdiv2"></div></td>
            <td><div id="profilepicdiv3"></div></td>
        </tr>
</table>
</div>
        <!--profile.jsp end-->
        