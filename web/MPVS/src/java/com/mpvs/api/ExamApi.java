package com.mpvs.api;

import com.google.gson.Gson;
import com.mpvs.db.ConnectionManager;
import com.mpvs.db.ExamContents;
import com.mpvs.db.ExamMember;
import com.mpvs.db.Exams;
import com.mpvs.db.Requests;
import com.mpvs.db.RequestsTo;
import com.mpvs.db.Results;
import com.mpvs.db.ResultsContent;
import com.mpvs.db.ResultsStudent;
import com.mpvs.db.Users;
import com.mpvs.db.UsersSubject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author noman
 */
@WebServlet(name = "ExamApi", urlPatterns = {"/examApi"})
public class ExamApi extends HttpServlet {

    private Session hob;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject resObj = new JSONObject();
        try {
            hob = ConnectionManager.getSession();
            Gson g = new Gson();
            String userId = request.getParameter("user_id");
            String functionName = request.getParameter("function_name");

            Users u = (Users) hob.get(Users.class, Integer.parseInt(userId));
            if (u == null) {
                out.println("cant find user");
                return;
            }


            switch (functionName) {
                case "getMessagesByid":
                    resObj.put("data", getAllMessages(request, u));
                    break;
                case "getMessages":
                    resObj.put("data", getAllMessages(request, u));
                    break;
                case "getAllResult":
                    resObj.put("data", getAllResult(request, u));
                    break;
                case "getResult":

                    resObj.put("data", getResult(request, u));
                    break;

                case "getAllExam":
                    List<ExamMember> emList = hob.createCriteria(ExamMember.class).add(Restrictions.eq("memberId", u)).list();
                    JSONArray jsonExamList = new JSONArray();
//                    out.println(emList.size());
                    for (ExamMember examMember : emList) {
                        JSONObject obj = new JSONObject();
                        obj.put("exam_id", examMember.getExamId().getId());
                        obj.put("exam_title", examMember.getExamId().getTitle());
                        obj.put("exam_description", examMember.getExamId().getDescription());
                        obj.put("exam_status", examMember.getExamId().getStatus());
                        obj.put("exam_subject_name", examMember.getExamId().getSbId().getName());
                        obj.put("exam_get_start_date", examMember.getExamId().getHeldId().getStartTime().toGMTString());
                        obj.put("exam_get_end_date", examMember.getExamId().getHeldId().getEndTime().toGMTString());
                        JSONArray exCList = new JSONArray();
                        for (ExamContents examContents : examMember.getExamId().getExamContentsCollection()) {
                            JSONObject ec = new JSONObject();
                            ec.put("exam_content_name", examContents.getName());
                            ec.put("exam_content_percentage", examContents.getPercentage());
                            ec.put("exam_content_id", examContents.getId());
                            exCList.add(ec);
                        }
                        obj.put("exam_contents", exCList);
                        List<UsersSubject> students = hob.createCriteria(UsersSubject.class).add(Restrictions.eq("subjectId", examMember.getExamId().getSbId())).createCriteria("usersStudentId").add(Restrictions.ilike("type", "student")).list();
                        JSONArray exSList = new JSONArray();
                        for (UsersSubject us : students) {
                            JSONObject ec = new JSONObject();
                            ec.put("s_id", us.getUsersStudentId().getId());
                            ec.put("s_name", us.getUsersStudentId().getFirstName() + " " + us.getUsersStudentId().getLastName());
                            ec.put("s_user_name", us.getUsersStudentId().getUserName());
                            exSList.add(ec);
                        }
                        obj.put("students", exSList);
                        jsonExamList.add(obj);
                    }
                    resObj.put("data", jsonExamList);
                    break;
                case "getExam":
                    String examID = request.getParameter("exam_ids");
                    Exams exam = (Exams) hob.get(Exams.class, Integer.parseInt(examID));
                    if (exam != null) {
                        JSONObject obj = new JSONObject();
                        obj.put("exam_id", exam.getId());
                        obj.put("exam_title", exam.getTitle());
                        obj.put("exam_description", exam.getDescription());
                        obj.put("exam_status", exam.getStatus());
                        obj.put("exam_subject_name", exam.getSbId().getName());
                        obj.put("exam_get_start_date", exam.getHeldId().getStartTime().toGMTString());
                        obj.put("exam_get_end_date", exam.getHeldId().getEndTime().toGMTString());
                        JSONArray exCList = new JSONArray();
                        for (ExamContents examContents : exam.getExamContentsCollection()) {
                            JSONObject ec = new JSONObject();
                            ec.put("exam_content_name", examContents.getName());
                            ec.put("exam_content_percentage", examContents.getPercentage());
                            ec.put("exam_content_id", examContents.getId());
                            exCList.add(ec);
                        }
                        obj.put("exam_contents", exCList);
                        List<UsersSubject> students = hob.createCriteria(UsersSubject.class).add(Restrictions.eq("subjectId", exam.getSbId())).createCriteria("usersStudentId").add(Restrictions.ilike("type", "student")).list();
                        JSONArray exSList = new JSONArray();
                        for (UsersSubject us : students) {
                            JSONObject ec = new JSONObject();
                            ec.put("s_id", us.getUsersStudentId().getId());
                            ec.put("s_name", us.getUsersStudentId().getFirstName() + " " + us.getUsersStudentId().getLastName());
                            ec.put("s_user_name", us.getUsersStudentId().getUserName());
                            exSList.add(ec);
                        }
                        obj.put("students", exSList);
                        JSONArray temp = new JSONArray();
                        temp.add(obj);
                        resObj.put("data", temp);
                    } else {
                        resObj.put("data", "[]");
                    }
                    break;

                case "upload":
                    String cId = request.getParameter("content_id");
                    String marks = request.getParameter("result");
                    String sId = request.getParameter("student_id");
                    String examId = request.getParameter("exam_id");
                    Transaction tr = hob.getTransaction();
                    tr.begin();

                    Exams examNew = (Exams) hob.get(Exams.class, Integer.parseInt(examId));
                    if (examNew != null) {
                        List<Results> resultList = hob.createCriteria(Results.class).add(Restrictions.eq("examId", examNew)).list();
                        Results result;
                        if (resultList.isEmpty()) {
                            result = new Results();
                            result.setExamId(examNew);
                            result.setCreatedAt(new Date());
                            result.setUpdatedAt(new Date());
                            result.setStatus("sync");
                        } else {
                            result = resultList.get(0);
                            result.setUpdatedAt(new Date());
                            result.setStatus("sync");
                        }
                        hob.saveOrUpdate(result);
                        
//                        List<ResultMember> rmList = hob.createCriteria(ResultMember.class).add(Restrictions.eq("memberId", u)).add(Restrictions.eq("resultId", result)).list();
//                        ResultMember rm;
//                        if (rmList.isEmpty()) {
//                            rm = new ResultMember();
//                            rm.setMemberId(u);
//                            rm.setResultId(result);
//                        } else {
//                            rm = rmList.get(0);
//                        }
//                        hob.saveOrUpdate(rm);


                        Users student = (Users) hob.get(Users.class, Integer.parseInt(sId));
                        List<ResultsStudent> rSList = hob.createCriteria(ResultsStudent.class).add(Restrictions.eq("resultId", result)).add(Restrictions.eq("studentId", student)).list();
                        ResultsStudent rs;
                        if (rSList.isEmpty()) {
                            rs = new ResultsStudent();
                            rs.setResultId(result);
                            rs.setStudentId(student);
                            rs.setStatus("sync");
                            rs.setCreatedAt(new Date());
                            rs.setUpdatedAt(new Date());
                        } else {
                            rs = rSList.get(0);
                            rs.setStatus("sync");
                            rs.setUpdatedAt(new Date());
                        }
                        hob.saveOrUpdate(rs);
                        ExamContents ec = (ExamContents) hob.get(ExamContents.class, Integer.parseInt(cId));

                        List<ResultsContent> rcList = hob.createCriteria(ResultsContent.class).add(Restrictions.eq("resultsStudentId", rs)).add(Restrictions.eq("examContentsId", ec)).list();
                        ResultsContent rc;
                        if (rcList.isEmpty()) {
                            rc = new ResultsContent();
                            rc.setResultsStudentId(rs);
                            rc.setCreatedAt(new Date());
                            rc.setUpdatedAt(new Date());
                            rc.setMarks(Float.parseFloat(marks));
                            rc.setStatus("sync");
                            rc.setExamContentsId(ec);
                        } else {
                            rc = rcList.get(0);
                            rc.setMarks(Float.parseFloat(marks));
                            rc.setUpdatedAt(new Date());
                            rc.setStatus("sync");
                        }
                        hob.saveOrUpdate(rc);
                    }
                    tr.commit();
                    break;
            }

            resObj.put("isError", false);
        } catch (Exception ex) {
            resObj.put("isError", true);
            resObj.put("message", ex.getMessage());

        }

        out.print(resObj);
        out.close();





    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private JSONArray getAllResult(HttpServletRequest request, Users u) {
        List<ResultsStudent> sList = hob.createCriteria(ResultsStudent.class).add(Restrictions.eq("studentId", u)).list();
        JSONArray resultList = new JSONArray();
        for (ResultsStudent rs : sList) {
            JSONObject anRes = new JSONObject();
            anRes.put("exam_title", rs.getResultId().getExamId().getTitle());
            anRes.put("exam_dec", rs.getResultId().getExamId().getDescription());
            anRes.put("exam_id", rs.getResultId().getExamId().getId());
            JSONArray conList = new JSONArray();
            Collection<ResultsContent> rcc = rs.getResultsContentCollection();
            for (ResultsContent rc : rcc) {
                JSONObject objR = new JSONObject();
                objR.put("marks", rc.getMarks());
                objR.put("title", rc.getExamContentsId().getName());
                conList.add(objR);
            }
            anRes.put("contents", conList);
            resultList.add(anRes);
        }
        return resultList;
    }

    private JSONArray getAllMessages(HttpServletRequest request, Users u) {
        String mId = request.getParameter("m_id");
        if (mId != null && !mId.isEmpty()) {
            JSONArray resultList = new JSONArray();
            Requests r = (Requests) hob.get(Requests.class, Integer.parseInt(mId));
            JSONObject anRes = new JSONObject();
            if (r != null) {
                anRes.put("from_id", r.getUserId().getId());
                anRes.put("from_name", r.getUserId().getFirstName());
                anRes.put("subject", r.getSubject());
                anRes.put("date", r.getSendDate().toString());
                anRes.put("message", r.getMessage());
            }

            resultList.add(anRes);
            return resultList;
        } else {
            List<RequestsTo> sList = hob.createCriteria(RequestsTo.class).add(Restrictions.eq("toId", u)).list();
            JSONArray resultList = new JSONArray();
            for (RequestsTo rs : sList) {
                JSONObject anRes = new JSONObject();
                anRes.put("from_id", rs.getRequestId().getUserId().getId());
                anRes.put("from_name", rs.getRequestId().getUserId().getFirstName());
                anRes.put("date", rs.getRequestId().getSendDate().toString());
                anRes.put("subject", rs.getRequestId().getSubject());
                anRes.put("message", rs.getRequestId().getMessage());
                resultList.add(anRes);
            }

            return resultList;

        }
    }

    private JSONArray getResult(HttpServletRequest request, Users u) {
        String examId = request.getParameter("exam_id");
        Exams exam = (Exams) hob.get(Exams.class, Integer.parseInt(examId));
        List<ResultsStudent> sList = hob.createCriteria(ResultsStudent.class).add(Restrictions.eq("studentId", u)).createCriteria("resultId").add(Restrictions.eq("examId", exam)).list();
        JSONArray resultList = new JSONArray();
        for (ResultsStudent rs : sList) {
            JSONObject anRes = new JSONObject();
            anRes.put("exam_title", rs.getResultId().getExamId().getTitle());
            anRes.put("exam_dec", rs.getResultId().getExamId().getDescription());
            anRes.put("exam_id", rs.getResultId().getExamId().getId());
            JSONArray conList = new JSONArray();
            Collection<ResultsContent> rcc = rs.getResultsContentCollection();
            for (ResultsContent rc : rcc) {
                JSONObject objR = new JSONObject();
                objR.put("marks", rc.getMarks());
                objR.put("title", rc.getExamContentsId().getName());
                conList.add(objR);
            }
            anRes.put("contents", conList);
            resultList.add(anRes);
        }
        return resultList;
    }
}
