/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.api;

import com.google.gson.JsonObject;
import com.mpvs.db.ConnectionManager;
import com.mpvs.db.ExamContents;
import com.mpvs.db.ExamMember;
import com.mpvs.db.Exams;
import com.mpvs.db.Requests;
import com.mpvs.db.Results;
import com.mpvs.db.ResultsContent;
import com.mpvs.db.ResultsStudent;
import com.mpvs.db.Subjects;
import com.mpvs.db.Users;
import com.mpvs.db.UsersSubject;
import com.mpvs.helper.Auth;
import com.mpvs.helper.PDFCreater;
import com.sun.xml.internal.bind.v2.model.core.ID;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "AjaxCalls", urlPatterns = {"/ajcall"})
public class AjaxCalls extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        try {
            hob = ConnectionManager.getSession();
            Transaction tr = hob.getTransaction();
            String id;
            tr.begin();
            String funcName = request.getParameter("func");
            switch (funcName) {
                case "saveRs":
                    id = request.getParameter("id");
                    String marks = request.getParameter("mark");
                    ResultsContent rc = (ResultsContent) hob.get(ResultsContent.class, Integer.parseInt(id));
                    if (rc != null) {
                        rc.setMarks(Float.parseFloat(marks));
                        hob.update(rc);
                        out.print("done");
                    }

                    break;
                case "getResultStatus":
                    out.print(getResultStatus(Auth.getUser(request)));
                    break;
                case "getUserPopUp":
                    id = request.getParameter("id");
                    Users uObj = (Users) hob.get(Users.class, Integer.parseInt(id));
                    if (uObj == null) {
                        throw new Exception("cant_find_user");
                    } else {
                        request.setAttribute("u", uObj);
                        request.setAttribute("sb", uObj.getUsersSubjectCollection());
                        request.getRequestDispatcher("userInfoPopUpView.jsp").forward(request, response);
                        obj.put("message", "success");
                    }
                    break;

                case "getResultPDF": {
                    id = request.getParameter("res_id");
                    Results r = (Results) hob.get(Results.class, Integer.parseInt(id));
                    PDFCreater p = new PDFCreater(this.getServletContext().getRealPath("/") + "/PDFS/" + id + "_pdf_result", r);
                    obj.put("URL", "/PDFS/" + id + "_pdf_result.pdf");
                }
                break;

                case "getResultPopUp":
                    id = request.getParameter("id");
                    ResultsStudent rs = (ResultsStudent) hob.get(ResultsStudent.class, Integer.parseInt(id));
                    request.setAttribute("rs", rs);
                    request.setAttribute("rc", rs.getResultsContentCollection());
                    request.getRequestDispatcher("resultInfoPopUpView.jsp").forward(request, response);
                    obj.put("message", "success");

                    break;
                case "getSlots":
                    id = request.getParameter("id");
                    String exam_id = request.getParameter("exam_id");
                    Subjects s = (Subjects) hob.get(Subjects.class, Integer.parseInt(id));
                    if (exam_id != null && !exam_id.isEmpty()) {
                        Exams exam = (Exams) hob.get(Exams.class, Integer.parseInt(exam_id));
                        request.setAttribute("exam", exam);
                    }
                    request.setAttribute("slots", s.getTimeSlotsCollection());
                    request.getRequestDispatcher("timeSlotView.jsp").forward(request, response);
                    obj.put("message", "success");
                    break;
                case "setExams":
                    int i = Integer.parseInt(request.getParameter("id"));
                    Exams e = (Exams) hob.get(Exams.class, i);
                    String date = request.getParameter("held");
//                    e.setStart(new Date(date));
                    hob.saveOrUpdate(e);
                    break;
                case "getExams":
                    Users u = (Users) request.getSession().getAttribute("userInfo");
                    String from = request.getParameter("from");
                    String to = request.getParameter("to");
                    if (u != null) {
                        if (Auth.isStudent(request)) {
                            out.print(getAllExamByStudent(u, to, from));
                        } else {
                            if (Auth.isMember(request)) {
                                List<ExamMember> emList = hob.createCriteria(ExamMember.class).add(Restrictions.eq("memberId", u)).list();
                                JSONArray exams = new JSONArray();
                                for (ExamMember em : emList) {
                                    JsonObject ex = new JsonObject();
                                    ex.addProperty("title", em.getExamId().getTitle());
                                    ex.addProperty("start", em.getExamId().getStartDate().getTime());
                                    ex.addProperty("end", em.getExamId().getHeldId().getEndTime().getTime());
                                    ex.addProperty("id", em.getExamId().getId());
                                    exams.add(ex);
                                }
                                out.print(exams);
                            } else {
                                List<Exams> examsList = hob.getNamedQuery("Exams.findAll").list();
                                JSONArray exams = new JSONArray();
                                for (Exams em : examsList) {
                                    JsonObject ex = new JsonObject();
                                    ex.addProperty("title", em.getTitle());
                                    ex.addProperty("start", em.getStartDate().getTime());
                                    ex.addProperty("end", em.getHeldId().getEndTime().getTime());
                                    ex.addProperty("id", em.getId());
                                    exams.add(ex);
                                }
                                out.print(exams);
                            }
                        }
                    }
                    break;
                case "delUser":
                    id = request.getParameter("id");
                    uObj = (Users) hob.get(Users.class, Integer.parseInt(id));
                    if (uObj == null) {
                        throw new Exception("cant_file_user");
                    } else {
                        hob.delete(uObj);
                        tr.commit();
                        obj.put("message", "success");
                    }
                    break;

                case "delExam":
                    id = request.getParameter("id");
                    Exams eObj = (Exams) hob.get(Exams.class, Integer.parseInt(id));
                    if (eObj == null) {
                        throw new Exception("cant_find_user");
                    } else {
                        hob.delete(eObj);
                        obj.put("message", "success");
                    }
                    break;

                case "delMes":
                    id = request.getParameter("id");
                    hob.delete(hob.get(Requests.class, Integer.parseInt(id)));
                    obj.put("message", "success");

                    break;
                case "getExamView":
                    id = request.getParameter("id");
                    eObj = (Exams) hob.get(Exams.class, Integer.parseInt(id));
                    List list = hob.createCriteria(ExamMember.class).add(Restrictions.eq("examId", eObj)).list();
                    request.setAttribute("member", list);
                    request.setAttribute("content", hob.createCriteria(ExamContents.class).add(Restrictions.eq("examId", eObj)).list());
                    request.setAttribute("exam", eObj);
                    request.getRequestDispatcher("examInfoPopUpView.jsp").forward(request, response);
                    obj.put("message", "success");
                    break;
                case "getMessagePopUp":
                    id = request.getParameter("id");
                    Requests r = (Requests) hob.get(Requests.class, Integer.parseInt(id));
                    request.setAttribute("r", r);
                    request.getRequestDispatcher("messagePopUpView.jsp").forward(request, response);
                    obj.put("message", "success");
                    break;


            }
            tr.commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            obj.put("error", true);
            obj.put("message", ex.getMessage());
        } finally {
            if (!obj.isEmpty()) {
                out.print(obj);
            }
            out.close();
        }
    }

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

    private JSONArray getAllExamByStudent(Users u, String to, String from) {
        List<Subjects> tempSubject = new ArrayList<>();
        for (UsersSubject usersSubject : u.getUsersSubjectCollection()) {
            tempSubject.add(usersSubject.getSubjectId());
        }
        List<Exams> exL = hob.createCriteria(Exams.class).add(Restrictions.in("sbId", tempSubject)).list();
        JSONArray examsA = new JSONArray();
        for (Exams exams : exL) {
            JsonObject ex = new JsonObject();
            ex.addProperty("title", exams.getTitle());
            ex.addProperty("start", exams.getStartDate().getTime());
            ex.addProperty("end", exams.getHeldId().getEndTime().getTime());
            ex.addProperty("id", exams.getId());
            examsA.add(ex);
        }
        return examsA;
    }

    private JSONArray getResultStatus(Users u) {
        if (u.getType().contains("member")) {
            u = (Users) hob.get(Users.class, u.getId());
            JSONArray rStatusL = new JSONArray();
            for (ExamMember examMember : u.getExamMemberCollection()) {
                for (Results results : examMember.getExamId().getResultsCollection()) {
                    JSONObject rStatus = new JSONObject();
                    rStatus.put("exam_name", results.getExamId().getTitle());
                    rStatus.put("subject_name", results.getExamId().getSbId().getName());
                    int pass = 0;
                    int fail = 0;
                    int absent = 0;
                    for (ResultsStudent resultsStudent : results.getResultsStudentCollection()) {
                        if (resultsStudent.getStatus().contains("pass")) {
                            pass++;
                        }
                        if (resultsStudent.getStatus().contains("fail")) {
                            fail++;
                        }
                        if (resultsStudent.getStatus().contains("absent")) {
                            absent++;
                        }
                    }
                    rStatus.put("pass", pass);
                    rStatus.put("fail", fail);
                    rStatus.put("absent", absent);
                    rStatusL.add(rStatus);

                }
            }
            return rStatusL;
        } else {

            JSONArray rStatusL = new JSONArray();
            for (Results results : (List< Results>) hob.getNamedQuery("Results.findAll").list()) {
                JSONObject rStatus = new JSONObject();
                rStatus.put("exam_name", results.getExamId().getTitle());
                rStatus.put("subject_name", results.getExamId().getSbId().getName());
                int pass = 0;
                int fail = 0;
                int absent = 0;
                for (ResultsStudent resultsStudent : results.getResultsStudentCollection()) {
                    if (resultsStudent.getStatus().contains("pass")) {
                        pass++;
                    }
                    if (resultsStudent.getStatus().contains("fail")) {
                        fail++;
                    }
                    if (resultsStudent.getStatus().contains("absent")) {
                        absent++;
                    }
                }
                rStatus.put("pass", pass);
                rStatus.put("fail", fail);
                rStatus.put("absent", absent);
                rStatusL.add(rStatus);

            }
            return rStatusL;
        }
    }
}
