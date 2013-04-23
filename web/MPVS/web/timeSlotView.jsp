<%-- 
    Document   : timeSlotView
    Created on : Apr 5, 2013, 9:15:42 PM
    Author     : noman
--%>

<%@taglib   uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<select name="hold_time" class="chzn-select form-row control-group row-fluid">
    <c:forEach items="${slots}" var="s">
        <option value="${s.id}"
                <c:if test="${s.id eq exam.heldId.id}">
                    selected=""
                </c:if>
                >${s.dayName} <fmt:formatDate value="${s.startTime}" pattern="hh:mm a"/></option>
    </c:forEach>
</select>
