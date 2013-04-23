<%-- 
    Document   : AddExamView
    Created on : Mar 12, 2013, 1:49:03 AM
    Author     : noman
--%>
<link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript"
        src="js/plugins/bootstrap-datetimepicker.min.js">
</script>

<%@page import="com.mpvs.db.Users"%>
<%@page import="com.mpvs.db.ExamMember"%>
<%@page import="com.mpvs.db.ExamMember"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="row-fluid">
    <div class="span17">
        <div class="box color_21">
            <div class="title">
                <h4>
                    <span>${title}</span>
                </h4>
            </div>
            <div class="content right">
                <form class="form-horizontal row-fluid"  action="AddExam.jsp" method="POST" id="addExamForm"  autocomplete="off">
                    <input type="hidden" name="createrId" value="${userInfo.id}"/>
                    <input type="hidden" name="examId" value="${exam.id}"/>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Title<span class="help-block">*</span></label>
                        <div class="controls span7">
                            <input type="text" id="normal-field qTitle" name="qTitle" value="${exam.title}" class="row-fluid">
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Description<span class="help-block">*</span></label>
                        <div class="controls span7">
                            <textarea rows="3" class="row-fluid" id="qDec" name="qDec"  placeholder="Question Description..">${exam.description}</textarea>
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field"></label>
                        <div class=" controls span7 ">
                            <table class="table">
                                <thead>
                                <th>Question / Feature</th>
                                <th>weight</th>
                                <th>Action</th>
                                </thead>
                                <tbody id="tbdy">
                                    <c:if test="${fn:length(content)>0}" >
                                        <c:forEach var="cn" items="${content}" varStatus="i">
                                            <tr>
                                                <td><input class="wName" name="names" type="text" value="${cn.name}"></td>
                                                <td><input type="text" class="span4 waight" name="waights" value="${cn.percentage}" placeholder="100"></td>
                                                <td class="ms">
                                                    <div class="btn-group1">

                                                        <c:if test="${i.index>1}" >
                                                            <a class="btn  btn-small record_del" rel="tooltip" data-placement="bottom" data-original-title="Remove"><i class="gicon-remove "></i></a> 
                                                            </c:if>                                                    


                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${fn:length(content)<=0}" >
                                        <tr>
                                            <td><input class="wName" name="names" type="text"></td>
                                            <td><input type="text" class="span4 waight" name="waights" placeholder="100"></td> 
                                            <td class="ms">
                                                <div class="btn-group1">
                                                    <!--<a class="btn  btn-small record_del" rel="tooltip" data-placement="bottom" data-original-title="Remove"><i class="gicon-remove "></i></a>--> 
                                                </div>
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                            <button type="button" class="btn color_15" id="addMore">Add More</button>
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="chzn-select">Subject   <span class="help-block">*</span></label>
                        <div class="controls span7">
                            <select data-placeholder="Choose a Subjects" id="subject_select" name="subjectList" class="chzn-select-deselect"  tabindex="1" >
                                <c:forEach var="item"  items="${subList}">
                                    <option value="${item.id}"
                                            <c:if test="${item.id eq exam.sbId.id}"> 
                                                selected="true"
                                            </c:if>
                                            >${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="chzn-select">Member   <span class="help-block">*</span></label>
                        <div class="controls span7 ">
                            <select data-placeholder="Choose a Member" name="memberList" id="membersList" class="chzn-select" multiple="multiple" tabindex="3">
                                <c:forEach var="item"  items="${memList}">

                                    <option value="${item.id}"
                                            <%
                                                if (request.getAttribute("member") != null) {
                                                    List<ExamMember> members = (List<ExamMember>) request.getAttribute("member");
                                                    Users em = (Users) pageContext.getAttribute("item");
                                                    if (members.contains(em)) {
                                                        out.print("selected");
                                                    }
                                                }
                                            %>
                                            >
                                        ${item.firstName}</option>
                                    </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Held Date   <span class="help-block">*</span></label>
                        <div class="controls date span7">
                            <input  type="text" id="datetimepicker" value="<fmt:formatDate value="${exam.startDate}" pattern="MMMMM yyyy"/>"  name="exam_date" placeholder="Months Year"  style="width: 150px">
                            <input type="text"  name="exam_week" id="exam_week" value="<fmt:formatDate value="${exam.startDate}" pattern="W"/>" placeholder="Week 1-4"  style="width: 100px">
                            <div id="selecter" class="input-append">
                                <input type="hidden" value="${exam.id}" id="exam_id" />
                            </div>
                        </div>
                    </div>
                    <div style="clear: both"></div>
                    <div class="row-fluid pagination-centered">
                        <span class="error"> 
                            <input type="hidden" name="sum">
                            <%
                                if (request.getAttribute("error") != null) {
                            %>
                            <div class="error form-row control-group row-fluid span7" >
                                <%=request.getAttribute("error")%>
                            </div>    
                            <%
                                }
                            %>   
                        </span>
                    </div>

                    <div class="form-actions row-fluid">
                        <div class="span7 offset2">
                            <button type="button" id="onSaveClicked" class="btn btn-primary">Save changes</button>
                            <button type="reset" class="btn btn-secondary">Cancel</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var row = "<tr><td><input class=\"wName\"  name=\"names\" type=\"text\"></td><td><input type=\"text\"  name=\"waights\" class=\"span4 waight\" placeholder=\"{0}\"></td><td class=\"ms\"><div class=\"btn-group1\"><a class=\"btn  btn-small record_del\" rel=\"tooltip\" data-placement=\"bottom\" data-original-title=\"Remove\"><i class=\"gicon-remove \"></i></a></div></td></tr>";
    $(document).ready(function() {


        $('#onSaveClicked').click(function() {
            var totalSum = getSum();
            if (totalSum < 100) {
                alert('Sum must be 100');
            } else {
                if (isSelected('#membersList'))
                {
                    $('#addExamForm').submit();
                } else {
                    alert("Please chose a member");
                }



            }
        });
        $('#addExamForm').validate({
            rules: {
                qTitle: 'required'
                        , qDec: 'required'
                        , subjectList: 'required'
                        , memberList: 'required'
                        , exam_date: 'required'
                        , exam_week: 'required'
            },
            messages: {
                qTitle: 'Title cant be empty.'
                        , qDec: 'please add some dec at least'
                        , subjectList: 'witch subject haa!'
                        , memberList: 'No one wants to take exame ?'
                        , heldDate: 'yapeee no date for paper!'
                        , exam_date: 'Please Select Month And Year'
                        , exam_week: 'Please Select Month And Week'
            }
        });
        $('#subject_select').change(function() {
            getSlote();
        });
        getSlote();


        $('#datetimepicker').datepicker({
            format: "MM yyyy",
            viewMode: "years",
            minViewMode: "months",
            startDate: new Date()

        }).on('changeDate', function(date) {
            console.log();
            var start = date.date.getMonth();
            var current = new Date().getMonth();
            if (start == current)
            {

                console.log(weekAndDay());

                $('#exam_week').autoNumeric({
                    vMax: '5',
                    vMin: weekAndDay()
                });
            }
        });
        $('#exam_week').autoNumeric({
            vMax: '5',
            vMin: '1'
        });

        $('.wName').rules('add', {
            required: true,
            messages: {
                required: "Names cant be empty"
            }
        });
        $('.waight').rules('add', {
            required: true,
            messages: {
                required: "waight cant be empty"
            }
        });



        $('.waight').autoNumeric({
            vMax: '100'
        });




        $('#addMore').click(function() {
            var sum = getSum();


            console.log(sum);
            var name_value = $('.wName:last').val();


            if ($.trim(name_value) == "") {
                alert("Please enter some value in Name");
                return;
            }
            if (sum >= 100 || isNaN(sum)) {
                if (isNaN(sum))
                    alert("Please enter some value in Weight");
                if (sum >= 100)
                    alert("you are on the edge");
            } else {
                var val = 100 - sum;
                console.log(val);
                var row1 = $('#tbdy').append($.validator.format(row, val));
                $(row1).find('.waight').autoNumeric({
                    vMax: val
                });

                $(".waight").change(function() {
                    var sum = getSum1();
                    $(".waight:last").attr("placeholder", 100 - sum);
                });


                button();
                $('.wName').rules('add', {
                    required: true, messages: {
                        required: "Names cant be empty"
                    }
                });
                $('.waight').rules('add', {
                    required: true, messages: {
                        required: "waight cant be empty"
                    }
                });
            }
        });
        $('#qDec').autogrow();
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({
            allow_single_deselect: true
        });


        button();

    });
    function isSelected(id) {
        var total = 0;
        $(id).find(":selected").each(function() {
            total++;
        });
        if (total >= 1)
            return true;
        else
            false;
    }
    function weekAndDay() {

        var date = new Date,
                days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday',
            'Thursday', 'Friday', 'Saturday'],
                prefixes = ['1', '2', '3', '4', '5'];

        return prefixes[0 | date.getDate() / 7];

    }
    function getSlote() {
        $.ajax({
            type: 'GET',
            url: 'ajcall',
            data: {id: $('#subject_select').attr("value"), exam_id: $('#exam_id').attr("value"), func: 'getSlots'},
            dataType: 'HTML'
        }).done(function(n) {
            $('#selecter').html(n);
        });
    }
    function getSum() {
        var sum = 0;
        $(".waight").each(function(i, n) {
            sum += parseFloat($(n).val());
        });
        return sum;
    }
    function getSum1() {
        var sum = 0;
        $(".waight").each(function(i, n) {
            var v = parseFloat($(n).val());
            if (!isNaN(v))
                sum += v;
        });
        return sum;
    }
    function button() {
        $('.btn-group1 a.record_del').click(function() {
            var $this = this;
            $($this).parents('tr').remove();
        });
    }
</script>