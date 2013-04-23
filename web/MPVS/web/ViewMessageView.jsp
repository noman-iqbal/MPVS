<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="box ">
    <div class="title">
        <h4> <span>Messages</span><a class="btn btn-large" id="delets">Delete selected</a></h4>
    </div>

    <div class="content top">


        <table id="datatable_example" class="responsive table table-striped table-bordered" style="width:100%;margin-bottom:0; ">
            <thead>
                <tr>
                    <th class="jv no_sort">
                        <label class="checkbox ">
                            <input id="allCheck" type="checkbox">
                        </label>
                    </th>
                    <th class="to_hide_phone">From</th>
                    <th class="to_hide_phone">Subject</th>
                    <th class="ms no_sort ">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${list}">
                    <tr>
                        <td>
                            <label class="checkbox" ><input type="checkbox"  class="checkboss" id="${l.requestId.id}" ></label>
                        </td>
                        <td>
                            ${l.requestId.userId.firstName}
                        </td>
                        <td>
                            ${l.requestId.subject}
                        </td>
                        <td class="ms">
                            <div class="btn-group1">
                                <a id="${l.requestId.userId.id}" class="btn btn-small record_edit"  rel="tooltip" data-placement="left" data-original-title=" Replay ">
                                    <i class="gicon-edit"></i>
                                </a>
                                <a id="${l.requestId.id}"  class="btn btn-small record_view" rel="tooltip" data-placement="top" data-original-title="View">
                                    <i class="gicon-eye-open"></i>
                                </a> 
                                <a id="${l.requestId.id}"   class="btn  btn-small record_del" rel="tooltip" data-placement="bottom" data-original-title="Remove"><i class="gicon-remove "></i></a> 
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
    </div>
</div>
<!-- End box --> 
<script type="text/javascript">
    // Datatables
    $(document).ready(function() {
        $('#delets').click(function(){
            alert()
        });
        
        $('.btn-group1 a.record_edit').click(function(){
            
            $(location).attr("href","message.jsp?uid="+this.id);
            console.log($(this).parent());    
            console.log(this.id+'from edit');
        });
        $('.btn-group1 a.record_view').click(function(){
            $.ajax({
                type:'POST',url:'ajcall',
                data:{id:this.id,func:'getMessagePopUp'},
                dataType:'HTML'
            }).done(function(msg){
                $('#myModal').html(msg);
                $('#myModal').modal('show');
                
            });
            console.log($(this).parents('tr'));
            console.log(this.id+' from View');
        });
        $('.btn-group1 a.record_del').click(function(){
            var $this=this;
            $.getJSON("ajcall",{id:this.id,func:'delMes'},  function(data){
                $($this).parents('tr').remove();
                alert(data.message);
            });
            console.log(this.id+'from Delet');
            
        });
        
        $('#allCheck').change(function() {
            console.log($(this).is('checked'));
            console.log($(this).parent().is('.checked'));
            if(!$(this).parent().is('.checked'))
                $('.checker span:not(.checked) input:checkbox:not(#allCheck)').click();
            else
                $('.checker span.checked input:checkbox:not(#allCheck)').click();
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