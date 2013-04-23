package com.mpvs.controllers;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Sender;
import com.mpvs.db.ConnectionManager;
import com.mpvs.db.ExamContents;
import com.mpvs.db.ExamMember;
import com.mpvs.db.Exams;
import com.mpvs.db.Subjects;
import com.mpvs.db.TimeSlots;
import com.mpvs.db.Users;
import com.mpvs.db.UsersSubject;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author noman
 */
@WebServlet(name = "AddExamPage", urlPatterns = {"/AddExam.jsp"})
public class AddExamPage extends HttpServlet {

    private Session hob;
    private Sender sender;

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
            Session hob = ConnectionManager.getSession();
            Users u = Auth.getUser(request);

            request.setAttribute("title", "Create Exam");
            request.getSession().removeAttribute("exam");
            request.getSession().removeAttribute("content");

            if (Auth.isSuperAdmin(request)) {
                List subList = hob.getNamedQuery("Subjects.findAll").list();
                request.setAttribute("subList", subList);

            } else {
                List<UsersSubject> l = (List<UsersSubject>) u.getUsersSubjectCollection();
                ArrayList<Object> temp = new ArrayList<>();
                for (UsersSubject usersSubject : l) {
                    temp.add(usersSubject.getSubjectId());
                }
                request.setAttribute("subList", temp);
            }
            List memList = hob.getNamedQuery("Users.findByType").setParameter("type", "member").list();
            request.setAttribute("memList", memList);
        } catch (Exception ex) {
            request.setAttribute("error", ex.toString());
        } finally {
            request.setAttribute("viewName", "AddExamView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);
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
            hob = ConnectionManager.getSession();
            sender = ConnectionManager.getSender();
            Builder m = new Message.Builder();
            String title = request.getParameter("qTitle");
            String desc = request.getParameter("qDec");
            String start = request.getParameter("start_date");
            String end = request.getParameter("end_date");
            String holdId = request.getParameter("hold_time");
            String[] names = request.getParameterValues("names");
            String[] waights = request.getParameterValues("waights");
            String subjectId = request.getParameter("subjectList");
            String[] membersId = request.getParameterValues("memberList");
            String createrId = request.getParameter("createrId");
            String examId = request.getParameter("examId");

            String examDate = request.getParameter("exam_date");
            String examWeek = request.getParameter("exam_week");

            m.addData("operation_type", "updateExam");
            Exams newEx = new Exams();
            if (!examId.isEmpty()) {
                m.addData("isUpdate", "true");

                newEx = (Exams) hob.get(Exams.class, Integer.parseInt(examId));

            } else {
                m.addData("isUpdate", "false");
                newEx.setCreatedAt(new Date());
            }
            newEx.setTitle(title);
            newEx.setDescription(desc);
            newEx.setStartDate(getHeldDate(examDate, examWeek, holdId).getTime());
            newEx.setUserCreaterId((Users) hob.get(Users.class, Integer.parseInt(createrId)));
            newEx.setStatus("currnet");
            newEx.setSbId((Subjects) hob.get(Subjects.class, Integer.parseInt(subjectId)));
            newEx.setHeldId((TimeSlots) hob.get(TimeSlots.class, Integer.parseInt(holdId)));
            newEx.setUpdatedAt(new Date());
            validateDate(newEx);

            hob.beginTransaction();
            hob.saveOrUpdate(newEx);
            deletExamOldContent(newEx);
            for (int i = 0; i < names.length; i++) {
                ExamContents exC = new ExamContents();
                exC.setExamId(newEx);
                exC.setName(names[i]);
                exC.setPercentage(Float.parseFloat(waights[i]));
                exC.setCreatedAt(new Date());
                exC.setUpdatedAt(new Date());
                hob.save(exC);
            }
            deletExamOldMember(newEx);
            if (membersId == null) {
                ExamMember sub = new ExamMember();
                sub.setExamId(newEx);
                sub.setMemberId((Users) hob.get(Users.class, Integer.parseInt(createrId)));
                hob.save(sub);
            } else {
                for (String id : membersId) {
                    ExamMember sub = new ExamMember();
                    sub.setExamId(newEx);
                    sub.setMemberId((Users) hob.get(Users.class, Integer.parseInt(id)));
                    hob.save(sub);
                }
            }



            for (UsersSubject us : newEx.getSbId().getUsersSubjectCollection()) {
                if (us.getUsersStudentId().getType().contains("student")) {
                    String rg = us.getUsersStudentId().getRegId();
                    if (rg != null && !rg.isEmpty()) {
                        m.addData("operation_type", "");
                        m.addData("message", "you have new exam On " + newEx.getStartDate());
                        m.addData("examId", String.valueOf(newEx.getId()));
//                        sender.send(m.build(), rg, 5);
                    }
                }
            }
            m.addData("operation_type", "updateExam");
            m.addData("examId", String.valueOf(newEx.getId()));

            List<ExamMember> memberList = hob.createCriteria(ExamMember.class).add(Restrictions.eq("examId", newEx)).list();
            for (ExamMember em : memberList) {
                String reg = em.getMemberId().getRegId();
                m.addData("message", "you have new exam To be taken");
                if (reg != null && !reg.isEmpty()) {
//                    sender.send(m.build(), reg, 5);
                }
            }
            request.setAttribute("error", "successfull");
            hob.getTransaction().commit();
        } catch (PropertyValueException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            request.setAttribute("error", errors.toString());
        } catch (HibernateException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            request.setAttribute("error", errors.toString());
        } catch (ParseException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            request.setAttribute("error", errors.toString());
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            request.setAttribute("error", errors.toString());
        } finally {
            processRequest(request, response);
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

    private void deletExamOldContent(Exams newEx) {
        List list = hob.createCriteria(ExamContents.class).add(Restrictions.eq("examId", newEx)).list();
        for (Object object : list) {
            hob.delete(object);
        }
    }

    private void deletExamOldMember(Exams newEx) {
        List list = hob.createCriteria(ExamMember.class).add(Restrictions.eq("examId", newEx)).list();
        for (Object object : list) {
            hob.delete(object);
        }
    }

    private void validateDate(Exams newEx) throws Exception {

        Calendar c = Calendar.getInstance();
        c.setTime(newEx.getStartDate());
        if (c.get(Calendar.DAY_OF_MONTH) < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
//            throw new Exception("Start date Should Greater then Current date");
        }




//        if (newEx.getStart().getTime() + 600 < new Date().getTime()) {
//            throw new Exception("Start date Should Greater then Current date");
//        }
//        if (newEx.getStart().getTime() > newEx.getEnd().getTime()) {
//            throw new Exception("End date Should Greater then end date");
//        }
//        Users creater = newEx.getUserCreaterId();
//        ArrayList<Subjects> tempSubject = new ArrayList<>();
//        for (UsersSubject usersSubject : creater.getUsersSubjectCollection()) {
//            tempSubject.add(usersSubject.getSubjectId());
//        }
//        List<UsersSubject> students = hob.createCriteria(UsersSubject.class).add(Restrictions.in("subjectId", tempSubject)).createCriteria("usersStudentId").add(Restrictions.eq("type", "student")).list();
//        for (UsersSubject us : students) {
//            for (UsersSubject usersSubject : us.getUsersStudentId().getUsersSubjectCollection()) {
//                for (Exams exams : usersSubject.getSubjectId().getExamsCollection()) {
//                    if (exams.getStart().getTime() > newEx.getStart().getTime() && exams.getEnd().getTime() < newEx.getStart().getTime()) {
//                        throw new Exception("There is an collioun in exams");
//                    }
//                }
//
//            }
//        }
        //        Criteria cc = hob.createCriteria(Exams.class);
        //        Conjunction c = Restrictions.conjunction();
        //        Conjunction c1 = Restrictions.conjunction();
        //        c.add(Restrictions.ge("start", newEx.getStart()));
        //        c.add(Restrictions.le("end", newEx.getStart()));
        //
        //        c1.add(Restrictions.ge("start", newEx.getEnd()));
        //        c1.add(Restrictions.le("end", newEx.getEnd()));
        //
        //        Disjunction d = Restrictions.disjunction();
        //        d.add(c);
        //        d.add(c1);
        //
        //        List examsList = cc.add(Restrictions.eq("sbId", newEx.getSbId())).list();
        //        if (!examsList.isEmpty()) {
        //            throw new Exception("There is already an Exam in this time .");
        //        }
    }

    private Calendar getHeldDate(String examDate, String examWeek, String holdId) throws ParseException {
        TimeSlots t = (TimeSlots) hob.get(TimeSlots.class, Integer.parseInt(holdId));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM yyyy EEEEE kk:mm:ss");

        String dateString = examDate + " " + t.getDayName() + " " + t.getStartTime().toString();
        Date d = sdf.parse(dateString);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(examWeek));
        return c;
    }
}
