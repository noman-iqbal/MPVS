<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row-fluid">
    <div class="span17">
        <div class="box color_13">
            <div class="title">
                <h4>
                    <span>${typeSt}</span>
                </h4>
            </div>
            <div class="content">
                <form class="form-horizontal row-fluid"  action="${callType}" method="POST" id="addUserForm" enctype="multipart/form-data" autocomplete="off">
                    <input name="type" type="hidden" value="${typeSt}"/>
                    <input name="user_id" type="hidden" value="${user.id}"/>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">University Id<span class="help-block">*</span></label>
                        <div class="controls span7">
                            <input type="text" id="normal-field uniId" name="uniId" value="${user.userName}" class="row-fluid">
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2">Password</label>
                        <div class="controls span7 ">
                            <div class="input-prepend row-fluid"> <span class="add-on"><i class="icon-lock"></i></span>
                                <input class="row-fluid" type="password" id="password" name="password" placeholder="min 5 characters">
                            </div>
                        </div>
                    </div>
                    <div class="control-group row-fluid">
                        <label class="control-label span2">Confirm Password</label>
                        <div class="controls span7">
                            <div class="input-prepend row-fluid"> <span class="add-on"><i class="icon-lock"></i></span>
                                <input class="row-fluid" type="password" id="confirm_password" placeholder="confirm password" name="confirm_password">
                            </div>
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">First Name<span class="help-block">*</span></label>
                        <div class="controls span7">
                            <input type="text" id="normal-field fName" class="row-fluid" value="${user.firstName}" name="fName">
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Last Name</label>
                        <div class="controls span7">
                            <input type="text" id="normal-field lName" class="row-fluid"value="${user.lastName}" name="lName">
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Email<span class="help-block">*</span></label>
                        <div class="controls span7">
                            <input type="text" id="normal-field email"value="${user.email}" class="row-fluid"name="email">
                        </div>
                    </div>


                    <c:if test="${typeSt eq 'student' or typeSt eq 'member'}">

                        <c:if test="${typeSt eq 'student'}">
                            <div class="form-row control-group row-fluid">
                                <label class="control-label span2" for="normal-field">Program<span class="help-block">*</span></label>
                                <div class="controls span7">
                                    <select data-placeholder="Choose a Subjects" name="program" id="programList">
                                        <c:forEach var="program"  items="${programList}">
                                            <option value="${program.id}"
                                                    <c:if test="${program.id eq user.program.id}">
                                                        selected
                                                    </c:if>>${program.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>



                            <div class="form-row control-group row-fluid">
                                <label class="control-label span2" for="normal-field">Session<span class="help-block">*</span></label>
                                <div class="controls span2">
                                    <select data-placeholder="Choose User type"  class="chzn-select" name="session_month" id="userType default-select">
                                        <option value="fall" <c:if test="${fn:contains(user.session, 'fall')}">selected</c:if> >Fall</option>
                                        <option value="summer" <c:if test="${fn:contains(user.session, 'summer')}">selected</c:if>>Summer</option>
                                        <option value="spring" <c:if test="${fn:contains(user.session, 'spring')}">selected</c:if>>Spring</option>
                                        <option value="winter"<c:if test="${fn:contains(user.session, 'winter')}">selected</c:if>>Winter</option>
                                    </select>
                                    <!--<input type="text" id="normal-field" class="row-fluid"name="session">-->
                                </div>
                                <label class="control-label span2" for="normal-field">Year<span class="help-block">*</span></label>
                                <div class="controls date span2">
                                    <input type="text" id="datepicker" value="<c:if test="${fn:replace(fn:substringAfter(user.session, '-'), '-', ' ')}"></c:if>" name="session_year"  class="row-fluid">
                                </div>
                            </div>
                        </c:if>

                        <div class="form-row control-group row-fluid">
                            <label class="control-label span2" for="chzn-select">Subject<span class="help-block">*</span></label>
                            <div class="controls span7">
                                <select data-placeholder="Choose a Subjects" name="subjectList" class="chzn-select" multiple="">
                                    <c:forEach var="item"  items="${subList}">
                                        <option value="${item.id}"
                                                <c:forEach items="${user_sb_list}" var="sb">
                                                    <c:if test="${sb.subjectId.id eq item.id}">
                                                        selected
                                                    </c:if>
                                                </c:forEach>
                                                >${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="search-input">Image upload</label>
                        <div class="controls span7">
                            <div class="input-append">
                                <input type="file" class="spa1n6 fileinput" id="search-input" name="image">
                            </div>
                        </div>
                    </div>
                    <div class="form-actions row-fluid">
                        <%
                            if (request.getAttribute("error") != null) {
                        %>
                        <div class="error form-row control-group row-fluid span7" >
                            <%=request.getAttribute("error")%>
                        </div>    
                        <%
                            }
                        %>      
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
<script type="text/javascript">
    $(document).ready(function(){
        $("#addUserForm").validate({
            rules: {
                uniId: {
                    required: true
                },
                password:{
                    required:true
                },
                confirm_password:{
                    required:true,
                    equalTo:'#password'
                },
                email:{
                    required:false,
                    email:true
                },
                userType: "required"
            
            },
            messages: {
                uniId:{required:"Please provide an ID"},
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                confirm_password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long",
                    equalTo: "Please enter the same password as above"
                },
                email: "Please enter a valid email address"
                ,
                userType: "Please select Type"
            }
        });
        
        
                
        $("#programList").chosen({
            allow_single_deselect:true
        });
                
        $(".chzn-select").chosen({
            disable_search_threshold: 10
        });     
        $('#datepicker').datepicker({
            format: " yyyy",
            viewMode: "years", 
            minViewMode: "years"
        });
        
        
    });
</script>