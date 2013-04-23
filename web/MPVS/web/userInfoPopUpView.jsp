<%-- 
    Document   : userInfoPopUpView
    Created on : Mar 7, 2013, 10:09:43 PM
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
                User Name 
            </TD>
            <TD>
                ${u.userName}
            </TD>
            <TD>
                <span class="icon">
                    <img src="${u.profilePicUrl}"  style="width: 75px;height: 75;"/>

                </span>
              
            </TD>

        </TR>
        <TR>
            <TD>
                Name 
            </TD>
            <TD>
                ${u.firstName} ${u.lastName}
            </TD>

        </TR>
        <TR>
            <TD>
                Email
            </TD>
            <TD>
                ${u.email}
            </TD>

        </TR>
        <TR>
            <TD>
                Subjects 
            </TD>
            <TD>
                <c:forEach items="${sb}" var="s">
                    ${s.subjectId.name}<br>
                </c:forEach>
            </TD>

        </TR>

        <TR>
            <TD>
                Program 
            </TD>
            <TD>
                ${u.program.name}
            </TD>
        </TR>
        <TR>
            <TD>
                Type
            </TD>
            <TD>
                ${u.type}
            </TD>
        </TR>
        <TR>
            <TD>
                Session  
            </TD>
            <TD>
                ${u.session}
            </TD>
        </TR>    
    </TABLE>
</div>
<div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
</div>
