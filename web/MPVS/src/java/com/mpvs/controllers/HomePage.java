/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.RequestsTo;
import com.mpvs.db.ResultsContent;
import com.mpvs.db.ResultsStudent;
import com.mpvs.db.Users;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.sql.ResultSet;
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
@WebServlet(name = "Home", urlPatterns = {"/index.jsp"})
public class HomePage extends HttpServlet {

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
        try {
            /* TODO output your page here. You may use following sample code. */
            hob = ConnectionManager.getSession();
            if (!Auth.isLogedIn(request, response)) {
                response.sendRedirect("login.jsp");
                return;
            }
            Users u = (Users) request.getSession().getAttribute("userInfo");
            hob = ConnectionManager.getSession();
            List<RequestsTo> rlist = hob.createCriteria(RequestsTo.class).add(Restrictions.eq("toId", u)).list();
            request.setAttribute("rList", rlist);

            if (Auth.isStudent(request)) {
                List<ResultsStudent> rsList = hob.createCriteria(ResultsStudent.class).add(Restrictions.eq("studentId", Auth.getUser(request))).createCriteria("resultId").add(Restrictions.eq("status", "publish")).list();
                request.setAttribute("rsList", rsList);
            }
        } catch (Exception ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("viewName", "homeView.jsp");
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
