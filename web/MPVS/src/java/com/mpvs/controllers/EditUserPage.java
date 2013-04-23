/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Users;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;

/**
 *
 * @author noman
 */
@WebServlet(name = "EditUserPage", urlPatterns = {"/EditUser"})
public class EditUserPage extends HttpServlet {

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
            String uId = request.getParameter("user_id");
            hob = ConnectionManager.getSession();
            Users u = (Users) hob.get(Users.class, Integer.parseInt(uId));
            String callType = null;
            switch (u.getType()) {
                case "student":
                    callType = "AddStudent.jsp";
                    break;
                case "member":
                    callType = "AddMember.jsp";
                    break;
                case "admin":
                    callType = "AddAdmin.jsp";
                    break;
            }
            request.setAttribute("user", u);
            request.setAttribute("typeSt", u.getType());
            request.setAttribute("callType", callType);
            request.setAttribute("user_sb_list", u.getUsersSubjectCollection());
            request.setAttribute("subList", hob.getNamedQuery("Subjects.findAll").list());
            request.setAttribute("programList", hob.getNamedQuery("Programs.findAll").list());
            request.setAttribute("viewName", "AddUserView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);
        } catch (Exception ex) {
            Logger.getLogger(EditUserPage.class.getName()).log(Level.SEVERE, null, ex);
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
