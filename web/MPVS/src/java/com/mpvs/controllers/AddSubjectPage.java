/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Subjects;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author noman
 */
@WebServlet(name = "AddSubjectPage", urlPatterns = {"/AddSubject.jsp"})
public class AddSubjectPage extends HttpServlet {

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
        
        request.setAttribute("viewName", "AddSubjectView.jsp");
        ViewDispatcher.dispatchMyView(request, response, this);

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
            Subjects sub = new Subjects();
            if (request.getParameter("id") != null || !request.getParameter("id").isEmpty()) {
                sub.setSubjectId(request.getParameter("id"));
            } else {
                throw new UnknownError("Subject id cant found");
            }
            if (request.getParameter("name") != null || !request.getParameter("name").isEmpty()) {
                sub.setName(request.getParameter("name"));
            } else {
                throw new UnknownError("Subject Name cant found");
            }
            if (request.getParameter("cHr") != null || !request.getParameter("cHr").isEmpty()) {
                sub.setCreditHr(Integer.parseInt(request.getParameter("cHr")));
            } else {
                throw new UnknownError("Subject Cradit hr cant found");
            }
            sub.setCreatedAt(new Date());
            sub.setUpdatedAt(new Date());
            hob.beginTransaction();
            hob.save(sub);
            hob.getTransaction().commit();


            processRequest(request, response);
        } catch (HibernateException ex) {
            request.setAttribute("error", ex.getMessage(1));
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
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
    }
}
