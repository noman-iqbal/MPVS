/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.ExamMember;
import com.mpvs.db.Exams;
import com.mpvs.db.Users;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;

/**
 *
 * @author noman
 */
@WebServlet(name = "ViewExamPage", urlPatterns = {"/exam.jsp"})
public class ViewExamPage extends HttpServlet {

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
            u = (Users) hob.get(Users.class, u.getId());
            List<Exams> examList;
            if (Auth.isMember(request)) {
                examList = new ArrayList<>();
                for (ExamMember examMember : u.getExamMemberCollection()) {
                    examList.add(examMember.getExamId());
                }
            } else {
                examList = hob.getNamedQuery("Exams.findAll").list();
            }
            request.setAttribute("list", examList);
            request.setAttribute("viewName", "ViewExamView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);

            request.setAttribute("error", "successfull");
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
        }



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
