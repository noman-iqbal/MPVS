/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Sender;
import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Requests;
import com.mpvs.db.RequestsTo;
import com.mpvs.db.Users;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "AddMessagePage", urlPatterns = {"/message.jsp"})
public class AddMessagePage extends HttpServlet {

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
            hob = ConnectionManager.getSession();
            getListOfOptions();
//            request.setAttribute("op", getListOfOptions());
            if (request.getParameter("uid") != null && !request.getParameter("uid").isEmpty()) {
                request.setAttribute("uid", Integer.parseInt(request.getParameter("uid")));
            }

            request.setAttribute("op", hob.getNamedQuery("Users.findAll").list());

            request.setAttribute("viewName", "AddMessageView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);
        } catch (Exception ex) {
            Logger.getLogger(AddMessagePage.class.getName()).log(Level.SEVERE, null, ex);
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
            m.addData("operation_type", "message");
            m.addData("message", "You Have Got New Message");

            String[] user = request.getParameterValues("user");
            String subject = request.getParameter("subject");
            String message = request.getParameter("message");
            Users u = (Users) request.getSession().getAttribute("userInfo");
            Requests r = new Requests();
            r.setMessage(message);
            r.setSubject(subject);
            r.setUserId(u);
            hob.beginTransaction();
            r.setSendDate(new Date());
            hob.save(r);
            m.addData("m_id", String.valueOf(r.getId()));
            for (String id : user) {
                RequestsTo rt = new RequestsTo();
                rt.setRequestId(r);
                Users tempU = (Users) hob.get(Users.class, Integer.parseInt(id));
                rt.setToId(tempU);
                if (tempU.getRegId() != null && !tempU.getRegId().isEmpty()) {
                    sender.send(m.build(), tempU.getRegId(), 5);
                }
                hob.save(rt);
            }
            hob.getTransaction().commit();
            request.setAttribute("message", "Successfull");

        } catch (Exception ex) {
            request.setAttribute("message", "Theres Some error Please Contect to Devaloper.");

            Logger.getLogger(AddMessagePage.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    class Option implements Serializable {

        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Option(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private ArrayList<Option> getListOfOptions() {
        List<Users> users = hob.getNamedQuery("Users.findAll").list();
        ArrayList<Option> allOp = new ArrayList<>();
        for (Users u : users) {
            allOp.add(new Option(u.getId(), u.getFirstName()));
        }
        return allOp;
    }
}
