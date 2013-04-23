<%-- 
    Document   : ViewUsersView
    Created on : Mar 6, 2013, 5:07:57 PM
    Author     : noman
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mpvs.db.Users"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="box color_13">
    <div class="title">
        <h4>
            <c:if test="${userInfo.type eq 'member' or userInfo.type eq 'student'}">
                Students
            </c:if>
            <c:if test="${userInfo.type eq 'admin' or userInfo.type eq 'super_admin'}">
                All Users
            </c:if>
        </h4>
    </div>

    <div class="content top">
        <table id="datatable_example" class="responsive table table-striped table-bordered" style="width:100%;margin-bottom:0; ">
            <thead>
                <tr>
                    <th class="jv no_sort">Image</th>
                    <th class="to_hide_phone">Name</th>
                    <th class="to_hide_phone ue no_sort">Last Name</th>
                    <th class="to_hide_phone span2">Type</th>
                    <th class="">Status</th>
                    
                    <th class="ms no_sort ">Actions</th>
                </tr>
            </thead>
            <tbody>





                <%
                    List<Users> userList = (ArrayList<Users>) request.getAttribute("list");
                    for (Users u : userList) {

                %>
                <tr>
                    <td>
                        <img class="thumbnail small" src="<%=u.getProfilePicUrl()%>" width="50" height="50">
                    </td>
                    <td ><%=u.getFirstName()%></td>
                    <td><%=u.getLastName()%></td>
                    <td><%=u.getType()%></td>
                    <td><%=u.getStatus()%></td>
                    <td class="ms">
                        <div class="btn-group1">
                            <c:if test="${userInfo.type eq 'admin' or userInfo.type eq 'super_admin'}">
                                <a id="<%=u.getId()%>" class="btn btn-small record_edit"  rel="tooltip" data-placement="left" data-original-title=" Edit ">
                                    <i class="gicon-edit"></i>
                                </a>
                                <a id="<%=u.getId()%>"   class="btn  btn-small record_del" rel="tooltip" data-placement="bottom" data-original-title="Remove"><i class="gicon-remove "></i></a> 
                                <a id="<%=u.getId()%>"  class="btn btn-small record_view" rel="tooltip" data-placement="top" data-original-title="View">
                                    <i class="gicon-eye-open"></i>
                                </a> 
                            </c:if>
                            <c:if test="${userInfo.type eq 'member' or userInfo.type eq 'student' }">
                                <a id="<%=u.getId()%>"  class="btn btn-small record_view" rel="tooltip" data-placement="top" data-original-title="View">
                                    <i class="gicon-eye-open"></i>
                                </a> 

                            </c:if>

                        </div>
                    </td>
                </tr>


                <%
                    }%>

            </tbody>
        </table>
    </div>
    <!-- End .content --> 

    <!--<a href="#myModal" role="button" class="btn" data-toggle="modal">Launch demo modal</a>-->

    <!-- Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    </div>


</div>
<!-- End box --> 

<script type="text/javascript">
    // Datatables
    $(document).ready(function() {
        
        
        $('.btn-group1 a.record_edit').click(function(){
            
            $(location).attr("href","EditUser?user_id="+this.id);
            console.log($(this).parent());    
            console.log(this.id+'from edit');
            
        });
        $('.btn-group1 a.record_view').click(function(){

            $.ajax({
                type:'POST',url:'ajcall',
                data:{id:this.id,func:'getUserPopUp'},
                dataType:'HTML'
            }).done(function(msg){
                $('#myModal').html(msg);
                $('#myModal').modal('show');
                
            });
         
            console.log($(this).parents('tr'));
            console.log(this.id+'from View');
            
        });
        $('.btn-group1 a.record_del').click(function(){
            var $this=this;
            $.getJSON("ajcall",{id:this.id,func:'delUser'},  function(data){
                $($this).parents('tr').remove();
                alert(data.message)
            });
            console.log(this.id+'from Delet');
            
        });
        var dontSort = [];
        $('#datatable_example thead th').each( function () {
            if ( $(this).hasClass( 'no_sort' )) {
                dontSort.push( { "bSortable": false } );
            } else {
                dontSort.push( null );
            }
        } );
        $('#datatable_example').dataTable( {
            "sDom": "<'row-fluid table_top_bar'<'span12'<'to_hide_phone' f>>>t<'row-fluid control-group full top' <'span4 to_hide_tablet'l><'span8 pagination'p>>",
            "aaSorting": [[ 1, "asc" ]],
            "bPaginate": true,
            
            "sPaginationType": "full_numbers",
            "bJQueryUI": false,
            "aoColumns": dontSort
            
        } );
        $.extend( $.fn.dataTableExt.oStdClasses, {
            "s`": "dataTables_wrapper form-inline"
        } );
        
    });
    

</script> 