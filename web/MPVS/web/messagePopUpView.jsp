<%-- 
    Document   : messagePopUpView
    Created on : Mar 28, 2013, 8:47:18 PM
    Author     : noman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Message</h3>
</div>
<div class="modal-body">
    <TABLE>
        <TR>
            <TD>
                From 
            </TD>
            <TD>
                ${r.userId.firstName}
            </TD>

        </TR>
        <TR>
            <TD>
                Subject
            </TD>
            <TD>
                ${r.subject}
            </TD>

        </TR>
        <TR>
            <TD>
                Message 
            </TD>
            <TD>
                ${r.message}
            </TD>

        </TR>
    </TABLE>
</div>
<div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    <button class="btn btn-primary" id="onResult" >Replay</button>
</div>

<script type="text/javascript">
    $('#onResult').click(function(){
        $(location).attr("href","message.jsp?uid="+${r.userId.id});    
    });
</script>
