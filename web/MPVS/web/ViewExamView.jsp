<%-- 
    Document   : ViewExamView
    Created on : Mar 14, 2013, 3:30:46 PM
    Author     : noman
--%>
<%@page import="com.mpvs.db.Subjects"%>
<%@page import="com.mpvs.db.Exams"%>
<%@page import="com.mpvs.db.Users"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="box ">
    <div class="title">
        <h4><span>Exams</span></h4>
    </div>
    <div class="content top">
        <table id="dataExamTable" class="responsive table table-striped table-bordered" style="width:100%;margin-bottom:0; ">
            <thead>
                <tr>
                    <th class="jv no_sort">
                        <label class="checkbox">
                            <input type="checkbox">
                        </label>
                    </th>
                    <th class="to_hide_phone">Title</th>
                    <th class="to_hide_phone ue no_sort">Desc</th>
                    <th class="to_hide_phone span2">Creater</th>
                    <th class="">Subject</th>
                    <th class="">On</th>
                    <th class="">Time</th>
                    <th class="">Status</th>
                    <th class="ms no_sort ">Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Exams> examList = (List<Exams>) request.getAttribute("list");
                    for (Exams u : examList) {
                %>
                <tr>
                    <td>
                        <label class="checkbox">
                            <input type="checkbox">
                        </label>
                    </td>
                    <td>
                        <%=u.getTitle()%>
                    </td>
                    <td ><%=u.getDescription()%></td>
                    <td><%=((Users) u.getUserCreaterId()).getFirstName()%></td>
                    <td><%=((Subjects) u.getSbId()).getName()%></td>
                    <td><%=u.getHeldId().getDayName()%></td>
                    <td><%=u.getHeldId().getStartTime()%></td>
                    <td><%=u.getStatus()%></td>
                    <td class="ms">
                        <div class="btn-group1">
                            <a id="<%=u.getId()%>" class="btn btn-small record_edit"  rel="tooltip" data-placement="left" data-original-title=" Edit ">
                                <i class="gicon-edit"></i>
                            </a>
                            <a id="<%=u.getId()%>" class="btn btn-small record_add"  rel="tooltip" data-placement="left" data-original-title=" Add Result ">
                                <i class="gicon-plus"></i>
                            </a>
                            <a id="<%=u.getId()%>"  class="btn btn-small record_view" rel="tooltip" data-placement="top" data-original-title="View">
                                <i class="gicon-eye-open"></i>
                            </a> 
                            <a id="<%=u.getId()%>"   class="btn  btn-small record_del" rel="tooltip" data-placement="bottom" data-original-title="Remove"><i class="gicon-remove "></i></a> 
                        </div>
                    </td>
                </tr>
                <%
                    }%>
            </tbody>
        </table>
    </div>
    <!--<a href="#myModal" role="button" class="btn" data-toggle="modal">Launch demo modal</a>-->
    <!-- Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      
    </div>
</div>
<!-- End box --> 
<script type="text/javascript">
    
    
    // Datatables
    $(document).ready(function() {
        
        
        $('.btn-group1 a.record_add').click(function(){
            $(location).attr("href","result.jsp?id="+this.id);
            console.log($(this).parent());    
            console.log(this.id+'from edit');
            
        });
        
        $('.btn-group1 a.record_edit').click(function(){
            $(location).attr("href","editExam?exam_id="+this.id);
            console.log($(this).parent());    
            console.log(this.id+'from edit');
            
        });
        
        
        $('.btn-group1 a.record_view').click(function(){
            $.ajax({
                type:'POST',url:'ajcall',
                data:{id:this.id,func:'getExamView'},
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
            $.getJSON("ajcall",{id:this.id,func:'delExam'},  function(data){
                $($this).parents('tr').remove();
                alert(data.message)
            });
            console.log(this.id+'from Delet');
    
        });
  
        test();
        //        setTimeout("test()", 0);
    });
    
    function test(){
        var dontSort = [];
        $('#dataExamTable thead th').each( function () {
            if ( $(this).hasClass( 'no_sort' )) {
                dontSort.push( { "bSortable": false } );
            } else {
                dontSort.push( null );
            }
        });  
        
    
  
    
        
        
        $('#dataExamTable').dataTable({
            "sDom": "<'row-fluid table_top_bar'<'span12'<'to_hide_phone' f>>>t<'row-fluid control-group full top' <'span4 to_hide_tablet'l><'span8 pagination'p>>",
            "aaSorting": [[ 1, "asc" ]],
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bJQueryUI": false,
            "aoColumns": dontSort
                    
        });
        $(".chzn-select, .dataTables_length select").chosen({
            disable_search_threshold: 10
        
        });  
        $.extend( $.fn.dataTableExt.oStdClasses, {
            "s`": "dataTables_wrapper form-inline"
        } );
    
    }
</script> 