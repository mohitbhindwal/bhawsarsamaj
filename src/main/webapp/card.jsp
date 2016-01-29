<div class="col-lg-12" style="margin: 0px;padding: 0px;">
    <div class="card hovercard" style="margin: 0px;padding: 0px;">
     <div class="card-background" style="margin: 0px;padding: 0px;">
    
         <img class="card-bkimg" alt="" src="img/mohit.png">
            <!-- http://lorempixel.com/850/280/people/9/ -->
        </div>
        <div class="useravatar">
            <img alt="" src="img/mohit.png">
        </div>
        <br/>
        <div class="card-info">
            <span class="card-title">Mohit Bhindwal</span>
        </div>
    </div>
    <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group" aria-label="...">
        <div class="btn-group" role="group">
            <button type="button" id="stars" class="btn btn-primary" href="#tab1" data-toggle="tab"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                <div class="hidden-xs">Home</div>
            </button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" id="favorites" class="btn btn-primary" href="#tab2" data-toggle="tab"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                <div class="hidden-xs">Posts</div>
            </button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" id="following" class="btn btn-primary" href="#tab3" data-toggle="tab"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                <div class="hidden-xs">Search</div>
            </button>
        </div>
    </div>

        <div class="well">
      <div class="tab-content">
        <div class="tab-pane fade in active" id="tab1">
          <h3>This is tab 1</h3>
          
          <jsp:include page="share.jsp"></jsp:include>
          <div id="wal">
          <jsp:include page="getpost.jsp"></jsp:include>
          </div>
          
        </div>
        <div class="tab-pane fade in" id="tab2">
         <div id="wal2">
         <jsp:include page="getfriendspost.jsp"></jsp:include>
          </div>
        </div>
        <div class="tab-pane fade in" id="tab3">
        <jsp:include page="search.jsp" />
        </div>
      </div>
    </div>
    
    </div>