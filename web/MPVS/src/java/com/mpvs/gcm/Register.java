package com.mpvs.gcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Users;
import java.io.IOException;
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
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author noman
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class Register extends HttpServlet {
    
    private Session hob;
    private Sender sender;

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
            String regId = request.getParameter("regId");
            Builder messageB = new Message.Builder();
            messageB.addData("operation_type", "login");
            String function = request.getParameter("function_name");
            switch (function) {
                case "un_reg": {
                    List<Users> users = hob.createCriteria(Users.class).add(Restrictions.eq("regId", regId)).list();
                    Transaction tr = hob.getTransaction();
                    tr.begin();
                    
                    
                    if (users.isEmpty()) {
                        messageB.addData("message", "you dont have any user");
                    } else {
                        Users u = users.get(0);
                        System.out.println(u);
                        u.setRegId(null);
                        u.setUpdatedAt(new Date());
                        
                        hob.saveOrUpdate(u);
                        
                        messageB.addData("message", "Thanks for using MPVS!");
                    }
                    
                    tr.commit();
                    break;
                    
                }
                case "reg": {
                    String userName = request.getParameter("user_name");
                    String password = request.getParameter("password");
                    List<Users> users = hob.createCriteria(Users.class).add(Restrictions.eq("userName", userName)).add(Restrictions.eq("password", password)).list();
                    Transaction tr = hob.getTransaction();
                    tr.begin();
                    if (users.isEmpty()) {
                        messageB.addData("message", "you dont have any user");
                    } else {
                        Users u = users.get(0);
                        System.out.println(u);
                        u.setRegId(regId);
                        u.setUpdatedAt(new Date());
                        
                        hob.saveOrUpdate(u);
                        messageB.addData("id", String.valueOf(u.getId()));
                        messageB.addData("is_student", u.getType());
                        messageB.addData("message", "You have succsessfully regesterd!");
                    }
                    tr.commit();
                    break;
                }
            }
            Result result = sender.send(messageB.build(), regId, 5);
        } catch (Exception ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
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
}
