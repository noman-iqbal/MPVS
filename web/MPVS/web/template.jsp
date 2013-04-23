<%-- 
    Document   : template
    Created on : Mar 2, 2013, 7:32:45 PM
    Author     : noman
--%>
<%@page import="com.mpvs.helper.Auth"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="sidebar_default  no-js" lang="en">
    <head>
        <meta charset="utf-8">
        <title>M P V S</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="Noman iqbal">
        <link rel="shortcut icon" href="css/images/favicon.png">
        <script src="js/jquery.js" type="text/javascript"> </script> 
        <link href="js/plugins/chosen/chosen/chosen.css" rel="stylesheet">
        <link href="css/twitter/bootstrap.css" rel="stylesheet">
        <link href="css/base.css" rel="stylesheet">
        <link href="css/twitter/responsive.css" rel="stylesheet">
        <link href="css/jquery-ui-1.8.23.custom.css" rel="stylesheet">

        <script src="js/plugins/modernizr.custom.32549.js"></script>

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
              <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
              <![endif]-->
    </head>
    <%//        boolean is_loged_in = Auth.isLogedIn(request, response);
//        boolean is_admin = Auth.isAdmin(request, response);
    %> 
    <body>
        <div id="loading"><img src="img/ajax-loader.gif"></div>
        <div id="responsive_part">
            <div class="logo"> <a href="index.jsp">
                    <span><img src="img/toplogo.png" width="200" height="65"></span>
                </a> 
            </div>
            <ul class="nav responsive">
                <li>
                    <button class="btn responsive_menu icon_item" data-toggle="collapse" data-target=".overview"> <i class="icon-reorder"></i> </button>
                </li>
            </ul>
        </div>
        <!-- Responsive part -->
        <div id="sidebar" class=" collapse1 in color_10">
            <div class="scrollbar">
                <div class="track">
                    <div class="thumb">
                        <div class="end">
                        </div>
                    </div>
                </div>
            </div>
            <div class="viewport ">
                <div class="overview collapse">
                    <ul id="sidebar_menu" class="navbar nav nav-list container full">
                        <li class="accordion-group  color_4"> <a class="dashboard" href="index.jsp">
                                <i class="icon-home" style="font-size:  40px"></i>
                                <span>Home</span></a> </li>
                        <li class="accordion-group color_7"> <a class="accordion-toggle widgets collapsed " data-toggle="collapse" data-parent="#sidebar_menu" href="#collapse1"> 
                                <i class="icon-group" style="font-size:  40px"></i>
                                <span>User</span></a>
                            <ul id="collapse1" class="accordion-body collapse ">
                                <c:if test="${userInfo.type eq 'member' or userInfo.type eq 'admin' or userInfo.type eq 'super_admin'}">
                                    <c:if test="${userInfo.type eq 'admin' or userInfo.type eq 'super_admin'}">
                                        <li><a href="AddAdmin.jsp">Add New Admin</a></li>
                                        <li><a href="AddMember.jsp">Add New Teacher</a></li>
                                    </c:if>
                                    <li><a href="AddStudent.jsp">Add New Student</a></li>
                                </c:if>
                                <li><a href="Users.jsp">View users</a></li>
                            </ul>
                        </li>



                        <li class="accordion-group color_8"> <a class="accordion-toggle widgets collapsed " data-toggle="collapse" data-parent="#sidebar_menu" href="#collapse2"> 
                                <i class="icon-credit-card" style="font-size:  40px"></i>
                                <span>Subject</span></a>
                            <ul id="collapse2" class="accordion-body collapse ">
                                <c:if test="${userInfo.type eq 'admin' or userInfo.type eq 'super_admin'}">
                                    <li><a href="AddSubject.jsp">Add Subject</a></li>
                                </c:if>
                                <li><a href="Subject.jsp">View all Subject</a></li>
                            </ul>
                        </li>
                        <c:if test="${userInfo.type eq 'member' or userInfo.type eq 'super_admin'}">
                            <li class="accordion-group color_9"> <a class="accordion-toggle widgets collapsed " data-toggle="collapse" data-parent="#sidebar_menu" href="#collapse3"> 
                                    <!--                                    <img src="img/menu_icons/docs.png">-->
                                    <i class="icon-book" style="font-size:  40px"></i>
                                    <span>Exam</span></a>
                                <ul id="collapse3" class="accordion-body collapse ">
                                    <li><a href="AddExam.jsp">Add Exam</a></li>
                                    <li><a href="exam.jsp">View Exam</a></li>
                                </ul>
                            </li>
                        </c:if>
                        <c:if test="${userInfo.type eq 'student'}">
                            <li class="accordion-group color_9"> <a class="accordion-toggle widgets collapsed " data-toggle="collapse" data-parent="#sidebar_menu" href="#collapse5"> 


                                    <i class="icon-bar-chart" style="font-size:  40px"></i>

                                    <span>Results</span></a>
                                <ul id="collapse5" class="accordion-body collapse ">
                                    <li><a href="results.jsp">View All</a></li>
                                </ul>
                            </li>
                        </c:if>
                        <li class="accordion-group color_9"> <a class="accordion-toggle widgets collapsed " data-toggle="collapse" data-parent="#sidebar_menu" href="#collapse4"> 
                                <i class="icon-envelope" style="font-size:  40px"></i>
                                <span>Message</span></a>
                            <ul id="collapse4" class="accordion-body collapse ">
                                <li><a href="message.jsp">Send</a></li>
                                <li><a href="messages.jsp">View All</a></li>
                            </ul>
                        </li>
                    </ul>
                    <!-- End sidebar_box --> 
                </div>
            </div>
        </div>
        <div id="main">
            <div class="container">
                <div class="header row-fluid">
                    <div class="logo">
                        <a href="index.jsp">
                            <div class="title">
                                <span class="name"style="font-family: oswald,'open sans','helvetica condensed bold','arial black';font-size: 36px; line-height: 36px;" >M.P.V.S</span><br>
                                <span class="subtitle" style="font-size: 18px;">Multi Purpuse Viva Exam System</span>
                            </div>
                        </a> 
                    </div>
                    <div class="top_right">
                        <ul class="nav nav_menu">
                            <li class="dropdown"> <a class="dropdown-toggle administrator" id="dLabel" role="button" data-toggle="dropdown" data-target="#" href="/page.html">
                                    <div class="title"><span class="name">${userInfo.firstName}</span><span class="subtitle">${userInfo.type}</span></div>
                                    <span class="icon"><img style="height: 75px;width: 75px;" src="${userInfo.profilePicUrl}"></span></a>
                                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                    <li><a href="EditUser?user_id=${userInfo.id}"><i class=" icon-user"></i> My Profile</a></li>
                                    <li><a href=""><i class=" icon-cog"></i>Settings</a></li>
                                    <li><a href="logout.jsp"><i class=" icon-unlock"></i>Log Out</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <!-- End top-right --> 
                </div>
                <script src="js/fileinput.jquery.js" type="text/javascript"></script> 
                <script src="js/jquery-ui-1.8.23.custom.min.js" type="text/javascript"></script> 
                <script src="js/jquery.touchdown.js" type="text/javascript"></script> 
                <script type="text/javascript" src="js/plugins/jquery.peity.min.js"></script> 
                <script type="text/javascript" src="js/plugins/flot/jquery.flot.js"></script> 
                <script type="text/javascript" src="js/plugins/flot/jquery.flot.resize.js"></script> 
                <script type="text/javascript" src="js/plugins/jquery.maskedinput-1.3.js"></script> 
                <script src="js/plugins/enquire.min.js" type="text/javascript"></script> 
                <script type="text/javascript" src="js/plugins/knockout-2.0.0.js"></script> 
                <script type="text/javascript" src="js/plugins/wysihtml5-0.3.0.min.js"></script> 
                <script type="text/javascript" src="js/plugins/textarea-autogrow.js"></script> 
                <script type="text/javascript" src="js/plugins/character-limit.js"></script> 
                <script language="javascript" type="text/javascript" src="js/plugins/jquery.avgrund.js/js/jquery.avgrund.js"></script> 
                <script language="javascript" type="text/javascript" src="js/plugins/jquery.sparkline.min.js"></script> 
                <script src="js/plugins/excanvas.compiled.js" type="text/javascript"></script> 
                <script language="javascript" type="text/javascript" src="js/plugins/jquery.uniform.min.js"></script> 
                <script language="javascript" type="text/javascript" src="js/jnavigate.jquery.min.js"></script> 
                <script language="javascript" type="text/javascript" src="js/jquery.touchSwipe.min.js"></script> 
                <script language="javascript" type="text/javascript" src="js/plugins/chosen/chosen/chosen.jquery.min.js"></script> 
                <script language="javascript" type="text/javascript" src="js/plugins/autoNumeric.js"></script> 
                <!--        BootStrap -->
                <script src="js/plugins/bootstrap-wysihtml5.js" type="text/javascript" ></script> 
                <script src="js/bootstrap-button.js" type="text/javascript"></script>
                <script src="js/bootstrap-transition.js" type="text/javascript"></script> 
                <script src="js/bootstrap-alert.js" type="text/javascript"></script> 
                <script src="js/bootstrap-modal.js" type="text/javascript"></script> 
                <script src="js/bootstrap-dropdown.js" type="text/javascript"></script> 
                <script src="js/bootstrap-scrollspy.js" type="text/javascript"></script> 
                <script src="js/bootstrap-tab.js" type="text/javascript"></script> 
                <script src="js/bootstrap-tooltip.js" type="text/javascript"></script> 
                <script src="js/bootstrap-popover.js" type="text/javascript"></script> 
                <script src="js/bootstrap-collapse.js" type="text/javascript"></script> 
                <script src="js/bootstrap-carousel.js" type="text/javascript"></script> 
                <script src="js/bootstrap-typeahead.js" type="text/javascript"></script> 
                <script src="js/bootstrap-affix.js" type="text/javascript"></script> 
                <script src="js/plugins/bootstrap-colorpicker.js" type="text/javascript" ></script> 
                <script src="js/plugins/bootstrap-datepicker.js" type="text/javascript" ></script> 
                <!-- Validation plugin --> 
                <script src="js/plugins/validation/dist/jquery.validate.min.js" type="text/javascript"></script> 
                <!-- Data tables plugin --> 
                <script type="text/javascript" language="javascript" src="js/plugins/datatables/js/jquery.dataTables.js"></script> 

                <!-- Custom made scripts for this template --> 
                <script src="js/scripts.js" type="text/javascript"></script> 
                <div id="main_container">
                    <jsp:include page="${jsppage}"></jsp:include>                   
                </div>
            </div>
            <div id="footer">
                <p> &copy; Noman Iqbal , 2013. All Rights Reserved </p>
            </div>
            <script type="text/javascript" src="js/plugins/jquery.tinyscrollbar.js"></script> 

        </div>
    </body>
</html>