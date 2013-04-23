/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.mpvs.db.ConnectionManager;
import com.mpvs.db.ExamContents;
import com.mpvs.db.Exams;
import com.mpvs.db.Results;
import com.mpvs.db.ResultsContent;
import com.mpvs.db.ResultsStudent;
import com.mpvs.db.Subjects;
import com.mpvs.db.Users;
import com.mpvs.db.UsersSubject;
import com.mpvs.db.modal.Pair;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author noman
 */
@WebServlet(name = "AddResultPage", urlPatterns = {"/result.jsp"})
public class AddResultPage extends HttpServlet {

    private Session hob;
    private Users u;
    private Sender sender;
    private Integer rId;

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
        if (!Auth.isLogedIn(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            u = Auth.getUser(request);
            hob = ConnectionManager.getSession();
            Exams ex = (Exams) hob.get(Exams.class, Integer.parseInt(request.getParameter("id")));
            Subjects sub = ex.getSbId();
            List resultList = hob.createCriteria(Results.class).add(Restrictions.eq("examId", ex)).list();
            List<UsersSubject> students = hob.createCriteria(UsersSubject.class).add(Restrictions.eq("subjectId", sub)).createCriteria("usersStudentId").add(Restrictions.ilike("type", "student")).list();
            List<ExamContents> content = hob.createCriteria(ExamContents.class).add(Restrictions.eq("examId", ex)).list();
            Results r = null;
            ArrayList<Pair> list = null;
            if (resultList.isEmpty()) {
                list = getStudentList(null, ex, students, content);
            } else {
                list = getStudentList((Results) resultList.get(0), ex, students, content);
            }
            request.setAttribute("exam", ex);
            request.getSession().setAttribute("result", r);
            request.getSession().setAttribute("listStd", list);
            request.getSession().setAttribute("students", students);
            request.getSession().setAttribute("content", content);
            request.setAttribute("viewName", "AddResultView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);
        } catch (Exception ex) {
            Logger.getLogger(AddResultPage.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            PrintWriter out = response.getWriter();
            String published = request.getParameter("toggle");
            if (hob == null) {
                hob = ConnectionManager.getSession();
            }
            sender = ConnectionManager.getSender();
            ArrayList<Pair> studentList = (ArrayList<Pair>) request.getSession().getAttribute("listStd");
            float totalMarks = getTotalMarks((List<ExamContents>) request.getSession().getAttribute("content"));
            hob.beginTransaction();
            for (Pair st : studentList) {
                String status = request.getParameter("student_id_" + st.getStudent().getStudentId().getId() + "_status");
                float userMarks = 0;
                for (ResultsContent rc : st.getRc()) {
                    String marks = request.getParameter("student_id" + st.getStudent().getStudentId().getId() + "-exam_conetnt_id" + rc.getExamContentsId().getId());
                    if (marks == null || marks.isEmpty()) {
                        rc.setMarks(0f);
                    } else {
                        float m = Float.parseFloat(marks);
                        userMarks += m;
                        rc.setMarks(m);
                    }

                    hob.saveOrUpdate(rc);
                }


                if (((userMarks / totalMarks) * 100) > 50) {
                    st.getStudent().setStatus("pass");
                } else {
                    st.getStudent().setStatus("fail");
                }
                if (status != null) {
                    if (status.contains("on")) {
                        st.getStudent().setStatus("absent");
                    }
                }
                rId = st.getStudent().getResultId().getId();

                hob.saveOrUpdate(st.getStudent());
            }
            hob.getTransaction().commit();
            hob.beginTransaction();
            Results r = (Results) hob.get(Results.class, rId);
            if (published.contains("on")) {
                r.setStatus("publish");
                publishResultToStudent(r);
            } else if (published.contains("off")) {
                r.setStatus("not_publish");
            }
            hob.saveOrUpdate(r);
            hob.getTransaction().commit();


            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private ArrayList<Pair> getStudentList(Results result, Exams ex, List<UsersSubject> students, Iterable<ExamContents> content) throws HibernateException, Exception {
        hob.beginTransaction();
        if (result == null) {
            result = new Results();
            result.setExamId(ex);
            result.setCreatedAt(new Date());
            result.setUpdatedAt(new Date());
            result.setStatus("draft");
            result.setUpdatedAt(new Date());
            hob.save(result);
            for (UsersSubject s : students) {
                ResultsStudent rs = new ResultsStudent();
                rs.setResultId(result);
                rs.setStudentId(s.getUsersStudentId());
                rs.setStatus("draft");
                rs.setCreatedAt(new Date());
                rs.setUpdatedAt(new Date());
                hob.saveOrUpdate(rs);
                for (ExamContents ec : content) {
                    ResultsContent rc = new ResultsContent();
                    rc.setExamContentsId(ec);
                    rc.setResultsStudentId(rs);
                    rc.setCreatedAt(new Date());
                    rc.setUpdatedAt(new Date());
                    hob.saveOrUpdate(rc);
                }
            }
        } else {
            result.setUpdatedAt(new Date());
        }
        hob.saveOrUpdate(result);


        hob.saveOrUpdate(result);
        hob.getTransaction().commit();
        Criteria rct = hob.createCriteria(ResultsStudent.class);
        rct.add(Restrictions.eq("resultId", result));
        List<ResultsStudent> rcList = rct.list();
        ArrayList<Pair> pList = new ArrayList<>();
        for (ResultsStudent rc : rcList) {
            Pair p = new Pair();
            p.setStudent(rc);
            Criteria rcc = hob.createCriteria(ResultsContent.class);
            rcc.add(Restrictions.eq("resultsStudentId", rc));
            Disjunction d = Restrictions.disjunction();
            for (ExamContents c : content) {
                d.add(Restrictions.eq("examContentsId", c));
            }
            rcc.add(d);
            p.setRc((ArrayList<ResultsContent>) rcc.list());
            pList.add(p);
        }
        return pList;
    }

    private void publishResultToStudent(Results r) {


        for (ResultsStudent resultsStudent : r.getResultsStudentCollection()) {
            String reg = resultsStudent.getStudentId().getRegId();

            if (reg != null && !reg.isEmpty()) {
                Builder m = new Message.Builder();
                m.addData("message", "your result is out");
                m.addData("examId", String.valueOf(resultsStudent.getResultId().getExamId().getId()));
                try {
                    Result result = sender.send(m.build(), reg, 5);
                } catch (IOException ex) {
                    Logger.getLogger(AddResultPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private float getTotalMarks(Collection<ExamContents> examContentsCollection) {
        float totalMarks = 0;
        for (ExamContents ec : examContentsCollection) {
            totalMarks += ec.getPercentage();
        }
        return totalMarks;
    }
}
