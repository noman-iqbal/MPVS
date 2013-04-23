
<%-- 
    Document   : homeView
    Created on : Mar 3, 2013, 3:55:42 PM
    Author     : noman
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${userInfo.type eq 'super_admin'}">
    <%@include file="homeMemberView.jsp" %>
</c:if>
<c:if test="${userInfo.type eq 'member'}">
    <%@include file="homeMemberView.jsp" %>
</c:if>
<c:if test="${userInfo.type eq 'student'}">
    <%@include file="homeStudentView.jsp" %>
</c:if>
<c:if test="${userInfo.type eq 'admin'}">
    <%@include file="homeAdminView.jsp" %>
</c:if>
