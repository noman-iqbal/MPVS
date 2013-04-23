<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row-fluid">
    <div class="span12">
        <div class="box color_3">
            <div class="title">
                <h4>
                    Send Message
                </h4>
            </div>
            <div class="content">
                <form class="form-horizontal row-fluid" id="message_form" method="POST" action="message.jsp">
                    <div class="control-group form-row row-fluid">
                        <label class="control-label span3">To</label>
                        <div class="controls span7">
                            <select data-placeholder="Choose a Name" name="user" multiple="" id="programList">
                                <c:forEach var="u"  items="${op}">
                                    <option value="${u.id}"
                                            <c:if test="${uid eq u.id}">
                                                selected=""
                                            </c:if>
                                            >${u.firstName}</option>
                                </c:forEach>

                            </select>
                        </div>
                    </div>
                    <div class="control-group form-row row-fluid">
                        <label class="control-label span3">Subject
                            <span class="help-block">*</span></label>
                        <div class="controls span7">
                            <input id="normal-field" class="row-fluid" type="text" name="subject">
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span3" for="default-textarea">Message   <span class="help-block">*</span></label>
                        <div class="controls span7">
                            <textarea rows="3" class="row-fluid" id="default-textarea" name="message"></textarea>
                        </div>
                    </div>
                    <div style="clear: both"></div>
                    <div class="row-fluid pagination-centered">
                        <span class="error"> 
                            <%
                                if (request.getAttribute("message") != null) {
                            %>
                            <div class="error form-row control-group row-fluid span7" >
                                <%=request.getAttribute("message")%>
                            </div>    
                            <%
                                }
                            %>   
                        </span>
                    </div>

                    <div class="form-actions row-fluid">

                        <div class="span7 offset3">
                            <button class="btn btn-primary" type="button" id="submitBt">Save changes</button>
                            <button class="btn btn-secondary" type="reset">Cancel</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {


        $('#submitBt').click(function() {
            if (isSelected('#programList'))
                $('#message_form').submit();
            else
                alert('Please chose some one To send Message');
        });

        $('#message_form').validate({
            rules: {
                user: 'required',
                subject: 'required',
                message: 'required'
            },
            messages: {
                user: 'User cant be empty.',
                subject: 'Subject cant be empty.',
                message: 'Message cant be empty.'
            }
        });

        $("#programList").chosen({
            disable_search_threshold: 10
        });

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
</script>