<!--link rel="stylesheet" href="../font-awesome/css/font-awesome.css"-->
<div class="container" >
    <div class="row">
        <!--h3>Status Upload Snipp</h3-->
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="widget-area no-padding blank">
                <div class="status-upload">
                    <form>
                        <textarea id="postdata" placeholder="What are you doing right now?" style="border-width: 1px;border-color: green"></textarea>
                        <ul id = "uploaddiv">
                            <li><a title="" data-toggle="tooltip" data-placement="bottom" data-original-title="Audio"><i class="fa fa-music"></i></a></li>
                            <li><a title="" data-toggle="tooltip" data-placement="bottom" data-original-title="Video"><i class="fa fa-video-camera"></i></a></li>
                            <li><a title="" data-toggle="tooltip" data-placement="bottom" data-original-title="Sound Record"><i class="fa fa-microphone"></i></a></li>
                            <li><a id="upload" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="Picture"><i class="fa fa-picture-o"></i></a></li>
                                <div id ="loadimage"></div>
                        </ul>
                        <button name="post" id="post" class="btn btn-success green"><i class="fa fa-share"></i> Share</button>
                    </form>
                </div> 
            </div>
        </div>
    </div>
</div>