<%-- 
    Document   : examInfoPopUpView
    Created on : Mar 14, 2013, 8:23:57 PM
    Author     : noman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Exam</h3>
</div>
<div class="modal-body">


    <TABLE>
        <TR>
            <TD>
                TITLE 
            </TD>
            <TD>
                ${exam.title}
            </TD>

        </TR>
        <TR>
            <TD>
                Decs
            </TD>
            <TD>
                ${exam.description}
            </TD>

        </TR>
        <TR>
            <TD>
                Subject 
            </TD>
            <TD>
                ${exam.sbId.name}
            </TD>

        </TR>

        <TR>
            <TD>
                Creater 
            </TD>
            <TD>
                ${exam.userCreaterId.firstName}
            </TD>

        </TR>

        <TR>
            <TD>
                Start time 
            </TD>
            <TD>
                <fmt:formatDate value="${exam.startDate}" pattern="MMMMM,EEE dd   hh:m a"/>                
            </TD>
              <TD>
                End time 
            </TD>
            <TD>
                ${exam.heldId.dayName} 
                <fmt:formatDate value="${exam.heldId.endTime}" pattern="hh:mm a"/>                
            </TD>

        </TR>
        <TR>
            <TD>
                Status
            </TD>
            <TD>
                ${exam.status}
            </TD>

        </TR>


        <TR>
            <TD>
                Member
            </TD>
            <TD>
                <c:forEach items="${member}" var="mem">
                    ${mem.memberId.firstName}
                    <BR>
                </c:forEach>
            </TD>

        </TR>    

        <TR>
            <TD>
                Name  
            </TD>
            <TD>
                Weight
            </TD>
        </TR>    
        <c:forEach items="${content}" var="cnt">
            <TR>
                <TD>
                    ${cnt.name}
                </TD>
                <TD>
                    ${cnt.percentage}
                </TD>
            </TR>    
        </c:forEach>
    </TABLE>
</div>
<div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    <button class="btn btn-primary" id="onResult" >Add Result</button>
</div>

<script type="text/javascript">
    $('#onResult').click(function(){
        $(location).attr("href","result.jsp?id="+${exam.id});    
    });
</script>
