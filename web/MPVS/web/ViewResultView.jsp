<%-- 
    Document   : ViewResultView
    Created on : Apr 2, 2013, 11:09:59 PM
    Author     : noman
--%>

<%@page import="com.mpvs.db.ResultsContent"%>
<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.mpvs.db.ConnectionManager"%>
<%@page import="com.mpvs.db.ResultsStudent"%>
<%@page import="com.mpvs.db.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="box paint color_10">
    <div class="title">
        <div class="row-fluid">
            <h4> <i class=" icon-bar-chart"></i>Results</h4>
        </div>
    </div>
    <div class="content">
        <%
            Users u = (Users) request.getAttribute("user");
            for (ResultsStudent rs : u.getResultsStudentCollection()) {
                if (rs.getResultId().getStatus().contains("publish")) {
        %>
        <div class="accordion" id="accordion5">
            <div class="accordion-group">
                <div class="accordion-heading">
                    <a class="accordion-toggle collapsed" data-toggle="collapse"  href="#collapseOne<%=rs.getId()%>"> 
                        <h2><%=rs.getResultId().getExamId().getSbId().getName()%></h2>  <%=rs.getResultId().getExamId().getTitle()%>
                    </a> 
                </div>
                <div id="collapseOne<%=rs.getId()%>" class="accordion-body collapse" style="height: 0px; ">
                    <div class="accordion-inner"> 
                        <%=rs.getResultId().getExamId().getDescription()%>
                        <table>
                            <%
                                Session hob = ConnectionManager.getSession();
                                List<ResultsContent> rc = hob.createCriteria(ResultsContent.class).add(Restrictions.eq("resultsStudentId", rs)).add(Restrictions.in("examContentsId", rs.getResultId().getExamId().getExamContentsCollection())).list();
                                for (ResultsContent resultsContent : rc) {
                            %>
                            <tr>
                                <td><%=resultsContent.getExamContentsId().getName()%></td>
                                <td><%=resultsContent.getMarks()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </table>

                    </div>
                </div>
            </div>
        </div>
        <%}%>
    </div>
</div>

