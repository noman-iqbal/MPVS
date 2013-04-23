/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Subjects;
import com.mpvs.db.Users;
import com.mpvs.db.UsersSubject;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author noman
 */
@WebServlet(name = "ViewUsersPage", urlPatterns = {"/Users.jsp"})
public class ViewUsersPage extends HttpServlet {

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
            ArrayList<Object> usersList = new ArrayList<>();

            String sId = request.getParameter("subject_id");
            if (sId != null && !sId.isEmpty()) {
                Subjects sb = (Subjects) hob.get(Subjects.class, Integer.parseInt(sId));
                List<UsersSubject> tempList = (List<UsersSubject>) sb.getUsersSubjectCollection();
                ArrayList<Object> temp = new ArrayList<>();
                for (UsersSubject object : tempList) {
                    if (object.getUsersStudentId().getType().equals("member")) {
                    } else {
                        temp.add(object.getUsersStudentId());
                    }

                }
                usersList = temp;
            } else {
                Users u = (Users) hob.get(Users.class, Auth.getUser(request).getId());
                Criteria crt = hob.createCriteria(Users.class);
                switch (Auth.getUser(request).getType()) {
                    case "member":

                        Collection<UsersSubject> us = u.getUsersSubjectCollection();
                        for (UsersSubject usersSubject : us) {
                            for (UsersSubject us1 : usersSubject.getSubjectId().getUsersSubjectCollection()) {
                                if (us1.getUsersStudentId().getType().equals("member")) {
                                } else {
                                    if (!usersList.contains(us1.getUsersStudentId())) {
                                    usersList.add(us1.getUsersStudentId());
                                    }
                                }
                            }
                        }
                        break;
                    case "student":
                        usersList = (ArrayList<Object>) crt.add(Restrictions.eq("type", "student")).list();
                        break;
                    case "super_admin":
                        usersList = (ArrayList<Object>) crt.list();
                        break;
                    case "admin":
                        usersList = (ArrayList<Object>) crt.add(Restrictions.ne("type", "super_admin")).list();
                        break;
                }
            }
            request.setAttribute("list", usersList);
        } catch (Exception ex) {
            Logger.getLogger(ViewUsersPage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            List usersList = hob.createCriteria(Users.class).add(Restrictions.ne("id",Auth.getUser(request).getId())).list()






            request.setAttribute("viewName", "ViewUsersView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);

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
