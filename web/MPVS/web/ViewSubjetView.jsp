<%-- 
    Document   : ViewSubjetView
    Created on : Mar 12, 2013, 1:06:01 AM
    Author     : noman
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="box ">
    <div class="title">
        <h4> <span>Subjects</span> </h4>
    </div>
    <!-- End .title -->
    <div class="content top">
        <table id="datatable_example" class="responsive table table-striped table-bordered" style="width:100%;margin-bottom:0; ">
            <thead>
                <tr>
                    <th class="to_hide_phone">ID</th>
                    <th class="to_hide_phone">Name</th>
                    <th class="to_hide_phone">Credit hr</th>
                    <th class="">Status</th>
                    <th class="ms no_sort ">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${sbList}">
                    <tr>
                        <td>
                            ${item.subjectId}
                        </td>
                        <td>
                            ${item.name}
                        </td>
                        <td>
                            ${item.creditHr}
                        </td>
                        <td>
                            ${item.status}
                        </td>
                        <td class="ms">
                            <div class="btn-group1">
                                <a id="${item.id}"  class="btn btn-small record_view" rel="tooltip" data-placement="top" data-original-title="View Students">
                                    <i class="gicon-eye-open"></i>
                                </a> 
                                <a id="${item.id}"   class="btn  btn-small record_del" rel="tooltip" data-placement="bottom" data-original-title="Remove"><i class="gicon-remove "></i></a> 
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- End .content --> 
    <!--<a href="#myModal" role="button" class="btn" data-toggle="modal">Launch demo modal</a>-->
    <!-- Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">Modal header</h3>
        </div>
        <div class="modal-body">
            <p>One fine body…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button class="btn btn-primary">Save changes</button>
        </div>
    </div>
</div>
<!-- End box --> 
<script type="text/javascript">
    
    
    // Datatables
    $(document).ready(function() {
        
        
        $('.btn-group1 a.record_edit').click(function(){
        
            console.log($(this).parent());    
            console.log(this.id+'from edit');
            
        });
        $('.btn-group1 a.record_view').click(function(){

            $(location).attr("href","Users.jsp?subject_id="+this.id);
            //$('#myModal').modal('show');
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