<%-- 
    Document   : resultInfoPopUpView
    Created on : Apr 1, 2013, 9:31:34 PM
    Author     : noman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Result</h3>
</div>
<div class="modal-body">
    <TABLE>
        <TR>
            <TD>
                TITLE 
            </TD>
            <TD>
                ${rs.resultId.examId.title}
            </TD>

        </TR>
        <TR>
            <TD>
                Decs
            </TD>
            <TD>
                ${rs.resultId.examId.description}
            </TD>

        </TR>
        <TR>
            <TD>
                Subject 
            </TD>
            <TD>
                ${rs.resultId.examId.sbId.name}
            </TD>

        </TR>

        <TR>
            <TD>
                Creater 
            </TD>
            <TD>
                ${rs.resultId.examId.userCreaterId.firstName}
            </TD>

        </TR>

        <TR>
            <TD>
                Start time 
            </TD>
            <TD>
                ${rs.resultId.examId.heldId.dayName} ${rs.resultId.examId.heldId.startTime}
            </TD>
            <TD>
                End time 
            </TD>
            <TD>
                ${rs.resultId.examId.heldId.dayName}  ${rs.resultId.examId.heldId.endTime}
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
        <c:forEach items="${rc}" var="cnt">
            <TR>
                <TD>
                    ${cnt.examContentsId.name}
                </TD>
                <TD>
                    ${cnt.marks}
                </TD>
            </TR>    
        </c:forEach>
    </TABLE>
</div>
<div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>

</div>
