/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Users;
import com.mpvs.db.modal.UsersDAO;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author noman
 */
@WebServlet(name = "Login", urlPatterns = {"/login.jsp", "/logout.jsp"})
public class LoginPage extends HttpServlet {

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
        if (request.getRequestURI().endsWith("logout.jsp")) {
            request.getSession().removeAttribute("userInfo");
            try {
                ConnectionManager.getSession().close();
            } catch (Exception ex) {
                Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
            }


            response.sendRedirect("index.jsp");
            return;
        }
        
        
        if (Auth.isLogedIn(request, response)) {
            response.sendRedirect("index.jsp");
            return;
        } else {
            request.setAttribute("viewName", "loginView.jsp");
        }
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
            String reqName = request.getParameter("functionName");
            switch (reqName) {
                case "loginUser": {
                    Users obj = UsersDAO.login(request);

                    if (obj != null) {
                        request.getSession().setAttribute("userInfo", obj);
                        Cookie name=new Cookie("name", obj.getFirstName());
                        Cookie title=new Cookie("title", obj.getType());
                        Cookie img=new Cookie("img", obj.getProfilePicUrl());
                        Cookie userName=new Cookie("user_name", obj.getUserName());
                        name.setMaxAge(365 * 24 * 60 * 60);
                        userName.setMaxAge(365 * 24 * 60 * 60);
                        title.setMaxAge(365 * 24 * 60 * 60);
                        img.setMaxAge(365 * 24 * 60 * 60);
                        response.addCookie(name);
                        response.addCookie(userName);
                        response.addCookie(title);
                        response.addCookie(img);
                    } else {
                        request.setAttribute("error", "Cant Find user");
                    }
                    break;
                }
            }

        } catch (Exception ex) {
            request.setAttribute("error", ex.toString());
        }
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
