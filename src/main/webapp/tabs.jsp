<div class="well">
    <div class="tabbable ">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#searchbhawsar" data-toggle="tab">Search</a></li>
            <li class=""><a href="#editprofile" data-toggle="tab">Profile</a></li>
            <li class=""><a href="#registration" data-toggle="tab">Registration</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="searchbhawsar">
                <p><h2>Search Bhawsar</h2></p>
                <jsp:include page="search.jsp" />
            </div>
            <div class="tab-pane" id="editprofile">
                <p><h2>Edit Profile</h2></p>
                <jsp:include page="profile.jsp" />
            </div>
            <div class="tab-pane" id="registration">
                <p><h2>Registration</h2></p>
            <div class="container" id="myregistrationdiv"> 
                <jsp:include page="registration.jsp" />
            </div>
            </div>
        </div>
    </div>
</div>