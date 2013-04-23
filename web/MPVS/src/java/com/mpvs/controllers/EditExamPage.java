/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.ExamContents;
import com.mpvs.db.ExamMember;
import com.mpvs.db.Exams;
import com.mpvs.db.Users;
import com.mpvs.db.UsersSubject;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author noman
 */
@WebServlet(name = "EditExamPage", urlPatterns = {"/editExam"})
public class EditExamPage extends HttpServlet {

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
        if (!Auth.isLogedIn(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            hob = ConnectionManager.getSession();
            request.setAttribute("title", "Edit Exam");




            Exams ex = (Exams) hob.get(Exams.class, Integer.parseInt(request.getParameter("exam_id")));
            List contant = hob.createCriteria(ExamContents.class).add(Restrictions.eq("examId", ex)).list();
            List<ExamMember> member = hob.createCriteria(ExamMember.class).add(Restrictions.eq("examId", ex)).list();

            List memList = hob.getNamedQuery("Users.findByType").setParameter("type", "member").list();


            request.setAttribute("memList", memList);

            List<UsersSubject> l = (List<UsersSubject>) Auth.getUser(request).getUsersSubjectCollection();
            ArrayList<Object> temp = new ArrayList<>();
            for (UsersSubject usersSubject : l) {
                temp.add(usersSubject.getSubjectId());
            }
            request.setAttribute("subList", temp);
            request.setAttribute("exam", ex);
            request.setAttribute("content", contant);
            ArrayList<Users> mem = new ArrayList<>();
            for (ExamMember users : member) {
                mem.add(users.getMemberId());
            }
            request.setAttribute("member", mem);
            request.setAttribute("viewName", "AddExamView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);
        } catch (Exception ex) {
            Logger.getLogger(EditExamPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("viewName", "AddExamView.jsp");
        ViewDispatcher.dispatchMyView(request, response, this);
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
}
