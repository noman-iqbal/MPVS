<%-- 
    Document   : AddResultView
    Created on : Mar 15, 2013, 4:44:43 PM
    Author     : noman
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row-fluid">
    <div class="span17">
        <div class="box color_13">
            <div class="title">
                <h4>
                    <span>${exam.title}</span>
                </h4>
            </div>
            <div class="content row-fluid">
                <div class="span7">
                    <h2>
                        <span>${exam.description}</span>
                    </h2>
                </div>
                <form action="" method="POST" name="submit_result" class="form-horizontal row-fluid">
                    <table class="responsive table table-striped table-bordered span24 " style="overflow: auto;">
                        <thead>    
                        <th>User id</th>
                        <th>Name</th>
                            <c:forEach items="${content}" var="con">
                            <th>${con.name}</th>
                            </c:forEach>
                        <th>Total</th>    
                        <th>Absent</th>    
                        </thead>
                        <tbody>
                            <c:forEach items="${listStd}" var="row">
                                <tr>
                                    <td>
                                        ${row.student.studentId.userName}
                                    </td>
                                    <td>
                                        ${row.student.studentId.firstName}
                                    </td>
                                    <c:forEach items="${row.rc}" var="rc">
                                        <td>
                                            <input type="text" rel="${rc.examContentsId.id}" name="student_id${row.student.studentId.id}-exam_conetnt_id${rc.examContentsId.id}"class="row_inputs_${row.student.studentId.id}" placeholder="${rc.examContentsId.percentage}" value="${rc.marks}" myId="${rc.id}" style="width: 60px;">
                                        </td>
                                    </c:forEach>
                                    <td>
                                        <span id="row_inputs_${row.student.studentId.id}_total">
                                        </span>
                                    </td>
                                    <td>
                                        <input type="checkbox" name="student_id_${row.student.studentId.id}_status"
                                               <c:if test="${row.student.status eq 'absent'}">
                                                   checked="ture"
                                               </c:if> 
                                               >
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="form-row control-group row-fluid ">
                        <label class="control-label ">Publish</label>
                        <div class="controls span7">
                            <div class="pull-left offset1">
                                <label class="radio off">
                                    <input type="radio" name="toggle" id="toggle2-off" value="off" 
                                           <c:if test="${result eq 'not_publish' or result eq 'draft'}">
                                               checked=""
                                           </c:if>>
                                </label>
                                <label class="radio on">
                                    <input type="radio" name="toggle" id="toggle2-on" value="on"
                                           <c:if test="${result eq 'publish'}">
                                               checked=""
                                           </c:if>
                                           >
                                </label>
                                <div class="toggle on">
                                    <div class="yes"> ON </div>
                                    <div class="switch"> </div>
                                    <div class="no"> OFF </div>
                                </div>
                            </div>
                        </div>
                    </div>



                    <div style="clear:both"></div>
                    <div class="form-actions row-fluid">
                        <div class="span7 offset2">
                            <button type="submit" class="btn btn-primary">Save changes</button>
                            <button type="reset" class="btn btn-secondary">Cancel</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<script>

    <c:forEach items="${content}" var="con">
    $('input[rel=${con.id}]').autoNumeric({
        vMax: '${con.percentage}'
    });
    </c:forEach>

        $(document).ready(function() {
            $('input[class^=row_inputs]').live("change", function() {
                sendData(this);
                totalListener(this);
            });
            $('input[class^=row_inputs]').change();
        });
        function sendData(obj) {
            var myVal = $(obj).val();
            if (myVal !== '') {
                console.log($(obj).attr('myId'));
                $.ajax({
                    type: 'POST',
                    url: 'ajcall',
                    data: {id: $(obj).attr('myId'), mark: myVal, func: 'saveRs'},
                    dataType: 'TEXT'
                }).done(function(n) {
                    console.log(n);
                });

            }
        }
        function totalListener(obj)
        {
            var class_name = $(obj).attr("class");
            var total = 0;
            $("." + class_name).each(function(index, element) {
                var current_val = parseFloat($(element).val());
                if (!isNaN(current_val) && current_val != "") {
                    total += current_val;
                }
            });
            $("#" + class_name + "_total").text(total);
        }
</script>