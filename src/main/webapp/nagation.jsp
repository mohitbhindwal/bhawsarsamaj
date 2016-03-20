 


        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand"  href="javascript:void(0);" style="background: #337ab7;color: #FFF"><I>Bawsar Samaj</I></a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <jsp:include page="notification.jsp"/>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${user.name} <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <!--li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li-->
                        <li>
                            <a  href="logout.jsp"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a onclick="getbody(this,${user.id});" href="javascript:void(0);"><i class="fa fa-fw fa-dashboard"></i> ${user.name}</a>
                    </li>
                    <li>
                        <a onclick="openmenu('friends.jsp')" href="javascript:void(0);"><i class="fa fa-fw fa-user"></i> Friends</a>
                    </li>
                    <!--li >
                        
                        <a onclick="openmenu('registration.jsp')" href="javascript:void(0);"><i class="fa fa-fw fa-table"></i> Registration</a>
                    
                    </li>   
                    <li>
                        <a onclick="openmenu('profile.jsp')" href="javascript:void(0);"><i class="fa fa-fw fa-gear"></i> Profile</a>
                    </li-->
                    <li>
                        <a onclick="openmenu('events.jsp')"><i class="fa fa-fw fa-desktop"></i> Events</a>
                    </li>
                     <li>
                        <a onclick="openmenu('matrimony.jsp')"><i class="fa fa-fw fa-desktop"></i> Matrimony</a>
                    </li>
                    <!--li>
                        <a href="bootstrap-grid.html"><i class="fa fa-fw fa-wrench"></i> Bootstrap Grid</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i> Dropdown <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="demo" class="collapse">
                            <li>
                                <a href="#">Dropdown Item</a>
                            </li>
                            <li>
                                <a href="#">Dropdown Item</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="blank-page.html"><i class="fa fa-fw fa-file"></i> Blank Page</a>
                    </li>
                    <li>
                        <a href="index-rtl.html"><i class="fa fa-fw fa-dashboard"></i> RTL Dashboard</a>
                    </li-->
                </ul>
            </div><!-- /.navbar-collapse -->
        </nav><!-- Navigation End -->